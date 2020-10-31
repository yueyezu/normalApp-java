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
 * 系统日志表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_Logs")
@ApiModel(value="SysLogs对象", description="系统日志表")
public class SysLogs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统的编码信息")
    @TableField("f_systemCode")
    private String fSystemcode;

    @ApiModelProperty(value = "功能模块")
    private String fModule;

    @ApiModelProperty(value = "操作类型")
    @TableField("f_optType")
    private String fOpttype;

    @ApiModelProperty(value = "操作说明")
    @TableField("f_optContent")
    private String fOptcontent;

    @ApiModelProperty(value = "IP地址")
    @TableField("f_ipAddress")
    private String fIpaddress;

    @ApiModelProperty(value = "IP城市")
    @TableField("f_ipCity")
    private String fIpcity;

    @ApiModelProperty(value = "操作用户")
    @TableField("f_createBy")
    private String fCreateby;

    @ApiModelProperty(value = "操作时间")
    @TableField("f_createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fCreatetime;

    @ApiModelProperty(value = "预留字段1")
    private String fField1;

    @ApiModelProperty(value = "预留字段2")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fField2;


    public String getfSystemcode() {
        return fSystemcode;
    }

    public void setfSystemcode(String fSystemcode) {
        this.fSystemcode = fSystemcode;
    }

    public String getfModule() {
        return fModule;
    }

    public void setfModule(String fModule) {
        this.fModule = fModule;
    }

    public String getfOpttype() {
        return fOpttype;
    }

    public void setfOpttype(String fOpttype) {
        this.fOpttype = fOpttype;
    }

    public String getfOptcontent() {
        return fOptcontent;
    }

    public void setfOptcontent(String fOptcontent) {
        this.fOptcontent = fOptcontent;
    }

    public String getfIpaddress() {
        return fIpaddress;
    }

    public void setfIpaddress(String fIpaddress) {
        this.fIpaddress = fIpaddress;
    }

    public String getfIpcity() {
        return fIpcity;
    }

    public void setfIpcity(String fIpcity) {
        this.fIpcity = fIpcity;
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
        return "SysLogs{" +
                "fSystemcode=" + fSystemcode +
                ", fModule=" + fModule +
                ", fOpttype=" + fOpttype +
                ", fOptcontent=" + fOptcontent +
                ", fIpaddress=" + fIpaddress +
                ", fIpcity=" + fIpcity +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
