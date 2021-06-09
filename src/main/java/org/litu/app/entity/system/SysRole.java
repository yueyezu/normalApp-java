package org.litu.app.entity.system;

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
 * 角色岗位表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysRole对象", description="角色岗位表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "分类：1、角色，2、岗位", example = "1")
    private Integer type;

    @ApiModelProperty(value = "排序码", example = "1")
    private Integer sortNum;

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
