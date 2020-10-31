package org.litu.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.litu.base.entity.BaseTreeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Menu")
@ApiModel(value = "SysMenu对象", description = "系统菜单表")
public class SysMenu extends BaseTreeEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统类型的编码信息")
    @TableField("f_systemCode")
    private String fSystemcode;

    @ApiModelProperty(value = "模块、功能、按钮", example = "1")
    private Integer fType;

    @ApiModelProperty(value = "权限标识")
    private String fCode;

    @ApiModelProperty(value = "图标")
    private String fIcon;

    @ApiModelProperty(value = "连接路径")
    private String fUrl;

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

    @ApiModelProperty(value = "预留字段1", example = "1")
    private Integer fField1;

    @ApiModelProperty(value = "预留字段2")
    private String fField2;

    public String getfSystemcode() {
        return fSystemcode;
    }

    public void setfSystemcode(String fSystemcode) {
        this.fSystemcode = fSystemcode;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getfIcon() {
        return fIcon;
    }

    public void setfIcon(String fIcon) {
        this.fIcon = fIcon;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
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

    public Integer getfField1() {
        return fField1;
    }

    public void setfField1(Integer fField1) {
        this.fField1 = fField1;
    }

    public String getfField2() {
        return fField2;
    }

    public void setfField2(String fField2) {
        this.fField2 = fField2;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "fParentid=" + fParentid +
                ", fLayers=" + fLayers +
                ", fSystemcode=" + fSystemcode +
                ", fType=" + fType +
                ", fCode=" + fCode +
                ", fName=" + fName +
                ", fIcon=" + fIcon +
                ", fUrl=" + fUrl +
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
