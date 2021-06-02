package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.SysRole;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysRoleService;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLog;
import org.litu.base.controller.PageBasePath;
import org.litu.core.login.TokenCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户管理controller
 */
@LtLog(module = "角色模块")
@RequestMapping(value = "/role")
@PageBasePath(basePath = "system/role")
@Controller
public class SysRoleController extends BaseViewFormController<SysRole, ISysRoleService> {
    /**
     * 系统管理的service
     */
    @Autowired
    ISysSystemService sysSystemService;

    /**
     * 角色权限选择界面
     *
     * @param model 实体类
     * @return 角色权限选择页面
     */
    @GetMapping(value = "/roleMenu/{roleId}")
    @TokenCheck(check = false)
    public String roleMenu(@PathVariable(name = "roleId") String roleId, Model model) {
        model.addAttribute("roleId", roleId);

        List<SysSystem> systems = sysSystemService.listEnabled();
        model.addAttribute("systemList", systems);
        return "system/role/roleMenu";
    }


    /**
     * 岗位列表界面
     *
     * @param model 实体类
     * @return 岗位列表界面
     */
    @GetMapping(value = "/quarters/index")
    @TokenCheck(check = false)
    public String quartersIndex(Model model) {
        return "system/quarters/index";
    }

    /**
     * 岗位表单界面
     *
     * @param model 实体类
     * @return 岗位表单界面
     */
    @GetMapping(value = {"/quarters/form", "/quarters/form/{id}"})
    @TokenCheck(check = false)
    public String quartersForm(Model model, @PathVariable(value = "id", required = false) String id) {
        // 如果ID不为空，则认为是编辑界面，对界面的数据进行获取
        if (StringUtils.isNotBlank(id)) {
            SysRole obj = service.detail(id);
            model.addAttribute("data", obj);
        }
        return "system/quarters/form";
    }

    /**
     * 岗位详情界面
     *
     * @param model 实体类
     * @return 岗位详情界面
     */
    @GetMapping(value = "/quarters/view/{id}")
    @TokenCheck(check = false)
    public String quartersDetail(Model model, @PathVariable(value = "id") String id) {
        // 获取详情信息
        SysRole obj = service.detail(id);
        model.addAttribute("data", obj);

        beforeView(model, obj);
        return "system/quarters/view";
    }
}
