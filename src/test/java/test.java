/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/16
 */
public class test {

    public static void main(String[] args) {
        //System.out.println(LocalDateTime.now().getMonthValue());
        //System.out.println(LocalDateTime.now().getMonth());

        Matcher matcher= matcher("你购买的产品为： ${ productname}");
        //System.out.println(matcher.find());

        String name= "张三.李四";
        //System.out.println(cheakChenese(name));

       UUID uuid=   UUID.randomUUID();
        System.out.println(uuid.toString().replace("-","").toUpperCase());



    }

    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    private static boolean cheakChenese(String str) {
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        Matcher matcher = pattern.matcher(str.replace(".",""));
        return matcher.matches();
    }


}
