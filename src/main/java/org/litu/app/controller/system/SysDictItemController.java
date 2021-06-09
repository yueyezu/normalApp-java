package org.litu.app.controller.system;

import org.litu.app.entity.system.SysDictItem;
import org.litu.app.service.ISysDictItemService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLog;
import org.litu.base.controller.PageBasePath;
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
public class SysDictItemController extends BaseViewFormController<SysDictItem, ISysDictItemService> {

}
