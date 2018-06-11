/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common;

import java.util.Random;

/**
 * 随机数生成工具类
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/10
 */
public class RadomNumberGenerator {
    private static final String NumAll = "0123456789";
    private static final String StrAll = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 随机生成指定长度的字符串(0-9数字和A-Z大写字母随机组合)
     *
     * @param len
     * @return
     */
    public static String getStringRandom(int len) {
        //定义一个包含数字，大小写字母的字符串
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int rd = random.nextInt(36);
            stringBuilder.append(StrAll.substring(rd, rd + 1));
        }
        return stringBuilder.toString();

    }

    /**
     * 生成指定长度的数字
     *
     * @param len
     * @return
     */
    public static String getNumberRandom(int len) {
        //定义一个包含字符串
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int rd = random.nextInt(10);
            stringBuilder.append(NumAll.substring(rd, rd + 1));
        }
        return stringBuilder.toString();

    }
}
