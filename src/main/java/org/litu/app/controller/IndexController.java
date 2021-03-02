package org.litu.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.*;
import org.litu.app.service.*;
import org.litu.app.vo.CacheData;
import org.litu.core.base.BaseController;
import org.litu.core.base.BaseRes;
import org.litu.core.base.SelectVo;
import org.litu.core.base.TreeUtil;
import org.litu.core.login.ShiroLoginUtil;
import org.litu.core.login.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页数据加载controller
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysOrganizeService sysOrganizeService;
    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    private ISysDictitemService sysDictitemService;

    /**
     * 主界面
     *
     * @return 主页面
     */
    @GetMapping("/index")
    public String index(String token, Model model) {
        UserInfo user = nowUser(token);

        model.addAttribute("userMsg", user);

        if (StringUtils.isBlank(user.getPhoto()))
            model.addAttribute("userPhoto", "/static/img/defaultPhoto.jpg");
        else
            model.addAttribute("userPhoto", "/sysFiles/loadFile?file=" + user.getPhoto());

        List<SysMenu> menus = getMenus(user.getId());
        model.addAttribute("menuList", menus);

        return "index";
    }

    private List<SysMenu> getMenus(String userId) {
        List<SysMenu> menus = ShiroLoginUtil.session(SysContant.SESSION_MENU);
        if (menus == null) {
            List<String> menuTypes = new ArrayList<>();
            menuTypes.add(SysContant.MENUTYPE_MODULE);
            menuTypes.add(SysContant.MENUTYPE_FUNCTION);
            List<SysMenu> tempMenus = sysMenuService.userMenus(userId, SysContant.CURRENT_SYSTEM_CODE, menuTypes);

            List<SysMenu> menuTreeNodes = new ArrayList<>();
            for (SysMenu sysMenu : tempMenus) {
                menuTreeNodes.add(sysMenu);
            }
            menus = TreeUtil.build(menuTreeNodes);

            ShiroLoginUtil.session(SysContant.SESSION_MENU, menus);
        }

        return menus;
    }

    @GetMapping("/home")
    public String main() {
        return "main/home";
    }


    /**
     * 修改密码界面
     *
     * @return 修改密码页面
     */
    @GetMapping("/main/changepassword")
    public String changepassword(Model model) {
        return "main/changepassword";
    }

    /**
     * 获取用户信息界面
     *
     * @return 用户信息页面
     */
    @GetMapping("/main/userinfo")
    public String userinfo(Model model) {
        String userId = nowUser().getId();
        SysUser user = sysUserService.getById(userId);
        model.addAttribute("data", user);

        SysOrganize organize = sysOrganizeService.getById(user.getDeptId());
        model.addAttribute("deptId", user.getDeptId());
        model.addAttribute("departmentName", organize.getName());

        SysRole role = sysRoleService.getById(user.getRoleId());
        model.addAttribute("roleName", role == null ? "无" : role.getName());

        return "main/userinfo";
    }

    /**
     * 前台缓存按钮和字典
     *
     * @return 前台缓存按钮和字典
     */
    @GetMapping("/data")
    @ResponseBody
    public BaseRes data() {
        CacheData data = new CacheData();
        String userId = nowUser().getId();
        data.setUserId(userId);

        // 字典信息获取
        LambdaQueryWrapper<SysDict> dictWrapper = Wrappers.lambdaQuery();
        dictWrapper.orderByAsc(SysDict::getSortNum);
        List<SysDict> dicts = sysDictService.list(dictWrapper);
        LambdaQueryWrapper<SysDictitem> dictitemWrapper = Wrappers.lambdaQuery();
        dictitemWrapper.orderByAsc(SysDictitem::getSortNum);
        List<SysDictitem> alldictitems = sysDictitemService.list(dictitemWrapper);
        // 转化为需要返回的实体类
        Map<String, List<SelectVo>> dictArrCache = new LinkedHashMap<>();
        Map<String, Map<String, String>> dictCache = new LinkedHashMap<>();
        for (SysDict dict : dicts) {
            List<SelectVo> dictitems = new ArrayList<>();
            Map<String, String> dictItemMap = new LinkedHashMap<>();
            for (SysDictitem dictitem : alldictitems) {
                if (dictitem.getDictId().equals(dict.getId())) {
                    SelectVo selectVo = new SelectVo(dictitem.getCode(), dictitem.getName());
                    dictitems.add(selectVo);
                    dictItemMap.put(dictitem.getCode(), dictitem.getName());
                }
            }

            dictArrCache.put(dict.getCode(), dictitems); // 选择列表
            dictCache.put(dict.getCode(), dictItemMap); // 显示列表
        }
        data.setDictItemArr(dictArrCache);
        data.setDictItems(dictCache);

        return BaseRes.ok(data);
    }
}
