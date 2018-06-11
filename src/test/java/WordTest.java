/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/15
 */
public class WordTest {

    public static void main(String[] args) {
        String path="D://test.docx";
        //readWord (path);

        readWord2(path);

    }

    /**
     * 使用： XWPFDocument. 读取docx
     * @param path
     */
    private static void readWord2(String path){
        try{
            FileInputStream in = new FileInputStream(path);
            XWPFDocument document = new XWPFDocument(in);
            List<XWPFParagraph> paras = document.getParagraphs();
            for(XWPFParagraph pa :paras){
                System.out.println(pa.getText());
                System.out.println(pa.getCTP().getPPr());
            }
            //获取文档中所有的表格
            List<XWPFTable>  tables = document.getTables();
            List<XWPFTableRow> rows;
            List<XWPFTableCell> cells;
            for(XWPFTable ta:tables){
                CTTblPr pr = ta.getCTTbl().getTblPr();
                //获取表格对应行
                rows =ta.getRows();
                for(XWPFTableRow row:rows){
                    cells = row.getTableCells();
                    for(XWPFTableCell cell:cells){
                        System.out.println(cell.getText());
                    }
                }
            }
            close(in);

        }catch (FileNotFoundException e){}
        catch(IOException e){}

    }


    /**
     * 使用 XWPFWordExtrator 读取docx
     * @param path
     */
    private static void readWord (String path){
        try{
            FileInputStream in = new FileInputStream(path);
            XWPFDocument doc = new XWPFDocument(in);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String str = extractor.getText();//读取文本
            System.out.println(str);
            CoreProperties core = extractor.getCoreProperties();
            printCoreProperties(core);
            close(in);

        }catch (Exception e){}
    }

    //读取属性
    public static void printCoreProperties(CoreProperties core){
        System.out.println(core.getCategory());//分类
        System.out.println(core.getCreator());//创建者
        System.out.println(core.getContentType());
        System.out.println(core.getDescription());
        System.out.println(core.getCreated());//创建时间
        System.out.println(core.getTitle());
    }

    private static void close(InputStream is){
        if(is !=null){
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
