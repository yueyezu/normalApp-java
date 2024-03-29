package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysAccessTokenMapper;
import org.litu.app.entity.system.SysAccessToken;
import org.litu.app.entity.system.SysAccessTokenEx;
import org.litu.app.entity.system.SysSystem;
import org.litu.app.entity.system.SysUser;
import org.litu.app.service.ISysAccessTokenService;
import org.litu.app.service.ISysSystemService;
import org.litu.app.service.ISysUserService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.litu.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2019-06-27
 */
@Service
public class SysAccessTokenServiceImpl extends BaseServiceImpl<SysAccessTokenMapper, SysAccessToken> implements ISysAccessTokenService {

    @Autowired
    ISysSystemService sysSystemService;
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public SysAccessTokenEx detailEx(String id) {
        SysAccessToken sysAccesstoken = super.detail(id);
        SysUser sysUser = sysUserService.getById(sysAccesstoken.getUserId());

        SysAccessTokenEx accesstokenEx = new SysAccessTokenEx(sysAccesstoken);
        accesstokenEx.setAccount(null == sysUser ? "" : sysUser.getAccount());
        accesstokenEx.setRealName(null == sysUser ? "" : sysUser.getRealName());
        return accesstokenEx;
    }

    /**
     * 启用
     *
     * @param id 需要启动的id
     * @return true则表明启动成功
     */
    @Override
    public boolean enable(String id) {
        return changeValue(id, "1", "enable_flag");
    }

    /**
     * 禁用
     *
     * @param id 需要禁用的id
     * @return true则表明禁用成功
     */
    @Override
    public boolean disable(String id) {
        return changeValue(id, "0", "enable_flag");
    }

    /*----------------- 以上为授权码管理的方法  ---------------------*/

    /**
     * 验证客户端是否为正确的客户端
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    @Override
    public boolean checkClient(String clientId, String clientSecret) {
        LambdaQueryWrapper<SysSystem> query = Wrappers.lambdaQuery();
        query.eq(SysSystem::getCode, clientId);
        query.eq(SysSystem::getSecret, clientSecret);

        SysSystem client = sysSystemService.getOne(query);

        return client != null;
    }

    /**
     * 创建认证token信息。并存储到数据库
     *
     * @param accessToken
     * @return
     */
    @Override
    public SysAccessToken createToken(SysAccessToken accessToken) {
        // 创建新的授权码
        accessToken = CreateAccessToken(accessToken);
        accessToken.setEnableFlag(SysContant.FLAG_TRUE);
        Date nowTime = new Date();
        accessToken.setCreateTime(nowTime);
        accessToken.setModifyTime(nowTime);

        // 保存数据库操作
        LambdaQueryWrapper<SysAccessToken> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysAccessToken::getClientType, accessToken.getClientType());
        queryWrapper.eq(SysAccessToken::getUserId, accessToken.getUserId());
        SysAccessToken tempToken = baseMapper.selectOne(queryWrapper);
        int rc = 0;
        if (tempToken == null) { // 如果temptoken为null，表示为第一次获取授权码，直接创建即可
            rc = baseMapper.insert(accessToken);
        } else {
            accessToken.setId(tempToken.getId());
            rc = baseMapper.updateById(accessToken);
        }

        if (rc > 0) {
            return accessToken;
        } else {
            throw new LtServerException("保存数据库错误。", ResultEnum.GetTokenError);
        }
    }

    /**
     * 刷新授权码，根据刷新码
     *
     * @param refreshToken
     * @return
     */
    @Override
    public SysAccessToken refreshToken(String clientType, String refreshToken) {
        LambdaQueryWrapper<SysAccessToken> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysAccessToken::getRefreshToken, refreshToken);
        queryWrapper.eq(SysAccessToken::getClientType, clientType);
        SysAccessToken accessToken = baseMapper.selectOne(queryWrapper);
        if (accessToken == null) {
            throw new LtServerException("未找到当前刷新码信息。", ResultEnum.GetRefreshTokenError);
        }

        // 判断刷新码是否过期
        Integer refreshSpan = accessToken.getEnableRefreshTime();
        Date nowTime = new Date();
        if (refreshSpan != 0) {
            Date createTime = accessToken.getCreateTime();
            long cha = nowTime.getTime() - createTime.getTime();
            long days = (refreshSpan * 24 * 60 * 60 * 1000); // 将时间调整到毫秒 天

            if (days < cha) {
                throw new LtServerException("刷新码已经过期,请重新获取！", ResultEnum.TokenTimeout);
            }
        }

        // 刷新获取新的授权码信息
        accessToken = CreateAccessToken(accessToken);
        accessToken.setEnableFlag(SysContant.FLAG_TRUE);
        accessToken.setModifyTime(nowTime);

        // 存储到数据库
        LambdaUpdateWrapper<SysAccessToken> updateWrapper = Wrappers.lambdaUpdate();
        queryWrapper.eq(SysAccessToken::getRefreshToken, refreshToken);
        queryWrapper.eq(SysAccessToken::getClientType, clientType);
        int rc = baseMapper.update(accessToken, updateWrapper);

        if (rc > 0) {
            return accessToken;
        } else {
            throw new LtServerException("保存数据库错误。", ResultEnum.GetRefreshTokenError);
        }
    }

    /**
     * 验证用户的授权码，并返回该授权码关联的用户信息。
     *
     * @param clientType 系统类型
     * @param token      授权码
     * @param mcode      设备机器码
     * @return
     */
    @Override
    public SysAccessToken checkToken(String clientType, String token, String mcode) {
        LambdaQueryWrapper<SysAccessToken> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysAccessToken::getClientType, clientType);
        queryWrapper.eq(SysAccessToken::getToken, token);
        SysAccessToken accessToken = baseMapper.selectOne(queryWrapper);
        if (accessToken == null) {
            return null;
        }

        // 判断授权码码是否过期
        Integer tokenSpan = accessToken.getEnableTime();
        Integer enableFlag = accessToken.getEnableFlag();
        if (enableFlag == SysContant.FLAG_TRUE && tokenSpan != 0) {
            Date nowTime = new Date();
            Date createTime = accessToken.getCreateTime();
            long cha = nowTime.getTime() - createTime.getTime();
            long hours = (tokenSpan * 60 * 60 * 1000); // 将时间调整到毫秒 天

            if (hours < cha) { // 如果是超期，则在数据库标记已经不可用
                accessToken.setEnableFlag(SysContant.FLAG_FALSE);
                baseMapper.updateById(accessToken);
            }
        }

        return accessToken;
    }

    /*----------------- 以下为授权码生成的方法  ---------------------*/

    /**
     * 授权码的有效时间，单位为小时，为0表示不会过期
     */
    @Value("${ltsystem.oauth.tokenEnableTime}")
    private Integer tokenEnableTime = 24;

    /**
     * 刷新码的有效时间,单位天，如果为0，表示不会过期
     */
    @Value("${ltsystem.oauth.refreshEnableTime}")
    private Integer refreshEnableTime = 0;

    /**
     * 生成授权码以及刷新码的方法
     *
     * @param accessToken
     * @return
     */
    private SysAccessToken CreateAccessToken(SysAccessToken accessToken) {
        // TODO 第三方的token，后续将该方法调整的token复杂度高些。
        String token = Util.GuId32();
        accessToken.setToken(token);
        accessToken.setEnableTime(tokenEnableTime);

        String refreshToken = Util.GuId32();
        accessToken.setRefreshToken(refreshToken);
        accessToken.setEnableRefreshTime(refreshEnableTime);

        return accessToken;
    }
}
