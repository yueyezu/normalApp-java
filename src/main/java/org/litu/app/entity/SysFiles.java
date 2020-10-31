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
 * 系统附件表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Files")
@ApiModel(value = "SysFiles对象", description = "系统附件表")
public class SysFiles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "原文件名，带后缀")
    @TableField("f_originName")
    private String fOriginname;

    @ApiModelProperty(value = "存储位置")
    private String fLocation;

    @ApiModelProperty(value = "文件类型")
    @TableField("f_fileType")
    private String fFiletype;

    @ApiModelProperty(value = "文件大小kb")
    @TableField("f_fileSize")
    private Long fFilesize;

    @ApiModelProperty(value = "附加信息")
    private String fAddition;

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

    @ApiModelProperty(value = "预留字段1", example = "1")
    private Integer fField1;

    @ApiModelProperty(value = "预留字段2")
    private String fField2;


    public String getfOriginname() {
        return fOriginname;
    }

    public void setfOriginname(String fOriginname) {
        this.fOriginname = fOriginname;
    }

    public String getfLocation() {
        return fLocation;
    }

    public void setfLocation(String fLocation) {
        this.fLocation = fLocation;
    }

    public String getfFiletype() {
        return fFiletype;
    }

    public void setfFiletype(String fFiletype) {
        this.fFiletype = fFiletype;
    }

    public Long getfFilesize() {
        return fFilesize;
    }

    public void setfFilesize(Long fFilesize) {
        this.fFilesize = fFilesize;
    }

    public String getfAddition() {
        return fAddition;
    }

    public void setfAddition(String fAddition) {
        this.fAddition = fAddition;
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
        return "SysFiles{" +
                "fOriginname=" + fOriginname +
                ", fLocation=" + fLocation +
                ", fFiletype=" + fFiletype +
                ", fFilesize=" + fFilesize +
                ", fAddition=" + fAddition +
                ", fRemark=" + fRemark +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
