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
 * 用户角色表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_UserRole")
@ApiModel(value="SysUserrole对象", description="用户角色表")
public class SysUserrole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    @TableField("f_userId")
    private String fUserid;

    @ApiModelProperty(value = "角色编号")
    @TableField("f_roleId")
    private String fRoleid;


    public String getfUserid() {
        return fUserid;
    }

    public void setfUserid(String fUserid) {
        this.fUserid = fUserid;
    }

    public String getfRoleid() {
        return fRoleid;
    }

    public void setfRoleid(String fRoleid) {
        this.fRoleid = fRoleid;
    }

    @Override
    public String toString() {
        return "SysUserrole{" +
                "fUserid=" + fUserid +
                ", fRoleid=" + fRoleid +
                "}";
    }
}
