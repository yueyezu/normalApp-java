package org.litu.app.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.*;
import org.litu.app.service.*;
import org.litu.base.controller.BaseFormController;
import org.litu.base.util.UserUtil;
import org.litu.base.vo.BaseRes;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.LtLogOperation;
import org.litu.core.annotation.PageBasePath;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.enums.LtLogOperationEnum;
import org.litu.core.login.LoginTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理controller
 */
@LtLog(module = "用户模块")
@RequestMapping(value = "/user")
@PageBasePath(basePath = "system/user")
@Controller
public class SysUserController extends BaseFormController<SysUser, ISysUserService> {

    @Autowired
    private ISysUserloginService sysUserLoginService;
    @Autowired
    private ISysOrganizeService sysOrganizeService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserroleService sysUserroleService;
    @Autowired
    private ISysUserService sysUserService;


    /**
     * 用户角色选择界面
     *
     * @param model 实体类
     * @return 用户角色选则界面
     */
    @GetMapping(value = "/userRole")
    public String userRole(Model model, String userId) {
        model.addAttribute("nowUser", userId);
        try {
            // 角色列表
            LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SysRole::getfType, SysContant.ROLETYPE_ROLE);
            List<SysRole> roleList = sysRoleService.list(queryWrapper);
            model.addAttribute("roleList", roleList);
            // 用户角色列表
            List<SysUserrole> userrolesList = sysUserroleService.getByUserId(userId);
            List<String> userRoleIds = new ArrayList<>();
            for (SysUserrole sysUserrole : userrolesList) {
                userRoleIds.add(sysUserrole.getfRoleid());
            }
            model.addAttribute("userRoleIds", userRoleIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "system/user/userRole";
    }

    /**
     * 选择列表的界面返回
     *
     * @param model      前后台交互使用实体类
     * @param selectType 选择框的类型，single-单选，multi-多选
     * @return
     */
    @GetMapping("/selectView/{selectType}")
    public String select(Model model, @PathVariable(name = "selectType") String selectType, String hasSelect) {
        model.addAttribute("selectType", selectType);

        return "system/user/selectView";
    }

    @Override
    protected void beforeView(Model model, SysUser user) {
        super.beforeView(model, user);
        try {
            SysOrganize organize = sysOrganizeService.getById(user.getfDepartmentid());
            model.addAttribute("departmentId", user.getfDepartmentid());
            model.addAttribute("departmentName", organize.getfName());

            SysRole role = sysRoleService.getById(user.getfRoleid());
            model.addAttribute("roleName", role == null ? "无" : role.getfName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void beforeForm(Model model, SysUser user) {
        super.beforeForm(model, user);
        try {
            if (user == null) {
                String deptId = request("deptId");
                SysOrganize organize = sysOrganizeService.getById(deptId);
                model.addAttribute("departmentId", deptId);
                model.addAttribute("departmentName", organize.getfName());
            } else {
                SysOrganize organize = sysOrganizeService.getById(user.getfDepartmentid());
                model.addAttribute("departmentId", user.getfDepartmentid());
                model.addAttribute("departmentName", organize.getfName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改密码
     *
     * @param newPwd 新密码
     * @param oldPwd 旧密码
     * @return 密码修改成功
     * @throws Exception 抛出异常
     */
    @LtLogOperation(operation = LtLogOperationEnum.MODIFYPWD)
    @PostMapping(value = "/modifyPwd", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public BaseRes modifyPwd(String newPwd, String oldPwd) throws Exception {
        if (StringUtils.isAnyBlank(newPwd, oldPwd)) {
            return BaseRes.error(ErrorEnum.ParamError, "密码不能为空!");
        }
        try {
            SysUserlogin userLogin = sysUserLoginService.getByUserId(UserUtil.getUserId());
            if (!userLogin.getfPassword().equals(LoginTokenUtil.GetDbPassword(userLogin.getfSecretkey(), oldPwd))) {
                return BaseRes.error(ErrorEnum.ParamError, "原密码不正确!");
            }

            String sKey = LoginTokenUtil.GetSecretkey();
            userLogin.setfSecretkey(sKey);
            userLogin.setfPassword(LoginTokenUtil.GetDbPassword(sKey, newPwd));
            userLogin.setfChangepasstime(new Date());
            sysUserLoginService.updateById(userLogin);
            return BaseRes.ok("密码修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRes.error();
        }
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 密码重置成功
     * @throws Exception 抛出异常
     */
    @LtLogOperation(operationEx = "修改密码")
    @PostMapping(value = "/resetPwd", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public BaseRes resetPwd(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return BaseRes.error(ErrorEnum.ParamError, "userId不能为空!");
        }
        try {
            SysUserlogin userLogin = sysUserLoginService.getByUserId(userId);
            String sKey = LoginTokenUtil.GetSecretkey();
            userLogin.setfSecretkey(sKey);
            userLogin.setfPassword(LoginTokenUtil.GetDbPassword(sKey));
            userLogin.setfChangepasstime(new Date());
            sysUserLoginService.updateById(userLogin);
            return BaseRes.ok("密码重置成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRes.error();
        }
    }

    /**
     * 修改头像
     *
     * @param model Model
     * @return
     */
    @RequestMapping("/changeHeader")
    public String changeHeader(Model model) {
        try {
            SysUser user = UserUtil.getCurrentUser();
            model.addAttribute("admin", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/system/user/avatar";
    }

    /**
     * 更新自己的头像
     *
     * @return
     */
    @RequestMapping("/updateAvatar")
    @ResponseBody
    public BaseRes updateAvatar(String photoId) {
        try {
            if (sysUserService.updatePhoto(UserUtil.getUserId(), photoId)) {
                return BaseRes.ok();
            } else {
                return BaseRes.error("修改失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRes.error();
        }
    }

    /**
     * 用户树结构数据
     *
     * @param fDepartmentid
     * @return
     */
    @GetMapping(value = "/userTree", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public BaseRes userTree(String fDepartmentid) {
        return BaseRes.ok(sysUserService.userTree(fDepartmentid));
    }

    //根据账号确定唯一
    @PostMapping(value = "/getByAccount")
    @ResponseBody
    public BaseRes getByAccount(SysUser sysUser) {
        SysUser user = sysUserService.getByAccount(sysUser.getfAccount());
        if (user == null) {
            String defValue = null;
            return BaseRes.ok(defValue);
        } else {
            return BaseRes.ok();
        }
    }
}
