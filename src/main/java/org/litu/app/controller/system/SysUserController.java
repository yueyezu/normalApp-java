package org.litu.app.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.system.*;
import org.litu.app.service.*;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.controller.PageBasePath;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperation;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.core.base.BaseRes;
import org.litu.core.enums.ResultEnum;
import org.litu.core.login.PasswordUtil;
import org.litu.core.login.TokenCheck;
import org.litu.core.login.UserInfo;
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
public class SysUserController extends BaseViewFormController<SysUser, ISysUserService> {

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
    @TokenCheck(check = false)
    public String userRole(Model model, String userId) {
        model.addAttribute("nowUser", userId);
        try {
            // 角色列表
            LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SysRole::getType, SysContant.ROLETYPE_ROLE);
            List<SysRole> roleList = sysRoleService.list(queryWrapper);
            model.addAttribute("roleList", roleList);
            // 用户角色列表
            List<SysUserrole> userrolesList = sysUserroleService.getByUserId(userId);
            List<String> userRoleIds = new ArrayList<>();
            for (SysUserrole sysUserrole : userrolesList) {
                userRoleIds.add(sysUserrole.getRoleId());
            }
            model.addAttribute("userRoleIds", userRoleIds);
        } catch (Exception e) {
            logger.error("获取用户角色选择界面错误！", e);
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
    @TokenCheck(check = false)
    public String select(Model model, @PathVariable(name = "selectType") String selectType, String hasSelect) {
        model.addAttribute("selectType", selectType);

        return "system/user/selectView";
    }

    @Override
    protected void beforeView(Model model, SysUser user) {
        super.beforeView(model, user);
        try {
            SysOrganize organize = sysOrganizeService.getById(user.getDeptId());
            model.addAttribute("deptId", user.getDeptId());
            model.addAttribute("deptName", organize.getName());

            SysRole role = sysRoleService.getById(user.getRoleId());
            model.addAttribute("roleName", role == null ? "无" : role.getName());
        } catch (Exception e) {
            logger.error("获取用户详情界面错误！", e);
        }
    }

    @Override
    protected void beforeForm(Model model, SysUser user) {
        super.beforeForm(model, user);
        try {
            if (user == null) {
                String deptId = request("deptId");
                SysOrganize organize = sysOrganizeService.getById(deptId);
                model.addAttribute("deptId", deptId);
                model.addAttribute("deptName", organize.getName());
            } else {
                SysOrganize organize = sysOrganizeService.getById(user.getDeptId());
                model.addAttribute("deptId", user.getDeptId());
                model.addAttribute("deptName", organize.getName());
            }
        } catch (Exception e) {
            logger.error("获取用户编辑界面错误！", e);
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
            return BaseRes.error(ResultEnum.ParamError, "密码不能为空!");
        }
        try {
            UserInfo user = nowUser();
            SysUserlogin userLogin = sysUserLoginService.getByUserId(user.getId());
            if (!userLogin.getPassword().equals(PasswordUtil.GetDbPassword(userLogin.getSecretKey(), oldPwd))) {
                return BaseRes.error(ResultEnum.ParamError, "原密码不正确!");
            }

            String sKey = PasswordUtil.GetSecretkey();
            userLogin.setSecretKey(sKey);
            userLogin.setPassword(PasswordUtil.GetDbPassword(sKey, newPwd));
            userLogin.setChangePassTime(new Date());
            sysUserLoginService.updateById(userLogin);
            return BaseRes.ok("密码修改成功！");
        } catch (Exception e) {
            logger.error("修改用户密码错误！", e);
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
            return BaseRes.error(ResultEnum.ParamError, "userId不能为空!");
        }
        SysUserlogin userLogin = sysUserLoginService.getByUserId(userId);
        String sKey = PasswordUtil.GetSecretkey();
        userLogin.setSecretKey(sKey);
        userLogin.setPassword(PasswordUtil.GetDbPassword(sKey));
        userLogin.setChangePassTime(new Date());
        sysUserLoginService.updateById(userLogin);
        return BaseRes.ok("密码重置成功！密码为:" + PasswordUtil.getDefaultPwd());
    }

    /**
     * 修改头像
     *
     * @param model Model
     * @return
     */
    @RequestMapping("/changeHeader")
    @TokenCheck(check = false)
    public String changeHeader(String token, Model model) {
        try {
            UserInfo user = nowUser(token);
            model.addAttribute("admin", user);
        } catch (Exception e) {
            logger.error("获取修改用户头像界面错误！", e);
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
            UserInfo user = nowUser();
            if (sysUserService.updatePhoto(user.getId(), photoId)) {
                return BaseRes.ok();
            } else {
                return BaseRes.error("修改失败!");
            }
        } catch (Exception e) {
            logger.error("修改用户头像错误！", e);
            return BaseRes.error();
        }
    }

    /**
     * 用户树结构数据
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "/userTree", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public BaseRes userTree(String deptId) {
        return BaseRes.ok(sysUserService.userTree(deptId));
    }
}
