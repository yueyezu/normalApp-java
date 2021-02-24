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
 * 系统信息表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_System")
@ApiModel(value="SysSystem对象", description="系统信息表")
public class SysSystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "系统类型")
    private String type;

    @ApiModelProperty(value = "当前版本")
    private String version;

    @ApiModelProperty(value = "访问密码, 第三方访问时，认证使用")
    private String secret;

    @ApiModelProperty(value = "建设单位")
    private String devOrg;

    @ApiModelProperty(value = "建设时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date devTime;

    @ApiModelProperty(value = "有效标志", example = "1")
    private Integer enableFlag;

    @ApiModelProperty(value = "允许删除", example = "1")
    private Integer enableDelete;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;


}
