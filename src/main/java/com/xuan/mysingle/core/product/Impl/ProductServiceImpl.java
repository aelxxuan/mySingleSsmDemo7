/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.product.Impl;

import com.xuan.mysingle.common.product.Product;
import com.xuan.mysingle.common.product.support.ProductRepository;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.core.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final String CHACH_NAME = "product_cache";

    @Autowired
    ProductRepository productRepository;

    @Override
    public boolean addProduct(Product product) {
        return productRepository.add(product) > 0;
    }

    @Override
    public boolean updateProduct(Product product) {
        return productRepository.update(product) > 0;
    }

    @Override
    public boolean deleteProductById(String productId) {
        return productRepository.delete(productId) > 0;
    }

    @Override
    public List<Product> getProductByParam(String name, Pager pager) {
        Map<String,Object> param = new HashMap<>();
        param.put("name",name);
        param.put("start",pager.getStart());
        param.put("length", pager.getPageSize());
        pager.setTotalRow(productRepository.getCountByPage(param));
        return productRepository.getByPage(param);
    }

    @Override
    @Cacheable(value=CHACH_NAME,key = "#productId",unless="#result == null")
    public Product getProductById(String productId) {
        return productRepository.getById(productId);
    }
}
