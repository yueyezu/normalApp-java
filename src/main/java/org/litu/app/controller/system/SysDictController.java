package org.litu.app.controller.system;

import org.litu.app.entity.system.SysDict;
import org.litu.app.service.ISysDictService;
import org.litu.base.controller.BaseViewTreeController;
import org.litu.base.controller.PageBasePath;
import org.litu.base.log.LtLog;
import org.litu.core.login.TokenCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 字典功能的控制器
 *
 * @author Administrator
 */
@RequestMapping(value = "/dict")
@LtLog(module = "字典管理")
@PageBasePath(basePath = "system/dict")
@Controller
public class SysDictController extends BaseViewTreeController<SysDict, ISysDictService> {
    /**
     * 选择列表的界面返回
     */
    @GetMapping("/selectView")
    @TokenCheck(check = false)
    public String select(Model model) {
        return "system/dict/selectView";
    }

}
