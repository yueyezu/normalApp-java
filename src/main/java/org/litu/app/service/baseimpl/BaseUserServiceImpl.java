package org.litu.app.service.baseimpl;

import org.litu.app.dao.SysUserMapper;
import org.litu.app.entity.system.SysUser;
import org.litu.base.service.IBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseUserServiceImpl implements IBaseUserService {

    @Autowired
    SysUserMapper userMapper;

    /**
     * 公共平台需要的方法
     *
     * @param userId
     * @return
     */
    @Override
    public String getNameByUserId(String userId) {
        SysUser user = userMapper.selectById(userId);
        return null == user ? "" : user.getRealName();
    }
}
