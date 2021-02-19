package org.litu.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * entity实体的基类
 */
@Data
public abstract class BaseTreeEntity extends BaseEntity {

    @ApiModelProperty(value = "名称")
    protected String name;

    @ApiModelProperty(value = "父级")
    protected String parentId;

    @ApiModelProperty(value = "层次：#编码#|#编码#")
    protected String layers;
}
