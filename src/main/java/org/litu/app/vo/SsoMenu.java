package org.litu.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.litu.app.entity.SysMenu;
import org.litu.core.base.ITreeNode;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "菜单")
public class SsoMenu implements ITreeNode<SsoMenu> {
    @ApiModelProperty(value = "系统编号")
    private String systemCode;

    @ApiModelProperty(value = "Id")
    private String id;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "Url地址")
    private String url;
    @ApiModelProperty(value = "排序码")
    private Integer sortNum;
    @ApiModelProperty(value = "子菜单信息")
    public List<SsoMenu> children;

    /**
     * 系统菜单的构造方法
     *
     * @param sysMenu 系统菜单
     */
    public SsoMenu(SysMenu sysMenu) {
        this.systemCode = sysMenu.getSystemCode();
        this.id = sysMenu.getId();
        this.parentId = sysMenu.getParentId();
        this.type = sysMenu.getType();
        this.code = sysMenu.getCode();
        this.name = sysMenu.getName();
        this.icon = sysMenu.getIcon();
        this.url = sysMenu.getUrl();
        this.sortNum = sysMenu.getSortNum();
        this.children = new ArrayList<>();
    }

    /**
     * 添加子节点
     *
     * @param child
     */
    @Override
    public void addChild(SsoMenu child) {
        children.add(child);
    }

    /**
     * 设置层级
     *
     * @param level
     */
    @Override
    public void setLevel(int level) {

    }
}
