package org.litu.core.base;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 树相关操作
 */
public class TreeUtil {
    /**
     * 构建树结构-循环判断
     * 根据引用，不断添加父级方式构造树。
     * 不附加level信息
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends ITreeNode<T>> T buildOne(List<T> nodeList) {
        if (nodeList.size() == 1) {
            return nodeList.get(0);
        }

        List<T> treeNodes = TreeUtil.build(nodeList);
        if (treeNodes.size() == 1) {
            return treeNodes.get(0);
        } else {
            T t = (T) T.newInstance(nodeList.get(0).getClass());
            t.setChildren(treeNodes);
            return t;
        }
    }

    /**
     * 构建树结构-循环判断
     * 根据引用，不断添加父级方式构造树。
     * 不处理level信息
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends ITreeNode<T>> List<T> build(List<T> nodeList) {
        if (nodeList == null) {
            return null;
        }
        List<T> topNodes = new ArrayList<>();
        for (T children : nodeList) {
            String pid = children.getParentId();
            if (StringUtils.isBlank(pid) || SysContant.TREE_ROOT.equals(pid)) {
                topNodes.add(children);
                continue;
            }
            for (T parent : nodeList) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.addChild(children);
                    continue;
                }
            }
        }
        return topNodes;
    }

    /**
     * 构建树结构-栈
     * 会附加level信息
     *
     * @param nodeList
     * @param level    初始的层级数
     * @param <T>
     * @return
     */
    public static <T extends ITreeNode<T>> T buildOneStack(List<T> nodeList, int level) {
        if (nodeList.size() == 1) {
            return nodeList.get(0);
        }

        List<T> treeNodes = TreeUtil.buildStack(nodeList, level);
        if (treeNodes.size() == 1) {
            return treeNodes.get(0);
        } else {
            T t = (T) T.newInstance(nodeList.get(0).getClass());
            t.setChildren(treeNodes);
            return t;
        }
    }

    /**
     * 构建树结构-栈
     * 会附加level信息
     */
    public static <T extends ITreeNode<T>> List<T> buildStack(List<T> nodeList, int level) {
        List<T> tree = new ArrayList<T>();
        if (nodeList == null || nodeList.size() == 0) {
            return tree;
        }

        CopyOnWriteArrayList<T> treeNodes_copy = new CopyOnWriteArrayList<T>(nodeList);
        Stack<T> stack = new Stack<T>();
        for (T treeNode : treeNodes_copy) {
            //根节点
            if (SysContant.TREE_ROOT.equals(treeNode.getParentId())) {
                tree.add(treeNode);
                T parent = treeNode;
                stack.push(parent);
                treeNodes_copy.remove(parent);
                parent.setLevel(stack.size() + level - 1);

                out:
                while (parent != null) {
                    for (T child : treeNodes_copy) {
                        //parent有子节点
                        if (child.getParentId().equals(parent.getId())) {
                            parent.addChild(child);
                            parent = child;
                            stack.push(parent);
                            treeNodes_copy.remove(parent);
                            parent.setLevel(stack.size() + level - 1);
                            continue out;
                        }
                    }
                    //parent没有子节点  出栈:后进先出   parent出栈
                    stack.pop();
                    if (stack.empty()) {
                        break out;
                    }
                    parent = stack.peek();
                }
            }
        }

        return tree;
    }

    /**
     * 构建树结构-递归
     * 会附加level信息
     *
     * @param nodeList
     * @param level    初始的层级数
     * @param <T>
     * @return
     */
    public static <T extends ITreeNode<T>> T buildOneRecursion(List<T> nodeList, int level) {
        if (nodeList.size() == 1) {
            return nodeList.get(0);
        }

        List<T> treeNodes = TreeUtil.buildRecursion(nodeList, SysContant.TREE_ROOT, level);
        if (treeNodes.size() == 1) {
            return treeNodes.get(0);
        } else {
            T t = (T) T.newInstance(nodeList.get(0).getClass());
            t.setChildren(treeNodes);
            return t;
        }
    }

    /**
     * 构建树结构-递归
     * 会附加level信息
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends ITreeNode<T>> List<T> buildRecursion(List<T> nodeList, String parentId, int level) {
        List<T> childrens = new ArrayList<>();

        for (T node : nodeList) {
            if (node.getParentId().equals(parentId)) {
                node.setLevel(level);
                childrens.add(node);
            }
        }
        if (!childrens.isEmpty()) {
            for (T node : childrens) {
                node.setChildren(buildRecursion(nodeList, node.getId(), level + 1));
            }
        }
        return childrens;
    }
}
