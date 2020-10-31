package org.litu.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

/**
 * entity实体的基类
 */
public abstract class BaseTreeEntity extends BaseEntity {

    @TableField("f_name")
    @ApiModelProperty(value = "名称")
    protected String fName;

    @TableField("f_parentId")
    @ApiModelProperty(value = "父级")
    protected String fParentid;

    @TableField("f_layers")
    @ApiModelProperty(value = "层次：#编码#|#编码#")
    protected String fLayers;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfParentid() {
        return fParentid;
    }

    public void setfParentid(String fParentid) {
        this.fParentid = fParentid;
    }

    public String getfLayers() {
        return fLayers;
    }

    public void setfLayers(String fLayers) {
        this.fLayers = fLayers;
    }
}
