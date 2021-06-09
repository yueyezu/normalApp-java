package org.litu.app.entity.system;

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
 * 字典表
 * </p>
 *
 * @author yueye
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysDict对象", description = "字典表")
public class SysDict extends BaseTreeEntity<SysDict> implements ITreeNode<SysDict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序码", example = "1")
    private Integer sortNum;

    @ApiModelProperty(value = "允许删除", example = "1")
    private Integer enableDelete;

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
