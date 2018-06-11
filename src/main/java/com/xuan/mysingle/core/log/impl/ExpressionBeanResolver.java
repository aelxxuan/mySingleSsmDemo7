/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.log.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * 表达式解析器,在spring配置文件中用到
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class ExpressionBeanResolver implements BeanResolver, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object resolve(EvaluationContext evaluationContext, String beanName) throws AccessException {
        return this.beanFactory.getBean(beanName);
    }
}
