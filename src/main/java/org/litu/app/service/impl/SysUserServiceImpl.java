package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysUserMapper;
import org.litu.app.dao.SysUserloginMapper;
import org.litu.app.entity.SysUser;
import org.litu.app.entity.SysUserlogin;
import org.litu.app.service.ISysFilesService;
import org.litu.app.service.ISysUserService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.exception.LtParamException;
import org.litu.core.login.LoginTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * user服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-17
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserloginMapper sysUserloginMapper;
    @Autowired
    private ISysFilesService sysFilesService;

    /**
     * 至获取有效的和未删除的list信息
     *
     * @param entity
     * @param keyword
     * @param params
     * @param query
     */
    @Override
    public void beforeList(SysUser entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysUser> query) {
        super.beforeList(entity, keyword, params, query);
        query.eq(SysUser::getfDeleteflag, SysContant.FLAG_FALSE);
        query.orderByAsc(SysUser::getfSortnum);
    }

    /**
     * 用户添加的方法
     */
    @Override
    public boolean save(SysUser entity, Map<String, String> params) {
        if (!super.save(entity)) {
            throw new LtParamException("用户信息保存失败！");
        }
        // 创建用户的密码信息
        SysUserlogin sysUserLogin = new SysUserlogin();
        String sKey = LoginTokenUtil.GetSecretkey();
        sysUserLogin.setfSecretkey(sKey);
        try {
            sysUserLogin.setfPassword(LoginTokenUtil.GetDbPassword(sKey));
        } catch (Exception e) {
            throw new LtParamException("生成用户密码错误！");
        }
        sysUserLogin.setfEnablelogin(SysContant.FLAG_TRUE);
        // TODO 这里默认用户都是允许多点登陆的。
        sysUserLogin.setfMultiuserlogin(0);
        sysUserLogin.setfUserid(entity.getfId());
        sysUserLogin.setfLogoncount(0);
        sysUserLogin.setfLoginstatus(SysContant.FLAG_TRUE);
        return retBool(sysUserloginMapper.insert(sysUserLogin));
    }

    /**
     * 根据账户名查找用户 在用户登录时使用， 只有当账户存在，并且可用是才会返回用户信息。
     *
     * @param account
     * @return
     */
    public SysUser getByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUser::getfAccount, account);
        SysUser user = getOne(queryWrapper);

        return user;
    }

    /**
     * 获取用户的树列表信息
     *
     * @param fDepartmentid
     * @return
     */
    @Override
    public List<Map<String, String>> userTree(String fDepartmentid) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("F_DeleteFlag", 0);
        queryWrapper.eq("F_DepartmentId", fDepartmentid);
        List<SysUser> userList = list(queryWrapper);
        //封装成前端树结构需要的数据形式
        for (SysUser sysUser : userList) {
            Map<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("id", sysUser.getfId());
            mapTemp.put("text", sysUser.getfRealname());
            mapTemp.put("parent", "#");
            resultList.add(mapTemp);
        }

        return resultList;
    }

    /**
     * 更换当前登陆用户头像
     *
     * @param userId
     * @param photoId 用户头像ID
     * @return 如果替换成功，则返回true
     */
    @Override
    public boolean updatePhoto(String userId, String photoId) {
        SysUser sysUser = getById(userId);
        //原头像删除
        String oldPhotoId = sysUser.getfPhoto();
        if (StringUtils.isNotBlank(oldPhotoId)) {
            // 删除这部分的异常忽略掉。
            try {
                sysFilesService.delete(oldPhotoId, null);
            } catch (Exception e) {
            }
        }
        sysUser.setfPhoto(photoId);
        return updateById(sysUser);
    }
}
