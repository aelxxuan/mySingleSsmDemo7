/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/16
 */
public class Word2003Test {
    public static void main(String[] args) throws Exception {
        String templatePath = "D:\\word\\template.doc";
        InputStream is = new FileInputStream(templatePath);


    }


    /**
     * 输出SummaryInfomation
     * @param info
     */
    private static void printInfo(SummaryInformation info) {
        //作者
        System.out.println(info.getAuthor());
        //字符统计
        System.out.println(info.getCharCount());
        //页数
        System.out.println(info.getPageCount());
        //标题
        System.out.println(info.getTitle());
        //主题
        System.out.println(info.getSubject());
    }

    /**
     * 输出DocumentSummaryInfomation
     * @param info
     */
    private static void printInfo(DocumentSummaryInformation info) {
        //分类
        System.out.println(info.getCategory());
        //公司
        System.out.println(info.getCompany());
    }

    /**
     * 关闭输入流
     * @param is
     */
    private static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
