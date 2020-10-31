package org.litu.app.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.litu.app.entity.SysLogs;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysLogsService;
import org.litu.app.service.ISysSystemService;
import org.litu.base.controller.BaseFormController;
import org.litu.base.vo.BaseRes;
import org.litu.core.annotation.LtLogOperation;
import org.litu.core.annotation.PageBasePath;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.enums.LtLogOperationEnum;
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
public class SysLogControlller extends BaseFormController<SysLogs, ISysLogsService> {

    @Autowired
    ISysSystemService sysSystemService;

    /**
     * 列表界面
     *
     * @param model 实体类
     * @return 对应该类的index页面
     */
    @GetMapping("/index")
    @Override
    public String index(Model model) {
        List<SysSystem> systems = sysSystemService.listEnabled();
        model.addAttribute("systemList", systems);

        return "system/log/index";
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
        return res ? BaseRes.ok("日志已清空！") : BaseRes.error(ErrorEnum.UpdateError, "清空日志未成功！");
    }
}
