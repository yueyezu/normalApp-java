package org.litu.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 操作日志
 */
@ApiModel(value = "操作日志")
public class LtLogsVo {
    @ApiModelProperty(value = "创建日期",required = true)
    private Date createtime = new Date();
    @ApiModelProperty(value = "模块",required = true)
    private String module = "";
    @ApiModelProperty(value = "用户ID",required = true)
    private String userId = "";
    @ApiModelProperty(value = "系统模块",required = true)
    private String systemCode = "";
    @ApiModelProperty(value = "操作内容",required = true)
    private String optcontent = "";
    @ApiModelProperty(value = "操作类型",required = true)
    private String opttype = "";
    @ApiModelProperty(value = "ip",required = false)
    private String ip = "";
    @ApiModelProperty(value = "ip所在城市",required = false)
    private String ipcity = "";

    /**
     * IP的get方法
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * IP的set方法
     * @param ip 放入的ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 创建时间的get方法
     * @return 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间的set方法
     * @param createtime 填入的创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 模块的get方法
     * @return 模块
     */
    public String getModule() {
        return module;
    }

    /**
     * 模块的set方法
     * @param module 填入的模块
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 模块的get方法
     * @return 模块
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户Id的set方法
     * @param userId 放入的用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 系统编号的get方法
     * @return 系统编号
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * 系统编号的set方法
     * @param system 填入的系统编号
     */
    public void setSystemCode(String system) {
        this.systemCode = system;
    }

    /**
     * 操作内容的的get方法
     * @return 操作内容
     */
    public String getOptcontent() {
        return optcontent;
    }

    /**
     * 操作内容的set方法
     * @param optcontent 填入的操作内容
     */
    public void setOptcontent(String optcontent) {
        this.optcontent = optcontent;
    }

    /**
     * 操作类型的get方法
     * @return 操作类型
     */
    public String getOpttype() {
        return opttype;
    }

    /**
     * 操作类型的set方法
     * @param opttype 填入的操作类型
     */
    public void setOpttype(String opttype) {
        this.opttype = opttype;
    }

    /**
     * Ip所在城市的get方法
     * @return Ip所在城市
     */
    public String getIpcity() {
        return ipcity;
    }

    /**
     * IP所在城市的set方法
     * @param ipcity  填入的IP所在城市
     */
    public void setIpcity(String ipcity) {
        this.ipcity = ipcity;
    }
}
