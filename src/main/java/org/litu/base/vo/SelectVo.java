package org.litu.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 下拉选择框
 */
@ApiModel(value = "下拉选择框")
public class SelectVo implements Serializable {

    private static final long serialVersionUID = 1123123L;

    @ApiModelProperty(value = "id")
    protected String id;
    @ApiModelProperty(value = "value")
    protected String value;
    @ApiModelProperty(value = "选择框中文本")
    protected String text = "";

    /**
     * 构造函数
     *
     * @param id    值、 id信息。
     * @param value 值信息。
     * @param text  显示的文本
     */
    public SelectVo(String id, String value, String text) {
        this.id = id;
        this.value = value;
        this.text = text;
    }

    public SelectVo(String id, String text) {
        this.id = id;
        this.value = id;
        this.text = text;
    }

    public SelectVo() {

    }


    /**
     * id的get方法
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取值信息
     *
     * @return
     */
    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 文本的的get方法
     *
     * @return 文本
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
