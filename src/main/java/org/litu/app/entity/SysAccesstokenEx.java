package org.litu.app.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SysAccesstoken对象", description = "授权令牌表")
public class SysAccesstokenEx extends SysAccesstoken {
    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    public SysAccesstokenEx(SysAccesstoken sysAccesstoken) {
        this.setfId(sysAccesstoken.getfId());
        this.setfClienttype(sysAccesstoken.getfClienttype());
        this.setfUserid(sysAccesstoken.getfUserid());
        this.setfToken(sysAccesstoken.getfToken());
        this.setfRefreshtoken(sysAccesstoken.getfRefreshtoken());
        this.setfClientmcode(sysAccesstoken.getfClientmcode());
        this.setfClientip(sysAccesstoken.getfClientip());
        this.setfClientmac(sysAccesstoken.getfClientmac());
        this.setfCreatetime(sysAccesstoken.getfCreatetime());
        this.setfModifytime(sysAccesstoken.getfModifytime());
        this.setfEnabletime(sysAccesstoken.getfEnabletime());
        this.setfEnablerefreshtime(sysAccesstoken.getfEnablerefreshtime());
        this.setfEnabledflag(sysAccesstoken.getfEnabledflag());
        this.setfRemark(sysAccesstoken.getfRemark());
        this.setfField1(sysAccesstoken.getfField1());
        this.setfField2(sysAccesstoken.getfField2());
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
