package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.SysMenu;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysMenuService;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseViewTreeController;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.PageBasePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * 表单界面
     *
     * @param model 实体类
     * @return 对应该类的form页面
     */
    @GetMapping(value = {"/form", "/form/{id}"})
    @Override
    public String form(Model model, @PathVariable(value = "id", required = false) String id) {
        Map<String, String> params = requestParams();
        // 如果ID不为空，则认为是编辑界面，对界面的数据进行获取
        model.addAttribute("systemCode", params.get("systemCode"));
        String parentId = "";
        if (StringUtils.isNotBlank(id)) {   // 修改时的返回信息处理
            SysMenu menu = service.detail(id);
            model.addAttribute("data", menu);
            model.addAttribute("type", menu.getType());
            parentId = menu.getParentId();
        } else {
            model.addAttribute("type", params.get("type"));
            parentId = params.get("parentId");
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

        return "system/menu/form";
    }
}
