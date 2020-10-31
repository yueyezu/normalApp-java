package org.litu.app.entity;

import org.litu.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 角色岗位表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Role")
@ApiModel(value="SysRole对象", description="角色岗位表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String fCode;

    @ApiModelProperty(value = "名称")
    private String fName;

    @ApiModelProperty(value = "说明")
    private String fDescription;

    @ApiModelProperty(value = "分类：1、角色，2、岗位", example = "1")
    private Integer fType;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;

    @ApiModelProperty(value = "备注")
    private String fRemark;

    @ApiModelProperty(value = "创建人")
    @TableField("f_createBy")
    private String fCreateby;

    @ApiModelProperty(value = "创建时间")
    @TableField("f_createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fCreatetime;

    @ApiModelProperty(value = "修改人")
    @TableField("f_modifyBy")
    private String fModifyby;

    @ApiModelProperty(value = "修改时间")
    @TableField("f_modifyTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fModifytime;

    @ApiModelProperty(value = "预留字段1")
    private String fField1;

    @ApiModelProperty(value = "预留字段2")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fField2;


    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public String getfCreateby() {
        return fCreateby;
    }

    public void setfCreateby(String fCreateby) {
        this.fCreateby = fCreateby;
    }

    public Date getfCreatetime() {
        return fCreatetime;
    }

    public void setfCreatetime(Date fCreatetime) {
        this.fCreatetime = fCreatetime;
    }

    public String getfModifyby() {
        return fModifyby;
    }

    public void setfModifyby(String fModifyby) {
        this.fModifyby = fModifyby;
    }

    public Date getfModifytime() {
        return fModifytime;
    }

    public void setfModifytime(Date fModifytime) {
        this.fModifytime = fModifytime;
    }

    public String getfField1() {
        return fField1;
    }

    public void setfField1(String fField1) {
        this.fField1 = fField1;
    }

    public Date getfField2() {
        return fField2;
    }

    public void setfField2(Date fField2) {
        this.fField2 = fField2;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "fCode=" + fCode +
                ", fName=" + fName +
                ", fDescription=" + fDescription +
                ", fType=" + fType +
                ", fSortnum=" + fSortnum +
                ", fRemark=" + fRemark +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fModifyby=" + fModifyby +
                ", fModifytime=" + fModifytime +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
