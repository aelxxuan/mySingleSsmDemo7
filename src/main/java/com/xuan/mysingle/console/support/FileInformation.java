/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.xuan.mysingle.common.user.User;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class FileInformation {
    /**
     * 总计数
     */
    private int totalCount = 0;
    /**
     * 新增加的数据量
     */
    private int newCount = 0;

    /**
     * 致命错误
     */
    private String fatalMessage;

    /**
     * 具体错误信息内容
     */
    private List<String> errorList;

    private List<User> errorUserList;

    public List<User> getErrorUserList() {
        return errorUserList;
    }

    public void setErrorUserList(List<User> errorUserList) {
        this.errorUserList = errorUserList;
    }

    public FileInformation() {
        fatalMessage = "";
        errorList = new ArrayList<>();
    }

    public void setFatalMessage(String fatalMessage) {
        this.fatalMessage = fatalMessage;
    }

    public void addError(String error) {
        errorList.add(error);
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getErrorCount() {
        return errorList == null ? 0 : errorList.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOutput() {
        if (!StringUtils.isEmpty(fatalMessage)) {
            return fatalMessage;
        }

        int errorCount =errorUserList.size();
        if (errorCount == 0) { //文件格式错的也会执行这里
            return String.format("总共上传%d条记录.", totalCount);
        } else { //说明到了记数阶段
            String retMsg = String.format("总共上传%d条记录,有问题%d条;", totalCount, errorCount);
            //retMsg += errorList.stream().collect(Collectors.joining());
            return retMsg;
        }
    }
}
