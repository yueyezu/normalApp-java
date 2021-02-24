package org.litu.app.entity;

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
 * 系统配置表
 * 对于系统的配置信息存储到数据库中的表
 * 主要是为了给用户提供一个可以通过界面管理的功能
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_Configs")
@ApiModel(value = "SysConfigs对象", description = "系统配置表 对于系统的配置信息存储到数据库中的表 主要是为了给用户提供一个可以通过界面管理的功能")
public class SysConfigs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置组名称")
    private String groups;

    @ApiModelProperty(value = "配置的名称")
    private String name;

    @ApiModelProperty(value = "配置Code")
    private String code;

    @ApiModelProperty(value = "配置Value")
    private String value;

    @ApiModelProperty(value = "配置Value的类型，值为：text-文本,numberbox-数字,datebox-日期框,checkbox-复选型")
    private String valueType;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序码", example = "1")
    private Integer sortNum;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;


}
