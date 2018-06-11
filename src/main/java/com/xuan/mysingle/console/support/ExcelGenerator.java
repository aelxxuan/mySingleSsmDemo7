/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class ExcelGenerator {

    /**
     * 创建单元格基本样式
     *
     * @param wb
     * @return
     */
    private static HSSFCellStyle createBasicStyle(HSSFWorkbook wb) {
        //sheet 样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置背景色
        cellStyle.setFillForegroundColor((short) HSSFColor.WHITE.index);//前景色的设置
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充模式
        setBorder(cellStyle, true);
        //设置自动换行
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 个性化创建单元格样式
     *
     * @param wb
     * @param color
     * @param fontHeight
     * @param bold
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook wb, short color, short fontHeight, boolean bold) {
        HSSFCellStyle cellStyle = createBasicStyle(wb);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints(fontHeight);
        font.setColor(color);
        font.setBold(bold);
        if (bold) {
            //粗体显示
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建大标题样式
     *
     * @param wb
     * @return
     */
    private static HSSFCellStyle createBigTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = createStyle(wb, HSSFColor.RED.index, (short) 14, true);
        setBorder(style, false);
        return style;
    }

    /**
     * 创建table标题样式
     *
     * @param wb
     * @return
     */
    private static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        return createStyle(wb, HSSFColor.BLACK.index, (short) 12, true);
    }

    /**
     * 创建表格首部尾部文字样式
     *
     * @param wb
     * @return
     */
    private static HSSFCellStyle createHeadTailStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = createStyle(wb, HSSFColor.GREEN.index, (short) 10, false);
        //设置自动换行
        style.setWrapText(false);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        setBorder(style, false);
        return style;
    }

    /**
     * 创建表格内容区样式
     *
     * @param wb
     * @return
     */
    private static HSSFCellStyle createContentStyle(HSSFWorkbook wb) {
        return createStyle(wb, HSSFColor.BLACK.index, (short) 10, false);
    }

    /**
     * 设置是否有边框
     *
     * @param style
     * @param withBorder
     */
    private static void setBorder(HSSFCellStyle style, boolean withBorder) {
        short border = withBorder ? HSSFCellStyle.BORDER_THIN : HSSFCellStyle.BORDER_NONE;
        //下边框
        style.setBorderBottom(border);
        //左边框
        style.setBorderLeft(border);
        //上边框
        style.setBorderTop(border);
        //右边框
        style.setBorderRight(border);
    }


    /**
     * 通用的生成excel数据
     *
     * @param titles    标题
     * @param dataList  数据列表
     * @param transFunc 将数据列表中某数据转换成具体某个单元格数据的转换器
     * @param extInfo   生成Excel时的额外信息
     * @return
     * @throws IOException
     */
    public static <T> byte[] generate(String[] titles, List<T> dataList, BiFunction<T, Integer, String> transFunc, ExcelGeneratorExtendInfo<T> extInfo) throws IOException {
        //创建一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建一个Excel的Sheet
        HSSFSheet sheet = wb.createSheet("sheet1");

        //设置列宽
        int[] colWidth = extInfo.getColWidth();
        for (int i = 0; i < titles.length; i++) {
            sheet.setColumnWidth((short) i, colWidth == null ? 20 * 256 : colWidth[i] * 256);
        }

        //输出大标题
        int rowNo = 0;
        HSSFRow row = null;
        if (!StringUtils.isEmpty(extInfo.getBigTitle())) {
            row = sheet.createRow(rowNo);
            row.setHeightInPoints((short) 25);
            HSSFCell cell = row.createCell(0);
            HSSFCellStyle bigTitleStyle = createBigTitleStyle(wb);
            cell.setCellStyle(bigTitleStyle);
            cell.setCellValue(extInfo.getBigTitle());
            //合并大标题的列
            sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, titles.length - 1));
            rowNo++;
        }

        //输出顶部信息
        if (extInfo.getHeadBeforeRowCount() > 0) {
            for (int i = 0; i < extInfo.getHeadBeforeRowCount(); i++) {
                sheet.createRow(rowNo++);
            }
        }
        if (!StringUtils.isEmpty(extInfo.getHeadLeft()) || !StringUtils.isEmpty(extInfo.getHeadRight())) {
            HSSFCellStyle headTailStyle = createHeadTailStyle(wb);
            row = sheet.createRow(rowNo);
            int headCount = 0;
            if (!StringUtils.isEmpty(extInfo.getHeadLeft())) {
                headCount++;
                HSSFCell cell = row.createCell(0);
                cell.setCellStyle(headTailStyle);
                cell.setCellValue(extInfo.getHeadLeft());
            }
            if (!StringUtils.isEmpty(extInfo.getHeadRight())) {
                headCount++;
                HSSFCell cell = row.createCell(titles.length - 1);
                cell.setCellStyle(headTailStyle);
                cell.setCellValue(extInfo.getHeadRight());
            }
            if (headCount == 1) {
                //只有1个顶部文字,则将该行合并
                sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, titles.length - 1));
            }

            rowNo++;
        }
        if (extInfo.getHeadAfterRowCount() > 0) {
            for (int i = 0; i < extInfo.getHeadAfterRowCount(); i++) {
                sheet.createRow(rowNo++);
            }
        }

        //输出第一行标题
        row = sheet.createRow(rowNo++);
        row.setHeightInPoints((short) 20);
        int colCount = titles.length;
        int colIdx = 0;
        HSSFCellStyle titleStyle = createTitleStyle(wb);
        for (String title : titles) {
            HSSFCell cell = row.createCell(colIdx++);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(title);
        }

        //输出具体数据行
        if (dataList != null && dataList.size() > 0) {
            HSSFCellStyle contentStyle = createContentStyle(wb);
            dataList.forEach(rowData -> {
                if (extInfo.isRowDataIsMulti()) {
                    //list里的1个数据输出到excel时需要变成多行输出
                    Integer rowCount = extInfo.multiRowCountGetter.apply(rowData);
                    //没有明细数据时
                    if (rowCount == 0 && extInfo.isOutputRowIfRowCountIsZero()) {
                        rowCount = 1;
                    }
                    for (int rr = 0; rr < rowCount; rr++) {
                        HSSFRow row1 = sheet.createRow(sheet.getLastRowNum() + 1);
                        int cellIdx = 0;
                        for (int i = 0; i < colCount; i++) {
                            HSSFCell cell = row1.createCell(cellIdx++);
                            cell.setCellStyle(contentStyle);
                            String cellValue = extInfo.muiltiRowDataGetter.apply(rowData, new Integer[]{rr, i});
                            cell.setCellValue(cellValue);
                        }
                    }
                } else {
                    HSSFRow row1 = sheet.createRow(sheet.getLastRowNum() + 1);
                    int cellIdx = 0;
                    for (int i = 0; i < colCount; i++) {
                        HSSFCell cell = row1.createCell(cellIdx++);
                        cell.setCellStyle(contentStyle);
                        String cellValue = transFunc.apply(rowData, i);
                        cell.setCellValue(cellValue);
                    }
                }
            });
        }

        //输出尾部信息
        rowNo = sheet.getLastRowNum() + 1;
        if (extInfo.getTailBeforeRowCount() > 0) {
            for (int i = 0; i < extInfo.getTailBeforeRowCount(); i++) {
                sheet.createRow(rowNo++);
            }
        }
        if (!StringUtils.isEmpty(extInfo.getTailLeft()) || !StringUtils.isEmpty(extInfo.getTailRight())) {
            row = sheet.createRow(rowNo++);
            HSSFCellStyle headTailStyle = createHeadTailStyle(wb);
            int tailCount = 0;
            if (!StringUtils.isEmpty(extInfo.getTailLeft())) {
                tailCount++;
                HSSFCell cell = row.createCell(0);
                cell.setCellStyle(headTailStyle);
                cell.setCellValue(extInfo.getTailLeft());
            }
            if (!StringUtils.isEmpty(extInfo.getTailRight())) {
                tailCount++;
                HSSFCell cell = row.createCell(titles.length - 1);
                cell.setCellStyle(headTailStyle);
                cell.setCellValue(extInfo.getTailRight());
            }
            if (tailCount == 1) {
                //只有1个底部文字,则将该行合并
                sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, titles.length - 1));
            }
        }

        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        wb.write(outPut);
        return outPut.toByteArray();
    }

    /**
     * Excel生成时的额外信息
     */
    public final static class ExcelGeneratorExtendInfo<T> {
        /**
         * 设置列宽
         */
        private int[] colWidth = null;
        /**
         * 大标题文字
         */
        private String bigTitle;

        /**
         * excel里头部信息前空行数
         */
        private int headBeforeRowCount = 0;
        /**
         * excel里头部信息后空行数
         */
        private int headAfterRowCount = 0;
        /**
         * 左上角头部信息
         */
        private String headLeft;
        /**
         * 右上角头部信息
         */
        private String headRight;

        /**
         * 左下角尾部信息
         */
        private String tailLeft;
        /**
         * 右下角尾部信息
         */
        private String tailRight;
        /**
         * 尾部信息输出前空行数
         */
        private int tailBeforeRowCount = 0;

        /**
         * 标志list里的1行数据在输出到excel时变成多行输出
         */
        private boolean rowDataIsMulti = false;

        /**
         * rowDataIsMulti=true时如果根据multiRowCountGetter得到的行数是0,是否还继续输出1行数据到excel
         * true则调用muiltiRowDataGetter函数得到单元格数据
         */
        private boolean outputRowIfRowCountIsZero = true;

        /**
         * 获得某一行数据转换成多行时能转换成的行数
         */
        private Function<T, Integer> multiRowCountGetter;

        /**
         * 转行多行时数据转换器，
         * T 表示List里的某个元素，
         * Integer[]数组长度=2，0表示行号，1表示列号。
         */
        private BiFunction<T, Integer[], String> muiltiRowDataGetter;

        public void setRowDataIsMulti(boolean rowDataIsMulti) {
            this.rowDataIsMulti = rowDataIsMulti;
        }

        public void setOutputRowIfRowCountIsZero(boolean outputRowIfRowCountIsZero) {
            this.outputRowIfRowCountIsZero = outputRowIfRowCountIsZero;
        }

        public void setMuiltiRowDataGetter(BiFunction<T, Integer[], String> muiltiRowDataGetter) {
            this.muiltiRowDataGetter = muiltiRowDataGetter;
        }

        public boolean isRowDataIsMulti() {
            return rowDataIsMulti;
        }

        public boolean isOutputRowIfRowCountIsZero() {
            return outputRowIfRowCountIsZero;
        }

        public void setMultiRowCountGetter(Function<T, Integer> multiRowCountGetter) {
            this.multiRowCountGetter = multiRowCountGetter;
        }

        public void setMuiltyRowDataGetter(BiFunction<T, Integer[], String> muiltiRowDataGetter) {
            this.muiltiRowDataGetter = muiltiRowDataGetter;
        }

        public int[] getColWidth() {
            return colWidth;
        }

        public void setColWidth(int[] colWidth) {
            this.colWidth = colWidth;
        }

        public String getBigTitle() {
            return bigTitle;
        }

        public void setBigTitle(String bigTitle) {
            this.bigTitle = bigTitle;
        }

        public int getHeadBeforeRowCount() {
            return headBeforeRowCount;
        }

        public void setHeadBeforeRowCount(int headBeforeRowCount) {
            this.headBeforeRowCount = headBeforeRowCount;
        }

        public int getHeadAfterRowCount() {
            return headAfterRowCount;
        }

        public void setHeadAfterRowCount(int headAfterRowCount) {
            this.headAfterRowCount = headAfterRowCount;
        }

        public String getHeadLeft() {
            return headLeft;
        }

        public void setHeadLeft(String headLeft) {
            this.headLeft = headLeft;
        }

        public String getHeadRight() {
            return headRight;
        }

        public void setHeadRight(String headRight) {
            this.headRight = headRight;
        }

        public String getTailLeft() {
            return tailLeft;
        }

        public void setTailLeft(String tailLeft) {
            this.tailLeft = tailLeft;
        }

        public String getTailRight() {
            return tailRight;
        }

        public void setTailRight(String tailRight) {
            this.tailRight = tailRight;
        }

        public int getTailBeforeRowCount() {
            return tailBeforeRowCount;
        }

        public void setTailBeforeRowCount(int tailBeforeRowCount) {
            this.tailBeforeRowCount = tailBeforeRowCount;
        }
    }
}
