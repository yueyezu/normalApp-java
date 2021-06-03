package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysUserloginMapper;
import org.litu.app.entity.system.SysUserlogin;
import org.litu.app.service.ISysUserloginService;
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
public class SysUserloginServiceImpl extends BaseServiceImpl<SysUserloginMapper, SysUserlogin> implements ISysUserloginService {

    /**
     * 根据userId查找
     *
     * @param userId
     * @return
     */
    public SysUserlogin getByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        LambdaQueryWrapper<SysUserlogin> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserlogin::getUserId, userId);
        SysUserlogin sysUserLogin = getOne(queryWrapper);

        return sysUserLogin;
    }
}
