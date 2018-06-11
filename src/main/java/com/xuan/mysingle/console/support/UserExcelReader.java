/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.xuan.mysingle.common.user.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读取公用类
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class UserExcelReader {
    private InputStream is = null;
    private Workbook wb;
    private Sheet sheet;

    //是否excel 2003版本.根据扩展名是否xls判断
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 根据学校用户白名单文件分析出白名单用户数据
     *
     * @param
     * @return
     * @throws IOException
     */
    public static ResponseVo<String[], User> getExcelData(String fileName) {
        UserExcelReader excelReader = new UserExcelReader();
        try {
            //初始化
            excelReader.initExcelReader(fileName);
            // 对读取Excel表格标题测试
            String[] title = excelReader.readExcelTitle();
            // 对读取Excel表格内容测试
            List<User> list = excelReader.readExcelContent();
            ResponseVo<String[], User> resp = new ResponseVo<>();
            resp.setEntity(title);
            resp.setList(list);
            return resp;

        } finally {
            //关闭文件流
            excelReader.closeReader();
        }
    }

    private void initExcelReader(String fileName) {
        boolean isXls2003 = isExcel2003(fileName);
        //兼容xls2003和xlsx2007版本 参考 http://blog.csdn.net/mmm333zzz/article/details/7962377
        try {
            InputStream is = new FileInputStream(fileName);
            if (isXls2003) {
                //用2003格式读取
                wb = new HSSFWorkbook(is);
            } else {
                //用2007格式读取
                wb = new XSSFWorkbook(is);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SsmException("读取excel出现问题", ex);
        }

        sheet = wb.getSheetAt(0);
    }

    private void closeReader() {
        try {
            if (wb != null) {
                wb.close();
            }
        } catch (Exception e) {
        }
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 读取Excel表格表头的内容
     *
     * @param
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle() {
        Row row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        try {
            for (int i = 0; i < colNum; i++) {
                title[i] = getCellValue(row.getCell(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SsmException("第1行表头有问题");
        }
        return title;
    }

    /**
     * 读取excel的内容，将每行结果转换成一个User的实例
     *
     * @return
     */
    public List<User> readExcelContent() {
        List<User> list = new ArrayList();
        User user = null;
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        //总列数以第1行为准
        int colNum = row.getPhysicalNumberOfCells();
        int j = 0;
        // 正文内容应该从第二行开始,第一行为表头的标题
        //实际中可能存在中间几行没数据的情况
        //实际中可能存在最后几行原本有数据，用户删除了，表面看没数据其实sheet.getLastRowNum()返回的行数还是包括了最后几行的情况
        for (int i = 1; i <= rowNum; i++) {
            try {
                row = sheet.getRow(i);
                user = new User();

                if (row == null) {
                    //本行曾经有数据后来删除(逐个单元格删除非右键整行删除)了会存在这种情况
                    //第n行未填写数据，直接填写n+2行以后的数据，则n,n+1行都会null

                    // list.add(user);//这种也需要记录,否则最终行数会不准确，数据行号和excel对不上
                    continue;
                }

                j = 0;
                while (j < colNum) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        String value = getCellValue(cell).trim();
                        //替换姓名和手机号中间的空格
                        value = value.replace(" ", "");

                        //截取过长的输入，避免数据库越界
                        if (value.length() > 50) {
                            value = value.substring(0, 48);
                        }
                        if (j == 0) {
                            user.setName(value);
                        } else if (j == 1) {
                            user.setLoginName(value);
                        }else if(j == 2){
                            user.setPhone(value);
                            break;//后面的不读了
                        }
                    } else {
                        //cell为空则user的属性不完整 由外部调用程序自行处理
                    }
                    j++;
                }
                //不读取空行
                if (StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getPhone()) && StringUtils.isEmpty(user.getLoginName())) {
                    continue;
                }
                list.add(user);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new SsmException(String.format(String.valueOf("读excel时第%s行%s列有问题."), String.valueOf(i + 1), String.valueOf(j + 1)), ex);
            }
        }
        return list;
    }

    /**
     * 获得某个单元格的字符串值
     *
     * @param hssfCell
     * @return
     */
    public static String getCellValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            //返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue()).trim();
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            //返回数字类型的值
            DecimalFormat df = new DecimalFormat();
            String tempString = df.format(hssfCell.getNumericCellValue());
            if (tempString.split(".").length > 2) {
                return tempString.split(".")[0].replace(",", "").trim();
            } else {
                return tempString.replace(",", "").trim();
            }
        } else {
            //返回字符串的值
            return String.valueOf(hssfCell.getStringCellValue()).trim();
        }
    }
}
