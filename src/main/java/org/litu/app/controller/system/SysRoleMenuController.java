package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.service.ISysRolemenuService;
import org.litu.core.base.BaseController;
import org.litu.core.base.BaseRes;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperation;
import org.litu.core.enums.ResultEnum;
import org.litu.core.login.TokenCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色菜单管理controller
 */
@LtLog(module = "角色菜单模块")
@RequestMapping(value = "/role")
@Controller
public class SysRoleMenuController extends BaseController {

    @Autowired
    private ISysRolemenuService sysRolemenuService;

    /**
     * 获取角色的所有菜单ID信息
     *
     * @param roleId 角色ID
     * @return 保存成功
     */
    @GetMapping(value = "/roleMenuIds")
    @ResponseBody
    public BaseRes roleMenuIds(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return BaseRes.error(ResultEnum.ParamError, "roleId不能为空！");
        }
        List<String> menuIds = sysRolemenuService.roleMenuIds(roleId);
        return BaseRes.ok(menuIds);
    }

    /**
     * 保存角色的权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单IDs
     * @return 保存成功
     */
    @LtLogOperation(operationEx = "角色授权")
    @PostMapping(value = "/saveRoleMenus")
    @ResponseBody
    public BaseRes saveRoleMenus(String roleId, String[] menuIds) {
        if (StringUtils.isBlank(roleId)) {
            return BaseRes.error(ResultEnum.ParamError, "roleId不能为空！");
        }

        if (menuIds.length > 0 && StringUtils.isAnyBlank(menuIds)) {
            return BaseRes.error(ResultEnum.ParamError, "menuIds参数包含空值！");
        }
        boolean res = sysRolemenuService.save(roleId, menuIds);
        return res ? BaseRes.ok("保存成功!") : BaseRes.error(ResultEnum.SaveError);
    }
}
