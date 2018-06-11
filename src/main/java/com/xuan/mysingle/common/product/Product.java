/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.product;

/**
 * 产品 java bean 属性必须是private 必须由无参构造函数、必须由属性的get/set方法
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/11
 */
public class Product {
    private String id;
    private String name;
    private String applicationId;
    private Integer groupId;
    private String roleId;

    public Product() {
    }

    public Product(String id, String name, String applicationId, Integer groupId, String roleId) {
        this.id = id;
        this.name = name;
        this.applicationId = applicationId;
        this.groupId = groupId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (applicationId != null ? !applicationId.equals(product.applicationId) : product.applicationId != null)
            return false;
        if (groupId != null ? !groupId.equals(product.groupId) : product.groupId != null) return false;
        return roleId != null ? roleId.equals(product.roleId) : product.roleId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", groupId=" + groupId +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
