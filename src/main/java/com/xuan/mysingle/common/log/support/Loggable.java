package com.xuan.mysingle.common.log.support;

import com.xuan.mysingle.common.log.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志切面注解
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable{
    /**
     * 日志描述，必须使用表达式模板，形如："一些字符#{}一些字符#{}"，#{}里面是spring表达式。解析器会将#{}中的表达式解析为相对应的值。
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @see LogType
     */
    LogType logType() default LogType.DATA;

    /**
     * 操作人.日志的触发人.如果不设置则自动从登录用户上下文获取.
     * 如果是外部通过api接口不登录直接调用则必须设置此参数!!
     *
     * @return
     */
    String operator() default "";
}
