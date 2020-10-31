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
 * 系统信息表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_System")
@ApiModel(value="SysSystem对象", description="系统信息表")
public class SysSystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    private String fCode;

    @ApiModelProperty(value = "名称")
    private String fName;

    @ApiModelProperty(value = "系统类型")
    private String fType;

    @ApiModelProperty(value = "当前版本")
    private String fVersion;

    @ApiModelProperty(value = "访问密码, 第三方访问时，认证使用")
    private String fSecret;

    @ApiModelProperty(value = "建设单位")
    @TableField("f_devOrg")
    private String fDevorg;

    @ApiModelProperty(value = "建设时间")
    @TableField("f_devTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fDevtime;

    @ApiModelProperty(value = "有效标志", example = "1")
    @TableField("f_enabledFlag")
    private Integer fEnabledflag;

    @ApiModelProperty(value = "允许删除", example = "1")
    @TableField("f_enableDelete")
    private Integer fEnabledelete;

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

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfVersion() {
        return fVersion;
    }

    public void setfVersion(String fVersion) {
        this.fVersion = fVersion;
    }

    public String getfSecret() {
        return fSecret;
    }

    public void setfSecret(String fSecret) {
        this.fSecret = fSecret;
    }

    public String getfDevorg() {
        return fDevorg;
    }

    public void setfDevorg(String fDevorg) {
        this.fDevorg = fDevorg;
    }

    public Date getfDevtime() {
        return fDevtime;
    }

    public void setfDevtime(Date fDevtime) {
        this.fDevtime = fDevtime;
    }

    public Integer getfEnabledflag() {
        return fEnabledflag;
    }

    public void setfEnabledflag(Integer fEnabledflag) {
        this.fEnabledflag = fEnabledflag;
    }

    public Integer getfEnabledelete() {
        return fEnabledelete;
    }

    public void setfEnabledelete(Integer fEnabledelete) {
        this.fEnabledelete = fEnabledelete;
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
        return "SysSystem{" +
                "fCode=" + fCode +
                ", fName=" + fName +
                ", fType=" + fType +
                ", fVersion=" + fVersion +
                ", fSecret=" + fSecret +
                ", fDevorg=" + fDevorg +
                ", fDevtime=" + fDevtime +
                ", fEnabledflag=" + fEnabledflag +
                ", fEnabledelete=" + fEnabledelete +
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
