/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class FileUtils {
    /**
     * NIO way
     * 返回文件字节
     *
     * @param filename 要读取的文件
     * @return 文件内容字节数组
     * @throws IOException
     */
    public static byte[] getFileContent(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 把内容写入文件
     *
     * @param filename 要写入的文件
     * @param bytes    文件内容字节数组
     * @return
     * @throws IOException
     */
    public static void writeFileContent(String filename, byte[] bytes) throws IOException {
        File localFile = new File(filename);
        File parentFile = localFile.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();//如果父目录不存在则创建父目录，否则后续输出文件会异常
        }

        if (!localFile.exists()) {
            localFile.createNewFile();
        }
        //可能是跟配置中2个文件上传有关将来最好用下面的生成文件
        //  file.transferTo(localFile);
        //下面的代码将来要重构
        FileOutputStream fos = new FileOutputStream(localFile);
        // get the content in bytes
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    /**
     * 读取inputSteam
     *
     * @param inStream
     * @return 字节数组
     * @throws IOException
     */
    public static final byte[] readInputStream(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        swapStream.close();
        return in2b;
    }
}
