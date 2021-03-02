package org.litu.app.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.SysAccesstoken;
import org.litu.app.service.ISysAccesstokenService;
import org.litu.base.controller.BaseViewFormController;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperation;
import org.litu.base.controller.PageBasePath;
import org.litu.core.base.BaseRes;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.core.enums.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 授权令牌管理Controller
 */
@LtLog(module = "授权令牌模块")
@RequestMapping(value = "/accessToken")
@PageBasePath(basePath = "system/accessToken")
@Controller
public class SysAccesstokenController extends BaseViewFormController<SysAccesstoken, ISysAccesstokenService> {

    /**
     * 启用
     *
     * @param id 启用的记录id
     * @return 启用成功或者失败
     */
    @LtLogOperation(operation = LtLogOperationEnum.ENABLE)
    @PostMapping("/enable")
    @ResponseBody
    public BaseRes enable(String id) {
        if (StringUtils.isBlank(id)) {
            return BaseRes.error(ResultEnum.ParamError, "id不能为空!");
        }
        boolean res = service.enable(id);
        return res ? BaseRes.ok("启用成功！") : BaseRes.error(ResultEnum.UpdateError, "启用失败！");
    }

    /**
     * 禁用
     *
     * @param id 禁用的记录id
     * @return 禁用成功或者失败
     */
    @LtLogOperation(operation = LtLogOperationEnum.DISABLE)
    @PostMapping("/disable")
    @ResponseBody
    public BaseRes disable(String id) {
        if (StringUtils.isBlank(id)) {
            return BaseRes.error(ResultEnum.ParamError, "id不能为空!");
        }
        boolean res = service.disable(id);
        return res ? BaseRes.ok("禁用成功！") : BaseRes.error(ResultEnum.UpdateError, "禁用失败！");
    }

}
