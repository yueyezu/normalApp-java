package org.litu.app.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.litu.core.base.BaseTreeEntity;
import org.litu.core.base.ITreeNode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 组织架构表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Sys_Organize")
@ApiModel(value = "SysOrganize对象", description = "组织架构表")
public class SysOrganize extends BaseTreeEntity<SysOrganize> implements ITreeNode<SysOrganize> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "部门说明")
    private String description;

    @ApiModelProperty(value = "负责人")
    private String manager;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "排序码", example = "1")
    private Integer sortNum;

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
