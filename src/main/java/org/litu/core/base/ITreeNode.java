package org.litu.core.base;

import java.util.List;

public interface ITreeNode<T extends ITreeNode<T>> {

    static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 获取节点ID
     */
    String getId();

    /**
     * 获取父节点ID,若无父节点,若当前节点没有父节点，即当前节点是根节点，需返回null或者空字符串
     */
    String getParentId();

    /**
     * 添加子节点
     *
     * @param child
     */
    void addChild(T child);

    /**
     * 设置所有子节点
     *
     * @param children
     */
    void setChildren(List<T> children);

    /**
     * 设置层级
     *
     * @param level
     */
    void setLevel(int level);
}
