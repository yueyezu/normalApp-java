package org.litu.core.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Token中存储的用户信息，需要为该类的子类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户缓存信息")
public class UserInfo {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机")
    private String phone;
    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 微信
     */
    @ApiModelProperty(value = "微信", required = true)
    private String wechat;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String photo;
    /**
     * 组织Code
     */
    @ApiModelProperty(value = "组织Code")
    private String orgCode;
    /**
     * 岗位Code
     */
    @ApiModelProperty(value = "岗位Code")
    private String postCode;
    /**
     * 角色Code列表
     */
    @ApiModelProperty(value = "角色Code列表")
    private List<String> roles = new ArrayList<>();
}