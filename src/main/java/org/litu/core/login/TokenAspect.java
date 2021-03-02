package org.litu.core.login;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * aop实现方法拦截
 */
@Aspect
@Component
public class TokenAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtil tokenUtil;

    private final Logger logger = LoggerFactory.getLogger(TokenAspect.class);

    //切点
    @Pointcut(value = "@annotation(org.litu.core.login.TokenCheck) || @within(org.litu.core.login.TokenCheck)")
    public void tokenPointcut() {
    }


    /**
     * 打印日志
     *
     * @param joinPoint 切点
     * @return 日志
     * @throws Throwable 抛出异常
     */
    @Around("tokenPointcut()")
    public Object processLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 判断类是否指定需要进行token校验
        TokenCheck typeTokenCheck = joinPoint.getTarget().getClass().getAnnotation(TokenCheck.class);
        if (typeTokenCheck != null && typeTokenCheck.check()) {
            // 如果类需要进行校验后，再判断方法是否需要进行校验
            // 如果类设置需要校验，则默认方法都是需要进行校验的。
            Method method = getMethod(joinPoint);
            TokenCheck tokenCheck = method.getAnnotation(TokenCheck.class);
            if (tokenCheck == null || tokenCheck.check()) {
                String token = request.getHeader(tokenUtil.getTokenKey());
                // 执行认证
                if (StringUtils.isBlank(token)) {
                    throw new LtServerException(ResultEnum.Unauthorized);
                }
                UserInfo user = tokenUtil.getUser(token);
                if (user == null) {
                    throw new LtServerException(ResultEnum.InvalidRequest);
                }
            }
        }

        return joinPoint.proceed();
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

}
