package org.litu.app.controller.system;

import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.PageBasePath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理Controller
 */
@LtLog(module = "系统模块")
@RequestMapping(value = "/system")
@PageBasePath(basePath = "system/system")
@Controller
public class SysSystemController extends BaseViewFormController<SysSystem, ISysSystemService> {

}
