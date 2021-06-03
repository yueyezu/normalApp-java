package org.litu.app.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.litu.core.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 授权令牌表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_AccessToken")
@ApiModel(value = "SysAccesstoken对象", description = "授权令牌表")
public class SysAccesstoken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权类型，通过系统定义，一般为无任何关联的两类系统进行区分")
    private String clientType;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "令牌")
    private String token;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    private String clientMcode;

    private String clientIp;

    private String clientMac;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "刷新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;

    @ApiModelProperty(value = "有效时间(以分钟为单位) 如果为0则表示永久有效。", example = "1")
    private Integer enableTime;

    @ApiModelProperty(value = "刷新令牌的有效时间(以天为单位) 如果为0则表示永久有效。", example = "1")
    private Integer enableRefreshTime;

    @ApiModelProperty(value = "有效标志 默认均为有效，可以人为改为无效，此时，该用户直接禁止登录", example = "1")
    private Integer enableFlag;

    @ApiModelProperty(value = "备注")
    private String remark;


}
