/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.xuan.mysingle.console.menu.ResponseCodeEnum;

import java.util.List;

/**
 * ajax后台返回给前端的载体
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/27
 */
public class ResponseVo<T,L> {
    private ResponseCodeEnum code;//编码 js判断时调用TLibSSO.codeNum.xxx判断
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 消息
     */
    private String message;
    /**
     * entity
     */
    private T entity;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 如返回list 可以多种媒介
     */
    private List<L> list;

    public ResponseVo() {
    }

    public ResponseVo(ResponseCodeEnum code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * 返回包含成功数据的成功实例
     *
     * @param entity
     * @param list
     * @param <T>
     * @param <L>
     * @return
     */
    public static <T, L> ResponseVo success(T entity, List<L> list) {
        ResponseVo<T, L> vo = new ResponseVo();
        vo.setSuccess(true);
        vo.setEntity(entity);
        vo.setList(list);
        return vo;
    }
    /**
     * 返回一个标志成功的实例
     *
     * @return
     */
    public static ResponseVo success() {
        ResponseVo vo = new ResponseVo();
        vo.setSuccess(true);
        return vo;
    }

    /**
     * 返回一个带描述文字的标志成功的实例
     *
     * @return
     */
    public static ResponseVo success(String message) {
        ResponseVo vo = new ResponseVo();
        vo.setSuccess(true);
        vo.setMessage(message);
        return vo;
    }

    /**
     * 返回一个包含错误信息标志失败的实例
     *
     * @param message
     * @return
     */
    public static ResponseVo fail(String message) {
        ResponseVo vo = new ResponseVo();
        vo.setSuccess(false);
        vo.setMessage(message);
        return vo;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<L> getList() {
        return list;
    }

    public void setList(List<L> list) {
        this.list = list;
    }

    public ResponseCodeEnum getCode() {
        return code;
    }

    public void setCode(ResponseCodeEnum code) {
        this.code = code;
    }
}
