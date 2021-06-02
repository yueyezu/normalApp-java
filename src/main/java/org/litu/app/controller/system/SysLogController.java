package org.litu.app.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.litu.app.entity.SysLogs;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysLogsService;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLogOperation;
import org.litu.base.controller.PageBasePath;
import org.litu.core.base.BaseRes;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.core.enums.ResultEnum;
import org.litu.core.login.TokenCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统日志管理controller
 */
@RequestMapping(value = "/log")
@PageBasePath(basePath = "system/log")
@Controller
public class SysLogController extends BaseViewFormController<SysLogs, ISysLogsService> {

    @Autowired
    ISysSystemService sysSystemService;

    @Override
    protected void beforeIndex(Model model) {
        List<SysSystem> systems = sysSystemService.listEnabled();
        model.addAttribute("systemList", systems);
    }

    /**
     * 清空日志
     *
     * @param id 要清空的日志id
     * @return 成功则返回日志已清空
     */
    @LtLogOperation(operation = LtLogOperationEnum.CLEAR)
    @PostMapping("/clear")
    @ResponseBody
    public BaseRes clear(String id) {
        QueryWrapper<SysLogs> queryWrapper = new QueryWrapper<SysLogs>();
        boolean res = service.remove(queryWrapper);
        return res ? BaseRes.ok("日志已清空！") : BaseRes.error(ResultEnum.UpdateError, "清空日志未成功！");
    }
}
