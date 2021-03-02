package org.litu.app.controller.system;

import org.litu.app.entity.SysOrganize;
import org.litu.app.service.ISysOrganizeService;
import org.litu.base.controller.BaseViewTreeController;
import org.litu.base.log.LtLog;
import org.litu.base.controller.PageBasePath;
import org.litu.core.login.TokenCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 组织/部门管理controller
 */
@LtLog(module = "组织模块")
@RequestMapping(value = "/organize")
@PageBasePath(basePath = "system/organize")
@Controller
public class SysOrganizeController extends BaseViewTreeController<SysOrganize, ISysOrganizeService> {

    /**
     * 选择列表的界面返回
     */
    @GetMapping("/selectView")
    @TokenCheck(check = false)
    public String select(Model model) {
        return "system/organize/selectView";
    }

}
