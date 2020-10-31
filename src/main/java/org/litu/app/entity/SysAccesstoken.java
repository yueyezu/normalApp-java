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
 * 授权令牌表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_AccessToken")
@ApiModel(value="SysAccesstoken对象", description="授权令牌表")
public class SysAccesstoken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权类型，通过系统定义，一般为无任何关联的两类系统进行区分")
    @TableField("f_clientType")
    private String fClienttype;

    @ApiModelProperty(value = "用户主键")
    @TableField("f_userId")
    private String fUserid;

    @ApiModelProperty(value = "令牌")
    private String fToken;

    @ApiModelProperty(value = "刷新令牌")
    @TableField("f_refreshToken")
    private String fRefreshtoken;

    @TableField("f_clientMCode")
    private String fClientmcode;

    @TableField("f_clientIp")
    private String fClientip;

    @TableField("f_clientMac")
    private String fClientmac;

    @ApiModelProperty(value = "创建时间")
    @TableField("f_createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fCreatetime;

    @ApiModelProperty(value = "刷新时间")
    @TableField("f_modifyTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fModifytime;

    @ApiModelProperty(value = "有效时间(以分钟为单位) 如果为0则表示永久有效。", example = "1")
    @TableField("f_enableTime")
    private Integer fEnabletime;

    @ApiModelProperty(value = "刷新令牌的有效时间(以天为单位) 如果为0则表示永久有效。", example = "1")
    @TableField("f_enableRefreshTime")
    private Integer fEnablerefreshtime;

    @ApiModelProperty(value = "有效标志 默认均为有效，可以人为改为无效，此时，该用户直接禁止登录", example = "1")
    @TableField("f_enabledFlag")
    private Integer fEnabledflag;

    @ApiModelProperty(value = "备注")
    private String fRemark;

    @ApiModelProperty(value = "预留字段1", example = "1")
    private Integer fField1;

    @ApiModelProperty(value = "预留字段2")
    private String fField2;


    public String getfClienttype() {
        return fClienttype;
    }

    public void setfClienttype(String fClienttype) {
        this.fClienttype = fClienttype;
    }

    public String getfUserid() {
        return fUserid;
    }

    public void setfUserid(String fUserid) {
        this.fUserid = fUserid;
    }

    public String getfToken() {
        return fToken;
    }

    public void setfToken(String fToken) {
        this.fToken = fToken;
    }

    public String getfRefreshtoken() {
        return fRefreshtoken;
    }

    public void setfRefreshtoken(String fRefreshtoken) {
        this.fRefreshtoken = fRefreshtoken;
    }

    public String getfClientmcode() {
        return fClientmcode;
    }

    public void setfClientmcode(String fClientmcode) {
        this.fClientmcode = fClientmcode;
    }

    public String getfClientip() {
        return fClientip;
    }

    public void setfClientip(String fClientip) {
        this.fClientip = fClientip;
    }

    public String getfClientmac() {
        return fClientmac;
    }

    public void setfClientmac(String fClientmac) {
        this.fClientmac = fClientmac;
    }

    public Date getfCreatetime() {
        return fCreatetime;
    }

    public void setfCreatetime(Date fCreatetime) {
        this.fCreatetime = fCreatetime;
    }

    public Date getfModifytime() {
        return fModifytime;
    }

    public void setfModifytime(Date fModifytime) {
        this.fModifytime = fModifytime;
    }

    public Integer getfEnabletime() {
        return fEnabletime;
    }

    public void setfEnabletime(Integer fEnabletime) {
        this.fEnabletime = fEnabletime;
    }

    public Integer getfEnablerefreshtime() {
        return fEnablerefreshtime;
    }

    public void setfEnablerefreshtime(Integer fEnablerefreshtime) {
        this.fEnablerefreshtime = fEnablerefreshtime;
    }

    public Integer getfEnabledflag() {
        return fEnabledflag;
    }

    public void setfEnabledflag(Integer fEnabledflag) {
        this.fEnabledflag = fEnabledflag;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
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
        return "SysAccesstoken{" +
                "fClienttype=" + fClienttype +
                ", fUserid=" + fUserid +
                ", fToken=" + fToken +
                ", fRefreshtoken=" + fRefreshtoken +
                ", fClientmcode=" + fClientmcode +
                ", fClientip=" + fClientip +
                ", fClientmac=" + fClientmac +
                ", fCreatetime=" + fCreatetime +
                ", fModifytime=" + fModifytime +
                ", fEnabletime=" + fEnabletime +
                ", fEnablerefreshtime=" + fEnablerefreshtime +
                ", fEnabledflag=" + fEnabledflag +
                ", fRemark=" + fRemark +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
