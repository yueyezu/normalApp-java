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
 * 系统版本管理表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_SystemVersion")
@ApiModel(value="SysSystemversion对象", description="系统版本管理表")
public class SysSystemversion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统类型的编码信息")
    @TableField("f_systemCode")
    private String fSystemcode;

    @ApiModelProperty(value = "版本号")
    private String fVersion;

    @ApiModelProperty(value = "更新时间")
    @TableField("f_updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fUpdatedate;

    @ApiModelProperty(value = "对当前版本较上一版本更新的内容说明")
    @TableField("f_updateNote")
    private String fUpdatenote;

    @ApiModelProperty(value = "当前版本软件对应的下载地址，主要是对于app会有使用")
    @TableField("f_downloadPath")
    private String fDownloadpath;

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

    public String getfVersion() {
        return fVersion;
    }

    public void setfVersion(String fVersion) {
        this.fVersion = fVersion;
    }

    public Date getfUpdatedate() {
        return fUpdatedate;
    }

    public void setfUpdatedate(Date fUpdatedate) {
        this.fUpdatedate = fUpdatedate;
    }

    public String getfUpdatenote() {
        return fUpdatenote;
    }

    public void setfUpdatenote(String fUpdatenote) {
        this.fUpdatenote = fUpdatenote;
    }

    public String getfDownloadpath() {
        return fDownloadpath;
    }

    public void setfDownloadpath(String fDownloadpath) {
        this.fDownloadpath = fDownloadpath;
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
        return "SysSystemversion{" +
                "fSystemcode=" + fSystemcode +
                ", fVersion=" + fVersion +
                ", fUpdatedate=" + fUpdatedate +
                ", fUpdatenote=" + fUpdatenote +
                ", fDownloadpath=" + fDownloadpath +
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
