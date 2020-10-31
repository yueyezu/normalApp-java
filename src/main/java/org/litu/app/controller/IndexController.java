package org.litu.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.*;
import org.litu.app.service.*;
import org.litu.app.vo.CacheData;
import org.litu.base.controller.BaseController;
import org.litu.base.util.UserUtil;
import org.litu.base.vo.BaseRes;
import org.litu.base.vo.SelectVo;
import org.litu.base.vo.TreeNodeVo;
import org.litu.base.vo.TreeUtil;
import org.litu.core.login.ShiroSessionUtil;
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
    public String index(Model model) {
        String userId = UserUtil.getUserId();
        SysUser user = sysUserService.getById(userId);
        model.addAttribute("userMsg", user);

        if (StringUtils.isBlank(user.getfPhoto()))
            model.addAttribute("userPhoto", "/static/img/defaultPhoto.jpg");
        else
            model.addAttribute("userPhoto", "/sysFiles/loadFile?file=" + user.getfPhoto());

        List<TreeNodeVo<SysMenu>> menus = getMenus(userId);
        model.addAttribute("menuList", menus);


        return "index";
    }

    private List<TreeNodeVo<SysMenu>> getMenus(String userId) {
        List<TreeNodeVo<SysMenu>> menus = ShiroSessionUtil.session(SysContant.SESSION_MENU);
        if (menus == null) {
            List<String> menuTypes = new ArrayList<>();
            menuTypes.add(SysContant.MENUTYPE_MODULE);
            menuTypes.add(SysContant.MENUTYPE_FUNCTION);
            List<SysMenu> tempMenus = sysMenuService.userMenus(userId, SysContant.CURRENT_SYSTEM_CODE, menuTypes);

            List<TreeNodeVo<SysMenu>> menuTreeNodes = new ArrayList<>();
            for (SysMenu sysMenu : tempMenus) {
                TreeNodeVo<SysMenu> menuTreeNode = new TreeNodeVo<>();
                menuTreeNode.init(sysMenu);
                menuTreeNode.setIcon(sysMenu.getfIcon());
                menuTreeNodes.add(menuTreeNode);
            }
            menus = TreeUtil.buildWithStack(menuTreeNodes);

            ShiroSessionUtil.session(SysContant.SESSION_MENU, menus);
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
        String userId = UserUtil.getUserId();
        SysUser user = sysUserService.getById(userId);
        model.addAttribute("data", user);

        SysOrganize organize = sysOrganizeService.getById(user.getfDepartmentid());
        model.addAttribute("departmentId", user.getfDepartmentid());
        model.addAttribute("departmentName", organize.getfName());

        SysRole role = sysRoleService.getById(user.getfRoleid());
        model.addAttribute("roleName", role == null ? "无" : role.getfName());

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
        String userId = UserUtil.getUserId();
        data.setUserId(userId);
        data.setIsDefaultPwd(UserUtil.session(SysContant.SESSION_IS_DEFAULT_PWD));

        // 字典信息获取
        QueryWrapper<SysDict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.orderByAsc("F_SortNum");
        List<SysDict> dicts = sysDictService.list(dictQueryWrapper);
        QueryWrapper<SysDictitem> dictitemQueryWrapper = new QueryWrapper<>();
        dictitemQueryWrapper.orderByAsc("F_SortNum");
        List<SysDictitem> alldictitems = sysDictitemService.list(dictitemQueryWrapper);
        // 转化为需要返回的实体类
        Map<String, List<SelectVo>> dictArrCache = new LinkedHashMap<>();
        Map<String, Map<String, String>> dictCache = new LinkedHashMap<>();
        for (SysDict dict : dicts) {
            List<SelectVo> dictitems = new ArrayList<>();
            Map<String, String> dictItemMap = new LinkedHashMap<>();
            for (SysDictitem dictitem : alldictitems) {
                if (dictitem.getfDictid().equals(dict.getfId())) {
                    SelectVo selectVo = new SelectVo(dictitem.getfCode(), dictitem.getfName());
                    dictitems.add(selectVo);
                    dictItemMap.put(dictitem.getfCode(), dictitem.getfName());
                }
            }

            dictArrCache.put(dict.getfCode(), dictitems); // 选择列表
            dictCache.put(dict.getfCode(), dictItemMap); // 显示列表
        }
        data.setDictItemArr(dictArrCache);
        data.setDictItems(dictCache);

        return BaseRes.ok(data);
    }
}
