package org.litu.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.litu.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 系统配置表
 * 对于系统的配置信息存储到数据库中的表
 * 主要是为了给用户提供一个可以通过界面管理的功能
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Configs")
@ApiModel(value = "SysConfigs对象", description = "系统配置表对于系统的配置信息存储到数据库中的表主要是为了给用户提供一个可以通过界面管理的功能")
public class SysConfigs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置组名称")
    private String fGroup;

    @ApiModelProperty(value = "配置的名称")
    private String fName;

    @ApiModelProperty(value = "配置的key值")
    private String fKey;

    @ApiModelProperty(value = "配置Value")
    private String fValue;

    @ApiModelProperty(value = "配置Value的类型，值为：text  文本 numberbox  数字 datebox 日期框 checkbox 复选型")
    @TableField("f_valueType")
    private String fValuetype;

    @ApiModelProperty(value = "描述")
    private String fDescription;

    @ApiModelProperty(value = "备注")
    private String fRemark;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;

    @ApiModelProperty(value = "修改人")
    @TableField("f_modifyBy")
    private String fModifyby;

    @ApiModelProperty(value = "修改时间")
    @TableField("f_modifyTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fModifytime;


    public String getfGroup() {
        return fGroup;
    }

    public void setfGroup(String fGroup) {
        this.fGroup = fGroup;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfKey() {
        return fKey;
    }

    public void setfKey(String fKey) {
        this.fKey = fKey;
    }

    public String getfValue() {
        return fValue;
    }

    public void setfValue(String fValue) {
        this.fValue = fValue;
    }

    public String getfValuetype() {
        return fValuetype;
    }

    public void setfValuetype(String fValuetype) {
        this.fValuetype = fValuetype;
    }

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
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

    @Override
    public String toString() {
        return "SysConfigs{" +
                "fGroup=" + fGroup +
                ", fName=" + fName +
                ", fKey=" + fKey +
                ", fValue=" + fValue +
                ", fValuetype=" + fValuetype +
                ", fDescription=" + fDescription +
                ", fRemark=" + fRemark +
                ", fSortnum=" + fSortnum +
                ", fModifyby=" + fModifyby +
                ", fModifytime=" + fModifytime +
                "}";
    }
}
