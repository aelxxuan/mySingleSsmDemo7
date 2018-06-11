/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/16
 */
public class WordWriteTest {
    public static void main(String[] args) throws  Exception{
        //writeWord();
        //writeTable();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currentdate", "2014-02-28");
        params.put("productname", "小学学科网");
        params.put("servicecode", "ZXXKRE");
        params.put("schoolname", "北京第一中学");
        InputStream inputStream = new FileInputStream("D://template.docx");
        XWPFDocument doc = new XWPFDocument(inputStream);
        replaceInPara(doc,params);
        OutputStream outputStream = new FileOutputStream("D://test44.docx");
        doc.write(outputStream);
        closInput(inputStream);
        close(outputStream);
        System.out.println("成功");

    }

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                        System.out.println(runText);
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }


    /**
     * 关闭输入流
     * @param is
     */
    private static void closInput(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 写Word
     */
    public static void writeWord() throws  Exception{
        //新建一个文档
        XWPFDocument doc = new XWPFDocument();
        //创建一个段落
        XWPFParagraph pa = doc.createParagraph();

        //一个XWPFRun代表具有相同属性的一个区域
        XWPFRun run = pa.createRun();
        run.setBold(true);//加粗
        run.setText("加粗的内容");
        run  = pa.createRun();
        run.setColor("FF0000");
        run.setText("红色字体");
        OutputStream out = new FileOutputStream("D://test2.docx");

        //输出到输出流
        doc.write(out);
        close(out);
        System.out.println("OK");

    }

    /**
     * 写入一个表格
     */
    public static void writeTable() throws  Exception{
        //创建一个文档
        XWPFDocument doc = new XWPFDocument();
        //创建5行5列的表格
        XWPFTable table = doc.createTable(5,5);
        //table.addNewCol();//增加一列,存在问题,不能写入数据，只有最后一行能写入数据
        table.createRow();//增加一行
        //table.addNewRowBetween(5,6);
        List<XWPFTableRow> rows = table.getRows();

        //表格属性;
        CTTblPr tablePr= table.getCTTbl().addNewTblPr();
        //表格宽度
        CTTblWidth width= tablePr.addNewTblW();
        width.setW(BigInteger.valueOf(8000));
        XWPFTableRow row;
        List<XWPFTableCell> cells;
        XWPFTableCell cell;
        int rowSize = rows.size();
        int cellSize;

        for(int i=0;i<rowSize;i++){
            row = rows.get(i);
            //新增单元格
            //row.addNewTableCell();
            //设置行的高度
            row.setHeight(500);
            cells = row.getTableCells();
            cellSize = cells.size();
            for(int j=0;j<cellSize;j++){
                cell = cells.get(j);
                if((i+j)%2==0){
                    //设置单元格的颜色
                    cell.setColor("ff0000");//红色
                }else{
                    cell.setColor("0000ff");
                }
                CTTcPr cellPr = cell.getCTTc().addNewTcPr();
                cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
               if(j==3){
                    //设置宽度
                    cellPr.addNewTcW().setW(BigInteger.valueOf(3000));
                }
                cell.setText(i+","+j);
            }
        }
        //文件不存在是自动创建
        OutputStream out = new FileOutputStream("D://test3.docx");
        //写入文件
        doc.write(out);
        close(out);
        System.out.println("OK");
    }



    private static void close(OutputStream out){
        if(out !=null){
            try{
                out.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
