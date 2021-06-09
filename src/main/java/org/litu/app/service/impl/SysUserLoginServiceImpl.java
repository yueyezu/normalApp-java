package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysUserLoginMapper;
import org.litu.app.entity.system.SysUserLogin;
import org.litu.app.service.ISysUserLoginService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
@Service
public class SysUserLoginServiceImpl extends BaseServiceImpl<SysUserLoginMapper, SysUserLogin> implements ISysUserLoginService {

    /**
     * 根据userId查找
     *
     * @param userId
     * @return
     */
    public SysUserLogin getByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        LambdaQueryWrapper<SysUserLogin> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserLogin::getUserId, userId);
        SysUserLogin sysUserLogin = getOne(queryWrapper);

        return sysUserLogin;
    }
}
