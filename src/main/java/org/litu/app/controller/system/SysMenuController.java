package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.SysMenu;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysMenuService;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseViewTreeController;
import org.litu.base.controller.PageBasePath;
import org.litu.base.log.LtLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理controller
 */
@LtLog(module = "菜单模块")
@RequestMapping(value = "/menu")
@PageBasePath(basePath = "system/menu")
@Controller
public class SysMenuController extends BaseViewTreeController<SysMenu, ISysMenuService> {

    /**
     * 系统管理的service
     */
    @Autowired
    ISysSystemService sysSystemService;

    @Override
    protected void beforeIndex(Model model) {
        super.beforeIndex(model);
        List<SysSystem> systems = sysSystemService.listEnabled();
        model.addAttribute("systemList", systems);
    }

    /**
     * 返回界面前数据处理
     *
     * @param model 前后端交互实体
     * @param menu
     */
    @Override
    protected void beforeForm(Model model, SysMenu menu) {
        Map<String, String> params = requestParams();
        model.addAttribute("systemCode", params.get("systemCode")); // 系统信息

        String parentId = "";
        if (menu == null) { // 为空，认为是添加界面
            model.addAttribute("type", params.get("type"));
            parentId = params.get("parentId");
        } else { // 否则认为是修改界面
            model.addAttribute("type", menu.getType());
            parentId = menu.getParentId();
        }

        // 显示父级菜单的处理
        if (StringUtils.isBlank(parentId) || parentId.equals("0")) {
            model.addAttribute("parentId", 0);
            model.addAttribute("parentName", "根目录");
        } else {
            SysMenu pMenu = service.detail(parentId);
            model.addAttribute("parentId", parentId);
            model.addAttribute("parentName", pMenu.getName());
        }
    }
}
