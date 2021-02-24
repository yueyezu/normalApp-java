package org.litu.app.entity;

import org.litu.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户登录表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_UserLogin")
@ApiModel(value="SysUserlogin对象", description="用户登录表")
public class SysUserlogin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密钥")
    private String secretKey;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastVisitTime;

    @ApiModelProperty(value = "最后密码修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date changePassTime;

    @ApiModelProperty(value = "允许多端登录", example = "1")
    private Integer multiuserLogin;

    @ApiModelProperty(value = "登录次数", example = "1")
    private Integer logonCount;

    @ApiModelProperty(value = "在线状态", example = "1")
    private Integer loginStatus;

    @ApiModelProperty(value = "密保问题")
    private String question;

    @ApiModelProperty(value = "问题答案")
    private String answer;

    @ApiModelProperty(value = "是否限制访问", example = "1")
    private Integer enableLogin;


}
