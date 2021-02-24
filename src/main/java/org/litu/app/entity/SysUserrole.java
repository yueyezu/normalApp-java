package org.litu.app.entity;

import org.litu.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_UserRole")
@ApiModel(value="SysUserrole对象", description="用户角色表")
public class SysUserrole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "角色编号")
    private String roleId;


}
