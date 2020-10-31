package org.litu.app.controller.system;

import org.litu.app.entity.SysDictitem;
import org.litu.app.service.ISysDictitemService;
import org.litu.base.controller.BaseFormController;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.PageBasePath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 字典值的控制器
 *
 * @author Administrator
 */
@LtLog(module = "字典值模块")
@RequestMapping(value = "/dictItem")
@PageBasePath(basePath = "system/dictItem")
@Controller
public class SysDictItemController extends BaseFormController<SysDictitem, ISysDictitemService> {

}
