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
 * 用户表
 *
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_User")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户帐号")
    private String fAccount;

    @ApiModelProperty(value = "姓名")
    @TableField("f_realName")
    private String fRealname;

    @ApiModelProperty(value = "昵称")
    @TableField("f_nickName")
    private String fNickname;

    @ApiModelProperty(value = "头像")
    private String fPhoto;

    @ApiModelProperty(value = "性别")
    private String fSex;

    @ApiModelProperty(value = "微信")
    @TableField("f_weChat")
    private String fWechat;

    @ApiModelProperty(value = "联系方式")
    private String fPhone;

    @ApiModelProperty(value = "邮件")
    private String fEmail;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fBirthday;

    @ApiModelProperty(value = "部门主键")
    @TableField("f_departmentId")
    private String fDepartmentid;

    @ApiModelProperty(value = "岗位主键")
    @TableField("f_roleId")
    private String fRoleid;

    @ApiModelProperty(value = "排序码", example = "1")
    @TableField("f_sortNum")
    private Integer fSortnum;

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

    @ApiModelProperty(value = "删除标志", example = "1")
    @TableField("f_deleteFlag")
    private Integer fDeleteflag;

    @ApiModelProperty(value = "删除人")
    @TableField("f_deleteBy")
    private String fDeleteby;

    @ApiModelProperty(value = "删除时间")
    @TableField("f_deleteTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fDeletetime;

    @ApiModelProperty(value = "预留字段1", example = "1")
    private Integer fField1;

    @ApiModelProperty(value = "预留字段2")
    private String fField2;

    @ApiModelProperty(value = "预留字段3")
    private String fField3;

    @ApiModelProperty(value = "预留字段4")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fField4;


    public String getfAccount() {
        return fAccount;
    }

    public void setfAccount(String fAccount) {
        this.fAccount = fAccount;
    }

    public String getfRealname() {
        return fRealname;
    }

    public void setfRealname(String fRealname) {
        this.fRealname = fRealname;
    }

    public String getfNickname() {
        return fNickname;
    }

    public void setfNickname(String fNickname) {
        this.fNickname = fNickname;
    }

    public String getfPhoto() {
        return fPhoto;
    }

    public void setfPhoto(String fPhoto) {
        this.fPhoto = fPhoto;
    }

    public String getfSex() {
        return fSex;
    }

    public void setfSex(String fSex) {
        this.fSex = fSex;
    }

    public String getfWechat() {
        return fWechat;
    }

    public void setfWechat(String fWechat) {
        this.fWechat = fWechat;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public String getfEmail() {
        return fEmail;
    }

    public void setfEmail(String fEmail) {
        this.fEmail = fEmail;
    }

    public Date getfBirthday() {
        return fBirthday;
    }

    public void setfBirthday(Date fBirthday) {
        this.fBirthday = fBirthday;
    }

    public String getfDepartmentid() {
        return fDepartmentid;
    }

    public void setfDepartmentid(String fDepartmentid) {
        this.fDepartmentid = fDepartmentid;
    }

    public String getfRoleid() {
        return fRoleid;
    }

    public void setfRoleid(String fRoleid) {
        this.fRoleid = fRoleid;
    }

    public Integer getfSortnum() {
        return fSortnum;
    }

    public void setfSortnum(Integer fSortnum) {
        this.fSortnum = fSortnum;
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

    public Integer getfDeleteflag() {
        return fDeleteflag;
    }

    public void setfDeleteflag(Integer fDeleteflag) {
        this.fDeleteflag = fDeleteflag;
    }

    public String getfDeleteby() {
        return fDeleteby;
    }

    public void setfDeleteby(String fDeleteby) {
        this.fDeleteby = fDeleteby;
    }

    public Date getfDeletetime() {
        return fDeletetime;
    }

    public void setfDeletetime(Date fDeletetime) {
        this.fDeletetime = fDeletetime;
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

    public String getfField3() {
        return fField3;
    }

    public void setfField3(String fField3) {
        this.fField3 = fField3;
    }

    public Date getfField4() {
        return fField4;
    }

    public void setfField4(Date fField4) {
        this.fField4 = fField4;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "fAccount=" + fAccount +
                ", fRealname=" + fRealname +
                ", fNickname=" + fNickname +
                ", fPhoto=" + fPhoto +
                ", fSex=" + fSex +
                ", fWechat=" + fWechat +
                ", fPhone=" + fPhone +
                ", fEmail=" + fEmail +
                ", fBirthday=" + fBirthday +
                ", fDepartmentid=" + fDepartmentid +
                ", fRoleid=" + fRoleid +
                ", fSortnum=" + fSortnum +
                ", fEnabledelete=" + fEnabledelete +
                ", fRemark=" + fRemark +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fModifyby=" + fModifyby +
                ", fModifytime=" + fModifytime +
                ", fDeleteflag=" + fDeleteflag +
                ", fDeleteby=" + fDeleteby +
                ", fDeletetime=" + fDeletetime +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                ", fField3=" + fField3 +
                ", fField4=" + fField4 +
                "}";
    }
}
