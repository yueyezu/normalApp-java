package org.litu.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.litu.app.entity.system.SysRole;

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

    @ResultMap("BaseResultMap")
    public List<SysRole> userRoles(@Param("userId") String userId);
}
