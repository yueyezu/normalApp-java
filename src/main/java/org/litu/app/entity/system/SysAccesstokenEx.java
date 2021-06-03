package org.litu.app.entity.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysAccesstoken对象", description = "授权令牌表")
public class SysAccesstokenEx extends SysAccesstoken {
    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    public SysAccesstokenEx(SysAccesstoken sysAccesstoken) {
        this.setId(sysAccesstoken.getId());
        this.setClientType(sysAccesstoken.getClientType());
        this.setUserId(sysAccesstoken.getUserId());
        this.setToken(sysAccesstoken.getToken());
        this.setRefreshToken(sysAccesstoken.getRefreshToken());
        this.setClientMcode(sysAccesstoken.getClientMcode());
        this.setClientIp(sysAccesstoken.getClientIp());
        this.setClientMac(sysAccesstoken.getClientMac());
        this.setCreateTime(sysAccesstoken.getCreateTime());
        this.setModifyTime(sysAccesstoken.getModifyTime());
        this.setEnableTime(sysAccesstoken.getEnableTime());
        this.setEnableRefreshTime(sysAccesstoken.getEnableRefreshTime());
        this.setEnableFlag(sysAccesstoken.getEnableFlag());
        this.setRemark(sysAccesstoken.getRemark());
    }
}
