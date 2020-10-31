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
 * 字典值表
 * </p>
 *
 * @author ltgk
 * @since 2020-10-28
 */
@TableName("Sys_DictItem")
@ApiModel(value="SysDictitem对象", description="字典值表")
public class SysDictitem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典主键")
    @TableField("f_dictId")
    private String fDictid;

    @ApiModelProperty(value = "编码")
    private String fCode;

    @ApiModelProperty(value = "名称")
    private String fName;

    @ApiModelProperty(value = "描述")
    private String fDescription;

    @ApiModelProperty(value = "有效标志", example = "1")
    @TableField("f_enabledFlag")
    private Integer fEnabledflag;

    @ApiModelProperty(value = "是否默认", example = "1")
    @TableField("f_isDefault")
    private Integer fIsdefault;

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

    @ApiModelProperty(value = "预留字段1")
    private String fField1;

    @ApiModelProperty(value = "预留字段2")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fField2;


    public String getfDictid() {
        return fDictid;
    }

    public void setfDictid(String fDictid) {
        this.fDictid = fDictid;
    }

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

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public Integer getfEnabledflag() {
        return fEnabledflag;
    }

    public void setfEnabledflag(Integer fEnabledflag) {
        this.fEnabledflag = fEnabledflag;
    }

    public Integer getfIsdefault() {
        return fIsdefault;
    }

    public void setfIsdefault(Integer fIsdefault) {
        this.fIsdefault = fIsdefault;
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
        return "SysDictitem{" +
                "fDictid=" + fDictid +
                ", fCode=" + fCode +
                ", fName=" + fName +
                ", fDescription=" + fDescription +
                ", fEnabledflag=" + fEnabledflag +
                ", fIsdefault=" + fIsdefault +
                ", fSortnum=" + fSortnum +
                ", fCreateby=" + fCreateby +
                ", fCreatetime=" + fCreatetime +
                ", fModifyby=" + fModifyby +
                ", fModifytime=" + fModifytime +
                ", fField1=" + fField1 +
                ", fField2=" + fField2 +
                "}";
    }
}
