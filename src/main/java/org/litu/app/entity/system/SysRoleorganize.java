package org.litu.app.entity.system;

import org.litu.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织岗位表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_RoleOrganize")
@ApiModel(value="SysRoleorganize对象", description="组织岗位表")
public class SysRoleorganize extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织主键")
    private String organizeId;

    @ApiModelProperty(value = "角色编号")
    private String roleId;

    @ApiModelProperty(value = "排序码", example = "1")
    private Integer sortNum;


}
