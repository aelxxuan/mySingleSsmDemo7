package com.xuan.mysingle.common.product.support;

import com.xuan.mysingle.common.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
@Repository
public interface ProductRepository {

    int add(Product product);
    int update(Product product);
    int delete(String productId);

    /**
     * 分页获取产品信息
     * @param map
     * @return
     */
    List<Product> getByPage(Map map);

    /**
     * 获取产品数量
     * @param map
     * @return
     */
    int getCountByPage(Map map);

    /**
     * 根据ID获取产品
     * @param productId
     * @return
     */
    Product getById(String productId);
}
