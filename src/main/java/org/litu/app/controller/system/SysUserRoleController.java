package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.service.ISysUserroleService;
import org.litu.app.vo.UserRole;
import org.litu.core.base.BaseController;
import org.litu.core.base.BaseRes;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.LtLogOperation;
import org.litu.core.enums.ResultEnum;
import org.litu.core.enums.LtLogOperationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户添加角色控制器
 *
 * @author Administrator
 */
@LtLog(module = "用户角色模块")
@RequestMapping(value = "/userRole")
@Controller
public class SysUserRoleController extends BaseController {

    @Autowired
    private ISysUserroleService sysUserRoleService;

    /**
     * 用户分配角色
     *
     * @param userId 用户Id
     * @return 提交成功，对应的用户角色
     */
    @LtLogOperation(operationEx = "角色分配")
    @PostMapping(value = "/roleList", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public BaseRes userRoles(String userId) {
        List<UserRole> userroles = sysUserRoleService.userRoles(userId);
        return BaseRes.ok(userroles);
    }

    /**
     * 保存用户的角色
     *
     * @param userId  用户Id
     * @param roleIds 角色Id
     * @return 保存成功
     */
    @LtLogOperation(operation = LtLogOperationEnum.ADD)
    @PostMapping(value = "/saveUserRole")
    @ResponseBody
    public BaseRes assign(@RequestParam(value = "userId") String userId, @RequestParam(value = "roleIds") String roleIds) {
        if (StringUtils.isBlank(userId)) {
            return BaseRes.error(ResultEnum.ParamError, "roleId不能为空！");
        }
        String[] roleIdsArray = new String[0];
        if (StringUtils.isNotBlank(roleIds)) {
            roleIdsArray = roleIds.split(",");
            if (roleIdsArray != null && roleIdsArray.length > 0 && StringUtils.isAnyBlank(roleIdsArray)) {
                return BaseRes.error(ResultEnum.ParamError, "roleIds参数包含空值！");
            }
        }
        boolean res = sysUserRoleService.save(userId, roleIdsArray);
        return res ? BaseRes.ok("保存成功！") : BaseRes.error(ResultEnum.SaveError);
    }
}
