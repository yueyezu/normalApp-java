package org.litu.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.litu.base.entity.BaseTreeEntity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 树节点
 *
 * @param <T>
 */
@ApiModel(value = "树节点", description = "继承BaseTreeEntity，调用Serializable接口")
public class TreeNodeVo<T extends BaseTreeEntity> extends TreeVo implements Serializable {

    private static final long serialVersionUID = -1489161744395151433L;
    @ApiModelProperty(value = "元数据", required = false)
    protected T data;

    // 子节点
    protected List<TreeNodeVo<T>> children = new CopyOnWriteArrayList<>();

    /**
     * 构造没有Data的树
     *
     * @param node 树实例
     */
    public void initNoData(T node) {
        if (node != null) {
            id = node.getfId();
            parent = node.getfParentid();
            text = node.getfName();
        }
    }

    /**
     * 构造树
     *
     * @param node 树实例
     */
    public void init(T node) {
        if (node != null) {
            id = node.getfId();
            parent = node.getfParentid();
            text = node.getfName();
            data = node;
        }
    }


    /**
     * 添加子节点
     *
     * @param child 子节点
     */
    public void addChild(TreeNodeVo<T> child) {
        children.add(child);
    }

    /**
     * 获取元数据
     *
     * @return 元数据
     */
//	@JSONField(serialize = false)
    public T getData() {
        return data;
    }

    /**
     * 设置元数据
     *
     * @param data 要设置的元数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取子节点
     *
     * @return 子节点
     */
    public List<TreeNodeVo<T>> getChildren() {
        return children;
    }

    /**
     * 设置子节点
     *
     * @param childs 要设置的子节点
     */
    public void setChildren(List<TreeNodeVo<T>> childs) {
        this.children = childs;
    }

    /**
     * 改成String类型输出，便于操作
     *
     * @return String类型的数据
     */
    @Override
    public String toString() {
        return "TreeNodeVo [id=" + id + ", parent=" + parent + ", text=" + text + ", icon=" + icon + ", data=" + data + "]";
    }

}
