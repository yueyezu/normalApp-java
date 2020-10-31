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
 * 组织架构表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Organize")
@ApiModel(value = "SysOrganize对象", description = "组织架构表")
public class SysOrganize extends BaseTreeEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String fCode;

    @ApiModelProperty(value = "部门说明")
    private String fDescription;

    @ApiModelProperty(value = "负责人")
    private String fManager;

    @ApiModelProperty(value = "电话")
    private String fPhone;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;

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

    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String getfManager() {
        return fManager;
    }

    public void setfManager(String fManager) {
        this.fManager = fManager;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
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

    @Override
    public String toString() {
        return "SysOrganize{" +
                ", fCode=" + fCode +
                ", fLayers=" + fLayers +
                ", fName=" + fName +
                "fParentid=" + fParentid +
                ", fDescription=" + fDescription +
                ", fManager=" + fManager +
                ", fPhone=" + fPhone +
                ", fSortnum=" + fSortnum +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fModifyby=" + fModifyby +
                ", fModifytime=" + fModifytime +
                "}";
    }
}
