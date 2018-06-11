/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common;

/**
 * 头像截取的位置信息
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public class AvatarData {
    private Integer x; //坐标x
    private Integer y; //坐标y
    private Integer height; //高度
    private Integer width; //宽度
    private Integer rotate; //旋转度，暂时没用上

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getRotate() {
        return rotate;
    }

    public void setRotate(Integer rotate) {
        this.rotate = rotate;
    }
}
