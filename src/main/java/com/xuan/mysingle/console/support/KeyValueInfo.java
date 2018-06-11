/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

/**
 * 基于名-值对形式的通用类
 * 因为k,v都是泛型，在repository层(如mybatis的xml里)建议显性定义resultMap映射关系,否则结果的k,v的类型可能不是预期类型。
 * 如count(*)对应的可能是Long类型而不是Integer类型
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class KeyValueInfo<K, V> {
    /**
     * 结果key
     */
    private K k;
    /**
     * 结果value
     */
    private V v;

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public KeyValueInfo() {
    }

    public KeyValueInfo(K k, V v) {
        this.k = k;
        this.v = v;
    }
}
