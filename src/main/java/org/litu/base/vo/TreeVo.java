package org.litu.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.litu.core.base.ITreeNode;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeVo implements ITreeNode<TreeVo> {

    @ApiModelProperty(value = "id", required = true)
    protected String id;
    /**
     * 父节点,若无父节点,若当前节点没有父节点，即当前节点是根节点，需返回null或者空字符串
     */
    @ApiModelProperty(value = "父节点id")
    protected String parentId;

    @ApiModelProperty(value = "文本", required = true)
    protected String text = "";

    @ApiModelProperty(value = "层级")
    @JsonIgnore
    public int level;

    @ApiModelProperty(value = "子节点列表")
    public List<TreeVo> children;

    /**
     * 添加子节点
     *
     * @param child
     */
    @Override
    public void addChild(TreeVo child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
