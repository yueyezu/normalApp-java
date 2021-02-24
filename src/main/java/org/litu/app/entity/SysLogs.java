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
 * 系统日志表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_Logs")
@ApiModel(value="SysLogs对象", description="系统日志表")
public class SysLogs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统的编码信息")
    private String systemCode;

    @ApiModelProperty(value = "功能模块")
    private String module;

    @ApiModelProperty(value = "操作类型")
    private String optType;

    @ApiModelProperty(value = "操作说明")
    private String optContent;

    @ApiModelProperty(value = "IP地址")
    private String ipAddr;

    @ApiModelProperty(value = "IP城市")
    private String ipCity;

    @ApiModelProperty(value = "操作用户")
    private String createBy;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


}
