package org.litu.app.dao;

import org.apache.ibatis.annotations.Param;
import org.litu.app.entity.SysUser;
import org.litu.app.entity.SysUserEx;
import org.litu.base.dao.BaseMapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 * 用户Mapper 接口
 * </p>
 *
 * @author ltgk
 * @since 2018-10-23
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

}
