/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.log.impl;

import com.xuan.mysingle.common.log.support.Loggable;
import com.xuan.mysingle.console.support.ResponseVo;
import com.xuan.mysingle.core.log.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
@Aspect
public class LogAspect {
    private LogService logService;
    private ExpressionBeanResolver beanResolver;

    /**
     * 使用Around环绕式切面织入连接点，在方法执行前解析springEL表达式，方法执行成功后再记录日志。
     * 不使用AfterReturing是避免删除操作完成后，获取不到表达式中需要的对象的相关值。
     *
     * @param jp 连接点
     * @throws Throwable
     */
    @Around("methodsToBeProfiled()")
    public Object log(ProceedingJoinPoint jp) throws Throwable {
        Method method = SpELReseover.getMethod(jp);
        Object proceed = jp.proceed();
        if (proceed instanceof ResponseVo) {
            ResponseVo vo = (ResponseVo) proceed;
            if (vo != null && !vo.isSuccess())
                return proceed;
        }
        //添加日志到数据库
        Loggable annotation = method.getAnnotation(Loggable.class);
        EvaluationContext context = SpELReseover.createEvaluationContext(jp, beanResolver);

        //解析日志描述表达式
        String desc = SpELReseover.parseExpression(context, annotation.value(), true);
        String operator = annotation.operator();
        //由日志实现类自动获取操作人
        if (StringUtils.isEmpty(operator)) {
            logService.add(annotation.logType(), desc);
        } else {
            //动态解析操作人
            operator = SpELReseover.parseExpression(context, operator, true);
            logService.add(annotation.logType(), desc, operator);
        }
        return proceed;
    }

    @Pointcut("execution(* com.xuan.mysingle..*.*(..))  && @annotation(com.xuan.mysingle.common.log.support.Loggable)")
    public void methodsToBeProfiled() {
    }

    //setters

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setBeanResolver(ExpressionBeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }
    //endregion

}
