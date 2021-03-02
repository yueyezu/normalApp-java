package org.litu.app.controller.system;

import org.litu.app.entity.SysConfigs;
import org.litu.app.service.ISysConfigsService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperation;
import org.litu.base.controller.PageBasePath;
import org.litu.core.base.BaseRes;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.core.enums.ResultEnum;
import org.litu.util.transform.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统配置管理控制器
 *
 * @author Administrator
 */
@LtLog(module = "系统配置")
@RequestMapping(value = "/config")
@PageBasePath(basePath = "system/config")
@Controller
public class SysConfigController extends BaseViewFormController<SysConfigs, ISysConfigsService> {

    /**
     * 更新
     *
     * @param changes 更改内容
     * @return 成功则显示修改完成
     */
    @LtLogOperation(operation = LtLogOperationEnum.UPDATE)
    @PostMapping("/updateConfig")
    @ResponseBody
    public BaseRes update(@RequestParam("changes") StringBuffer changes) {
        List<SysConfigs> configs = JsonUtil.json2list(changes.toString(), SysConfigs.class);
        boolean res = service.updates(configs);
        return res ? BaseRes.ok("修改完成！") : BaseRes.error(ResultEnum.UpdateError);
    }
}
