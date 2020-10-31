package org.litu.app.entity;

import org.litu.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 组织岗位表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_RoleOrganize")
@ApiModel(value="SysRoleorganize对象", description="组织岗位表")
public class SysRoleorganize extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织主键")
    @TableField("f_organizeId")
    private String fOrganizeid;

    @ApiModelProperty(value = "角色编号")
    @TableField("f_roleId")
    private String fRoleid;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;


    public String getfOrganizeid() {
        return fOrganizeid;
    }

    public void setfOrganizeid(String fOrganizeid) {
        this.fOrganizeid = fOrganizeid;
    }

    public String getfRoleid() {
        return fRoleid;
    }

    public void setfRoleid(String fRoleid) {
        this.fRoleid = fRoleid;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
    }

    @Override
    public String toString() {
        return "SysRoleorganize{" +
                "fOrganizeid=" + fOrganizeid +
                ", fRoleid=" + fRoleid +
                ", fSortnum=" + fSortnum +
                "}";
    }
}
