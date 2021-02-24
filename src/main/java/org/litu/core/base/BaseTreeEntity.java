package org.litu.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * entity实体的基类
 */
@Data
public abstract class BaseTreeEntity<T extends BaseTreeEntity> extends BaseEntity {

    @ApiModelProperty(value = "名称")
    protected String name;

    @ApiModelProperty(value = "父级")
    protected String parentId;

    @ApiModelProperty(value = "层次：#编码#|#编码#")
    protected String layers;

    /**
     * 组织架构子级树
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<T> children;

    @TableField(exist = false)
    @JsonIgnore
    public int level;

    /**
     * 添加子节点
     *
     * @param child
     */
    public void addChild(T child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
