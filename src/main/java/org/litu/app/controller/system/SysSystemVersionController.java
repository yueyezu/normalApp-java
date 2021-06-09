package org.litu.app.controller.system;

import org.litu.app.entity.system.SysSystemVersion;
import org.litu.app.service.ISysSystemVersionService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLog;
import org.litu.base.controller.PageBasePath;
import org.litu.core.login.TokenCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统版本管理Controller
 */
@LtLog(module = "系统版本模块")
@RequestMapping(value = "/systemVersion")
@PageBasePath(basePath = "system/systemVersion")
@Controller
public class SysSystemVersionController extends BaseViewFormController<SysSystemVersion, ISysSystemVersionService> {

    /**
     * 列表界面
     *
     * @param model 实体类
     * @return 对应该类的index页面
     */
    @GetMapping("/index/{sysCode}")
    @TokenCheck(check = false)
    public String index(Model model, @PathVariable(value = "sysCode") String sysCode) {
        model.addAttribute("sysCode", sysCode);
        return "system/systemVersion/index";
    }

    /**
     * 表单界面-添加
     *
     * @return 对应该类的form页面
     */
    @GetMapping(value = "/form/{sysCode}")
    @TokenCheck(check = false)
    @Override
    public String form(Model model, @PathVariable(value = "sysCode") String sysCode) {
        model.addAttribute("sysCode", sysCode);

        return "system/systemVersion/form";
    }

    /**
     * 表单界面-修改
     *
     * @return 对应该类的form页面
     */
    @GetMapping(value = "/form/{sysCode}/{id}")
    @TokenCheck(check = false)
    public String form(Model model, @PathVariable(value = "sysCode") String sysCode, @PathVariable(value = "id") String id) {
        SysSystemVersion obj = service.detail(id);
        model.addAttribute("data", obj);
        model.addAttribute("sysCode", sysCode);

        return "system/systemVersion/form";
    }

}
