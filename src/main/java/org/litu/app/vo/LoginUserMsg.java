package org.litu.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.litu.app.entity.SysMenu;
import org.litu.core.login.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yueye
 */
@Data
@ApiModel(value = "用户登陆信息")
public class LoginUserMsg {
    @ApiModelProperty(value = "用户的信息")
    private UserInfo user;

    @ApiModelProperty(value = "权限信息")
    private List<String> powers = new ArrayList<>();

    @ApiModelProperty(value = "菜单列表")
    private List<SsoMenu> menus = new ArrayList<>();

    public void setMenus(List<SysMenu> sysMenus) {
        for (SysMenu sysMenu : sysMenus) {
            menus.add(new SsoMenu(sysMenu));
        }
    }
}

@Data
@ApiModel(value = "菜单")
class SsoMenu {
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
    @ApiModelProperty(value = "组件地址")
    private String cmpUrl;
    @ApiModelProperty(value = "排序码")
    private Integer sortNum;

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
//        this.cmpUrl = sysMenu.getCmpUrl();
        this.sortNum = sysMenu.getSortNum();
    }
}
