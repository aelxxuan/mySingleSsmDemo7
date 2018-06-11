/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.log.impl;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spel表达式解析器
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class SpELReseover {
    private static LocalVariableTableParameterNameDiscoverer parameterNameDiscovere =
            new LocalVariableTableParameterNameDiscoverer();
    private static ExpressionParser parser = new SpelExpressionParser();

    //缓存SPEL Expression
    private static Map<String, Expression> spelExpressionCaches = new ConcurrentHashMap<>();

    //缓存有@RedisLockable方法参数名称
    private static Map<String, String[]> parameterNameCaches = new ConcurrentHashMap<>();


    /**
     * 解析切点表达式的值
     *
     * @param joinPoint
     * @param beanResolver
     * @param expStr
     * @param isTemplate
     * @return
     */
    public static String parseExpression(JoinPoint joinPoint, ExpressionBeanResolver beanResolver,
                                         String expStr, boolean isTemplate) {
        EvaluationContext context = createEvaluationContext(joinPoint, beanResolver);
        return parseExpression(context, expStr, isTemplate);
    }


    /**
     * 创建表达式解析上下文
     *
     * @param joinPoint 连接点
     * @return
     */
    public static EvaluationContext createEvaluationContext(JoinPoint joinPoint, ExpressionBeanResolver beanResolver) {
        // 获取方法中的参数名数组
        String methodLongName = joinPoint.getSignature().toLongString();
        String[] parameterNames = parameterNameCaches.get(methodLongName);
        if (parameterNames == null) {
            Method method = getMethod(joinPoint);
            parameterNames = parameterNameDiscovere.getParameterNames(method);
            parameterNameCaches.put(methodLongName, parameterNames);
        }

        StandardEvaluationContext context = new StandardEvaluationContext();
        // 设置beanResolver，用于解析表达式时获取表达式中以@符号引用的bean
        context.setBeanResolver(beanResolver);
        //将方法的所有参数设置到表达式估值上下文
        Object[] args = joinPoint.getArgs();
        if (args.length == parameterNames.length) {
            for (int i = 0, len = args.length; i < len; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return context;
    }

    /**
     * 解析spring表达式或模板
     *
     * @param expStr     表达式或模板
     * @param isTemplate expStr是否模板
     * @return 表达式对应的字符串
     */
    public static String parseExpression(EvaluationContext context,
                                         String expStr, boolean isTemplate) {
        //缓存表达式
        Expression expression = spelExpressionCaches.get(expStr);
        if (expression == null) {
            if (isTemplate) {
                expression = parser.parseExpression(expStr, new TemplateParserContext());
            } else {
                expression = parser.parseExpression(expStr);
            }
            spelExpressionCaches.put(expStr, expression);
        }
        String result = null;
        if (expression instanceof CompositeStringExpression) {
            // 如果是复合表达式，则遍历所有表达式，然后把表达式的值组成一个字符串
            result = "";
            for (Expression subExpression : ((CompositeStringExpression) expression).getExpressions()) {
                Object o = subExpression.getValue(context);
                // 如果对字符串用JSON.toJSONString序列化，返回结果会包含双引号
                result += o instanceof String ? o.toString() : JSON.toJSONString(o);
            }
        } else {
            // 如果是单个表达式(LiteralExpression, SPELExpression),取出表达式的值，然后序列化成字符串
            Object o = expression.getValue(context);
            // 如果对字符串用JSON.toJSONString序列化，返回结果会包含双引号
            result = o instanceof String ? o.toString() : JSON.toJSONString(o);
        }
        return result;
    }

    /**
     * 获得切点方法
     *
     * @param joinPoint
     * @return
     */
    public static Method getMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
}
