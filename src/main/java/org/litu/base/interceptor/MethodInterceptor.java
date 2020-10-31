package org.litu.base.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.litu.base.service.IBaseLogService;
import org.litu.base.util.UserUtil;
import org.litu.base.vo.LtLogsVo;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.LtLogOperation;
import org.litu.util.transform.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * aop实现方法拦截
 */
@Aspect
@Component
public class MethodInterceptor {

    @Autowired
    private IBaseLogService optLogService;
    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(MethodInterceptor.class);

    //切点
    @Pointcut("execution(public * org.litu.app.controller..*.*(..)) || execution(public * org.litu.base.controller.BaseFormController.*(..))")
    public void myMethod() {
    }

    /**
     * 进入方法后打印日志
     *
     * @param joinPoint 切点
     */
    @Before("myMethod()")
    public void before(JoinPoint joinPoint) {
    }

    /**
     * 方法结束打印日志
     *
     * @param joinPoint 切点
     */
    @After("myMethod()")
    public void after(JoinPoint joinPoint) {
    }

    /**
     * 打印日志
     *
     * @param joinPoint 切点
     * @return 日志
     * @throws Throwable 抛出异常
     */
    @Around("myMethod()")
    public Object processLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        try {
            obj = joinPoint.proceed(args);
            return obj;
        } catch (Throwable e) {
            throw e;
        } finally {
            try {
                process(getMethod(joinPoint), getTarget(joinPoint), request.getParameterMap(), obj);
            } catch (Exception e) {
                logger.error("日志注入错误", e);
            }
            logger.info("执行方法:{}:\n请求参数:{}\n返回结果:{}", getMethodName(joinPoint), JsonUtil.map2json(request.getParameterMap()), obj);
        }
    }

    /**
     * 获取方法名
     *
     * @param joinPoint 切点
     * @return 方法名
     */
    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }

    /**
     * 获取目标
     *
     * @param joinPoint 切点
     * @return 目标
     */
    private Object getTarget(JoinPoint joinPoint) {
        return joinPoint.getTarget();
    }

    /**
     * 获取方法
     *
     * @param joinPoint 切点
     * @return 方法
     */
    private Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        return targetMethod;
    }

    @Value("${ltsystem.sysmsg.code}")
    private String syetemCode;

    /**
     * 解析@SysLogDescriptor注解，并将日志写入数据库
     *
     * @param method 方法
     */
    public void process(Method method, Object target, Map<String, String[]> args, Object result) {
        if (method == null) {
            return;
        }
        LtLogOperation methodLogParam = method.getAnnotation(LtLogOperation.class);
        if (methodLogParam == null || !methodLogParam.enable()) {
            return;
        }

        String module = methodLogParam.module();
        if (StringUtils.isBlank(module)) {
            LtLog moduleLogParam = target.getClass().getAnnotation(LtLog.class);
            if (moduleLogParam == null) {
                logger.error(target.getClass().getName() + "日志模块注解信息缺失！");
                return;
            }
            if (!moduleLogParam.enable()) {
                return;
            }

            module = moduleLogParam.module();
        }

        String operation = methodLogParam.operationEx();
        if (StringUtils.isBlank(operation)) {
            operation = methodLogParam.operation().getOperation();
        }
        String ip = SecurityUtils.getSubject().getSession().getHost();
        String userId = UserUtil.getUserId();

        LtLogsVo log = new LtLogsVo();
        log.setCreatetime(new Date());
        log.setUserId(userId);
        log.setIp(ip);
        log.setModule(module);
        log.setOpttype(operation);
        log.setSystemCode(syetemCode);
        String content = "args=" + JsonUtil.map2json(args) + "\nresult=" + JsonUtil.pojo2json(result);
        log.setOptcontent(content);

        optLogService.addOptLogs(log);
    }

    /**
     * AfterReturning 方法执行正常返回 拦截执行
     */
    @AfterReturning("myMethod()")
    public void afterReturning(JoinPoint joinPoint) {
    }

    /**
     * AfterThrowing 拦截执行
     */
    @AfterThrowing(pointcut = "myMethod()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        // logger.error("{} 出现异常：",getMethodName(joinPoint),ex);
    }
}