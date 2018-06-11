/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common.PictureTool;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public class Result {
    private boolean success;
    private String message;
    public final static Result SUCCESS = new Result(true);
    public final static Result ERROR = new Result(false);

    public Result(boolean success){
        this.success = success;
    }

    public Result(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public static Result newInstance(boolean success){
        return success ? SUCCESS : ERROR;
    }

    public static Result newInstance(boolean success, String message){
        return new Result(success, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
