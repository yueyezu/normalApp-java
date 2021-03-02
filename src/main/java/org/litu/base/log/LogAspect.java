package org.litu.base.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.litu.core.login.TokenUtil;
import org.litu.core.login.UserInfo;
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
@Aspect()
@Component
public class LogAspect {

    @Autowired
    private IBaseLogService optLogService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenUtil tokenUtil;

    @Value("${ltsystem.sysmsg.code}")
    private String syetemCode;

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //切点
    @Pointcut("@annotation(org.litu.base.log.LtLogOperation)")
    public void logPointcut() {
    }

//    /**
//     * 进入方法后打印日志
//     *
//     * @param joinPoint 切点
//     */
//    @Before("logPointcut()")
//    public void before(JoinPoint joinPoint) {
//    }
//
//    /**
//     * 方法结束打印日志
//     *
//     * @param joinPoint 切点
//     */
//    @After("logPointcut()")
//    public void after(JoinPoint joinPoint) {
//    }

    /**
     * 打印日志
     *
     * @param joinPoint 切点
     * @return 日志
     * @throws Throwable 抛出异常
     */
    @Around("logPointcut()")
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
        String token = request.getHeader(tokenUtil.getTokenKey());
        UserInfo userInfo = tokenUtil.getUser(token);

        LtLogsVo log = new LtLogsVo();
        log.setCreatetime(new Date());
        log.setUserId(userInfo.getId());
        log.setIp(ip);
        log.setModule(module);
        log.setOpttype(operation);
        log.setSystemCode(syetemCode);
        String content = "args=" + JsonUtil.map2json(args) + "\nresult=" + JsonUtil.pojo2json(result);
        log.setOptcontent(content);

        optLogService.addOptLogs(log);
    }

//    /**
//     * AfterReturning 方法执行正常返回 拦截执行
//     */
//    @AfterReturning("logPointcut()")
//    public void afterReturning(JoinPoint joinPoint) {
//    }
//
//    /**
//     * AfterThrowing 拦截执行
//     */
//    @AfterThrowing(pointcut = "logPointcut()", throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
//        // logger.error("{} 出现异常：",getMethodName(joinPoint),ex);
//    }
}