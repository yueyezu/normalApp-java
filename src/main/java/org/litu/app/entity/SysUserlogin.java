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
 * 用户登录表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_UserLogin")
@ApiModel(value="SysUserlogin对象", description="用户登录表")
public class SysUserlogin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    @TableField("f_userId")
    private String fUserid;

    @ApiModelProperty(value = "密码")
    private String fPassword;

    @ApiModelProperty(value = "密钥")
    private String fSecretkey;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("f_lastVisitTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fLastvisittime;

    @ApiModelProperty(value = "最后密码修改时间")
    @TableField("f_changePassTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fChangepasstime;

    @ApiModelProperty(value = "允许多端登录", example = "1")
    @TableField("f_multiUserLogin")
    private Integer fMultiuserlogin;

    @ApiModelProperty(value = "登录次数", example = "1")
    @TableField("f_logOnCount")
    private Integer fLogoncount;

    @ApiModelProperty(value = "在线状态", example = "1")
    @TableField("f_loginStatus")
    private Integer fLoginstatus;

    @ApiModelProperty(value = "密保问题")
    private String fQuestion;

    @ApiModelProperty(value = "问题答案")
    private String fAnswer;

    @ApiModelProperty(value = "是否限制访问", example = "1")
    @TableField("f_enableLogin")
    private Integer fEnablelogin;

    @ApiModelProperty(value = "预留字段1", example = "1")
    private Integer fField1;

    @ApiModelProperty(value = "预留字段2")
    private String fField2;


    public String getfUserid() {
        return fUserid;
    }

    public void setfUserid(String fUserid) {
        this.fUserid = fUserid;
    }

    public String getfPassword() {
        return fPassword;
    }

    public void setfPassword(String fPassword) {
        this.fPassword = fPassword;
    }

    public String getfSecretkey() {
        return fSecretkey;
    }

    public void setfSecretkey(String fSecretkey) {
        this.fSecretkey = fSecretkey;
    }

    public Date getfLastvisittime() {
        return fLastvisittime;
    }

    public void setfLastvisittime(Date fLastvisittime) {
        this.fLastvisittime = fLastvisittime;
    }

    public Date getfChangepasstime() {
        return fChangepasstime;
    }

    public void setfChangepasstime(Date fChangepasstime) {
        this.fChangepasstime = fChangepasstime;
    }

    public Integer getfMultiuserlogin() {
        return fMultiuserlogin;
    }

    public void setfMultiuserlogin(Integer fMultiuserlogin) {
        this.fMultiuserlogin = fMultiuserlogin;
    }

    public Integer getfLogoncount() {
        return fLogoncount;
    }

    public void setfLogoncount(Integer fLogoncount) {
        this.fLogoncount = fLogoncount;
    }

    public Integer getfLoginstatus() {
        return fLoginstatus;
    }

    public void setfLoginstatus(Integer fLoginstatus) {
        this.fLoginstatus = fLoginstatus;
    }

    public String getfQuestion() {
        return fQuestion;
    }

    public void setfQuestion(String fQuestion) {
        this.fQuestion = fQuestion;
    }

    public String getfAnswer() {
        return fAnswer;
    }

    public void setfAnswer(String fAnswer) {
        this.fAnswer = fAnswer;
    }

    public Integer getfEnablelogin() {
        return fEnablelogin;
    }

    public void setfEnablelogin(Integer fEnablelogin) {
        this.fEnablelogin = fEnablelogin;
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
        return "SysUserlogin{" +
                "fUserid=" + fUserid +
                ", fPassword=" + fPassword +
                ", fSecretkey=" + fSecretkey +
                ", fLastvisittime=" + fLastvisittime +
                ", fChangepasstime=" + fChangepasstime +
                ", fMultiuserlogin=" + fMultiuserlogin +
                ", fLogoncount=" + fLogoncount +
                ", fLoginstatus=" + fLoginstatus +
                ", fQuestion=" + fQuestion +
                ", fAnswer=" + fAnswer +
                ", fEnablelogin=" + fEnablelogin +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
