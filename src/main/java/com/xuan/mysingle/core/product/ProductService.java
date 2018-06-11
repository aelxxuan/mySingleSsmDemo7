/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.product;

import com.xuan.mysingle.common.product.Product;
import com.xuan.mysingle.console.support.Pager;

import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
public interface ProductService {
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProductById(String productId);

    /**
     * 获取分页数据
     * @param name
     * @param pager
     * @return
     */
    List<Product> getProductByParam(String name,Pager pager);

    /**
     * 根据ID获取产品信息
     * @param productId
     * @return
     */
    Product getProductById(String productId);
}
