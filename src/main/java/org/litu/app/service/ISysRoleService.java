package org.litu.app.service;

import org.litu.app.entity.SysRole;
import org.litu.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
public interface ISysRoleService extends IBaseService<SysRole> {

    /**
     * 用户角色code
     *
     * @param userId
     * @return
     */
    public List<String> userRoles(String userId);
}
