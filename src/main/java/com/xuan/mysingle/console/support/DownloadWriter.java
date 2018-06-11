/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class DownloadWriter {
    /**
     * 输出字节数据到浏览器客户端以便用户下载
     *
     * @param response
     * @param bytesData   文件内容字节数组
     * @param contentType 文件类型
     * @param fileName    文件名称
     * @throws IOException
     */
    public static void writeToResponse(HttpServletResponse response, byte[] bytesData, String contentType, String fileName) throws IOException {
        fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentLength(bytesData.length);
        response.getOutputStream().write(bytesData);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
