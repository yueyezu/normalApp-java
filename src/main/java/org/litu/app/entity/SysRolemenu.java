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
 * 权限角色表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_RoleMenu")
@ApiModel(value="SysRolemenu对象", description="权限角色表")
public class SysRolemenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编号")
    @TableField("f_roleId")
    private String fRoleid;

    @ApiModelProperty(value = "权限编号")
    @TableField("f_menuId")
    private String fMenuid;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;


    public String getfRoleid() {
        return fRoleid;
    }

    public void setfRoleid(String fRoleid) {
        this.fRoleid = fRoleid;
    }

    public String getfMenuid() {
        return fMenuid;
    }

    public void setfMenuid(String fMenuid) {
        this.fMenuid = fMenuid;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
    }

    @Override
    public String toString() {
        return "SysRolemenu{" +
                "fRoleid=" + fRoleid +
                ", fMenuid=" + fMenuid +
                ", fSortnum=" + fSortnum +
                "}";
    }
}
