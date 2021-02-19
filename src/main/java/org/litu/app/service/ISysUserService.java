package org.litu.app.service;

import org.litu.app.entity.SysUser;
import org.litu.base.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * user服务类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-17
 */
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 根据账户名查找用户
     *
     * @param account
     * @return
     */
    public SysUser getByAccount(String account);

    /**
     * 获取用户的树列表信息
     *
     * @param deptId
     * @return
     */
    List<Map<String, String>> userTree(String deptId);

    /**
     * 更换当前登陆用户头像
     *
     * @param userId  用户ID
     * @param photoId 用户头像ID
     * @return 如果替换成功，则返回true
     */
    boolean updatePhoto(String userId, String photoId);

}
