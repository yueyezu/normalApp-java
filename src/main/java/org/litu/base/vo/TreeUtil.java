package org.litu.base.vo;

import org.litu.base.entity.BaseTreeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 树相关操作
 */
public class TreeUtil {
	/**
	 * 构建树结构-栈
	 *
	 * @param treeNodes 树节点
	 * @param <T>       实体类
	 * @return 树结构-栈
	 */
	public static <T extends BaseTreeEntity> List<TreeNodeVo<T>> buildWithStack(List<TreeNodeVo<T>> treeNodes) {
		List<TreeNodeVo<T>> tree = new ArrayList<TreeNodeVo<T>>();

		if (treeNodes == null || treeNodes.size() == 0) {
			return tree;
		}

		CopyOnWriteArrayList<TreeNodeVo<T>> treeNodes_copy = new CopyOnWriteArrayList<TreeNodeVo<T>>(treeNodes);
		Stack<TreeNodeVo<T>> stack = new Stack<TreeNodeVo<T>>();
		for (TreeNodeVo<T> treeNode : treeNodes_copy) {
			// 根节点
			String pId = treeNode.getParent();
			if (pId.equals("0")) {
				tree.add(treeNode);
				TreeNodeVo<T> parent = treeNode;
				stack.push(parent);
				treeNodes_copy.remove(parent);

				out:
				while (parent != null) {
					for (TreeNodeVo<T> child : treeNodes_copy) {
						// parent有子节点
						if (child.getParent().equals(parent.getId())) {
							parent.addChild(child);
							parent = child;
							stack.push(parent);
							treeNodes_copy.remove(parent);
							continue out;
						}
					}
					// parent出栈
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
}
