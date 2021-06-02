package org.litu.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.litu.app.entity.SysMenu;
import org.litu.core.base.TreeUtil;
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
        List<SsoMenu> menuTreeNodes = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            menuTreeNodes.add(new SsoMenu(sysMenu));
        }
        menus = TreeUtil.build(menuTreeNodes);
    }
}
