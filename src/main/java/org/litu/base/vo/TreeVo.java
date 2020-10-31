package org.litu.base.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TreeVo implements Serializable {


    @ApiModelProperty(value = "id", required = true)
    protected String id;
    @ApiModelProperty(value = "父节点id", required = false)
    protected String parent;
    @ApiModelProperty(value = "文本", required = true)
    protected String text = "";
    @ApiModelProperty(value = "样式", required = false)
    protected String icon;

    public String getId() {
        return id;
    }

    /**
     * 获取节点ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取父节点ID,若无父节点,若当前节点没有父节点，即当前节点是根节点，需返回null或者空字符串
     */
    public String getParent() {
        return parent;
    }

    /**
     * 父节点的set方法
     *
     * @param parent 要填写的父节点
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * 获取文本
     *
     * @return 文本
     */
    public String getText() {
        return text;
    }

    /**
     * 文本set方法
     *
     * @param text 要填入的文本
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取样式
     *
     * @return 样式
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置样式
     *
     * @param icon 要设置的样式
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
