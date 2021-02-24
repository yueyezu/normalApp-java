package org.litu.core.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.litu.core.base.BaseRes;
import org.litu.core.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常处理器
     *
     * @param e LtParamException
     * @return Map
     */
    @ExceptionHandler(LtParamException.class)
    @ResponseBody
    public BaseRes handleLtParamException(LtParamException e) {
        return BaseRes.error(ResultEnum.ParamError, e.getMessage());
    }

    /**
     * 自定义异常处理器
     *
     * @param e LtServerException
     * @return Map
     */
    @ExceptionHandler(LtServerException.class)
    @ResponseBody
    public BaseRes handleLtServerException(LtServerException e) {
        return BaseRes.error(e.getErrorMsg(), e.getMessage());
    }

    /**
     * 数据库中已存在该记录
     *
     * @param e DuplicateKeyException
     * @return Map
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public BaseRes handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return BaseRes.error(ResultEnum.DuplicateError);
    }

    /**
     * 没有权限
     *
     * @param e AuthorizationException
     * @return Map
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public BaseRes handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return BaseRes.error(ResultEnum.InvalidRequest);
    }

    /**
     * 添加全局异常处理流程，根据需要设置需要处理的异常
     *
     * @param request   HttpServletRequest
     * @param exception Exception
     * @return Map
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object methodExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {// 记录异常日志
        logger.error(exception.getMessage(), exception);
        return BaseRes.error();
    }
}