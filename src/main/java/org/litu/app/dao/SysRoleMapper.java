package org.litu.app.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.litu.app.entity.SysRole;
import org.litu.base.dao.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色/岗位Mapper 接口
 * </p>
 *
 * @author ltgk
 * @since 2018-10-19
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    public List<SysRole> showRoleList(@Param("keyword") String keyword, @Param("fType") Integer fType);

    @Select(" select r.* from Sys_Role r " +
            " inner join Sys_UserRole ur on r.F_Id = ur.F_RoleId" +
            " where ur.F_UserId = #{userId}")
    @ResultMap("BaseResultMap")
    public List<SysRole> userRoles(@Param("userId") String userId);
}
