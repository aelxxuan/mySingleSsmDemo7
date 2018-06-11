/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/7
 */
public class CommonUtil {
    public static final String BLANK = " ";

    /**
     * 计算两个数字的百分比.a为被除数，b为除数.b==0时结果也是0.
     * 结果已*100,外部不需要再*100
     *
     * @param a
     * @param b
     * @param scale 小数位数
     * @return
     */
    public static float percent(int a, int b, int scale) {
        if (a == 0 || b == 0) {
            return 0f;
        }
        return new BigDecimal(a * 100).divide(new BigDecimal(b), scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        String pattern = "^1[3-6,8-9]\\d{9}|17[2-9]\\d{8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断手机号是否以170/171开头
     *
     * @param mobile
     * @return
     */
    public static boolean isPhonePattern(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        String pattern = "^17[0,1]\\d{8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断手机号是否是11或12开头
     *
     * @param mobile
     * @return
     */
    public static boolean isPhoneOneOrTwo(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        String pattern = "^1[1,2]\\d{9}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 手机号码部分隐藏
     *
     * @param mobile
     * @return
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isEmpty(mobile) || mobile.length() < 7) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }

    /**
     * 截取字符串服务于Safari浏览器
     *
     * @param message
     * @param cutLength
     * @param userAgent
     * @return
     */
    public static String cutMessageForSafari(String message, int cutLength, String userAgent) {
        String msg = message;
        boolean isSafari = userAgent.indexOf("Safari") != -1 && userAgent.indexOf("Version") != -1;
        if (isSafari && message.length() > cutLength + 3) {
            //safari里如果反馈消息过长会造成浏览器死机
            msg = message.substring(0, cutLength) + "...";
        }
        return msg;
    }

    /**
     * 过滤非法html标签
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        // 含html标签的字符串
        String htmlStr = inputString, textStr = "";
        Pattern p_script, p_style, p_html;
        Matcher m_script, m_style, m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
            out.println("h2txt error:" + e.getMessage());
        }
        // 返回文本字符串
        return textStr;
    }

    /**
     * 验证输入的是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String patternStr = "[0-9]+";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * MD5加密
     *
     * @param sourceString
     * @return
     */
    public static String encodeMD5(String sourceString) {
        String resultString = null;
        try {
            resultString = sourceString;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes("utf-8")));

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return resultString;
    }

    private static String byte2hexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            if (((int) aByte & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) aByte & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * stream 的distinct方法本身没有参数，针对自定义类如果要distinct就无法实现，此方法变通实现。
     * stream调用filter方法时根据
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    /**
     * json 转换为对象
     *
     * @param str   json格式的字符串
     * @param clazz 转换的目标类
     * @return
     */
    public static <T> T jsonToObject(String str, Class<T> clazz) {
        JSONObject jsStr = JSONObject.parseObject(str);
        return JSONObject.toJavaObject(jsStr, clazz);
    }

    private static String[] productLevel = new String[]{"pt","zd","gd"};

    /**
     * 等级对比，如果destLevel高于sourceLevel返回值大于0
     * @param sourceLevel 参照物
     * @param destLevel 跟参照比较的产品级别
     * @return
     */
    public static int compareSchoolProductLevel(String sourceLevel,String destLevel){
        int oldIndex = 0;
        int newIndex = 0;
        for(int i=0 ;i < productLevel.length;i++){
            if(sourceLevel.equals(productLevel[i])){
                oldIndex = i;
            }
            if(destLevel.equals(productLevel[i])){
                newIndex = i;
            }
        }
        return newIndex - oldIndex;
    }
}
