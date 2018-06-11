/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.product;

import com.xuan.mysingle.common.product.Product;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
public class ProductVo extends Product {
    private String applicationName;
    private String groupName;
    private String roleName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
