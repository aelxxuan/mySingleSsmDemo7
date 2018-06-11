/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.user;

import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.console.common.ClientIpUtils;
import com.xuan.mysingle.console.common.RadomNumberGenerator;
import com.xuan.mysingle.console.support.*;
import com.xuan.mysingle.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
@Component
public class UserComponent {
    @Autowired
    UserService userService;


    /**
     * 下载导入白名单模板
     *
     * @param response
     * @param request
     * @throws IOException
     */
    public void downloadUserImportFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String url = request.getSession().getServletContext().getRealPath("/WEB-INF/download");
        String template = url + "/importUser.xlsx";
        byte[] bytes = FileUtils.getFileContent(template);
        DownloadWriter.writeToResponse(response, bytes, "application/x-msdownload", "importUser.xlsx");
    }
    /**
     * 学校白名单用户批量excel上传并入库
     *
     * @param request
     * @param schoolId
     * @param productId excel中的用户要赋予可使用的的产品id,null则授予所有公用产品权限
     * @throws IOException
     */
    public FileInformation uploadFile(HttpServletRequest request, Integer schoolId, String productId, String operator) throws IOException {

        //上传结果详情信息
        FileInformation info = new FileInformation();

        //如果想在controller中能用Value注解 必须修改配置，配置文件中只放一个路径不同的业务配置不同的文件就一个配置就可以
        String path =userService.getUploadPath();
        //创建一个通用的多部分解析器，用于上传文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //判断request是否有文件上传，即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            MultipartFile file = null;
            while (iter.hasNext()) {
                //根据name获取上传的文件,name是文件上传标签的name值
                file = multiRequest.getFile(iter.next());
                if (file == null) {
                    continue;
                }

                String myFileName = file.getOriginalFilename();
                if (StringUtils.isEmpty(myFileName)) {
                    continue;
                }

                String extName = myFileName.substring(myFileName.lastIndexOf("."), myFileName.length());
                if (!(".xls".equals(extName.toLowerCase())
                        || ".xlsx".equals(extName.toLowerCase()))) {
                    info.setFatalMessage("请上传.xls或.xlsx文件");
                    //break和continue一样 因为只有一个文件
                    break;
                }

                //防止重名 要定期删除，因为linux下 一个文件夹下最大文件数有限制 myFileName带着扩展名
                if (myFileName.indexOf(File.separator) > 0) {
                    myFileName = myFileName.substring(myFileName.lastIndexOf(File.separator) + 1);
                }
                //避免文件名重复
                String newFileName = path + File.separator + UUID.randomUUID() + "_" + myFileName;
                //保存上传的文件到指定文件
                FileUtils.writeFileContent(newFileName, file.getBytes());
                file.getInputStream().close();

                //将文件内容读取成标准结构
                ResponseVo<String[], User> dataVo = readExcelUsers(newFileName, info);
                if (dataVo == null) {
                    continue;
                }

                String[] titles = dataVo.getEntity();
                List<User> userList = dataVo.getList();
                if (userList.size() <= 0) {
                    info.setFatalMessage("上传文件为空，请重新上传");
                    break;
                }
                //导入的手机号在本校已存在则update
                boolean sameSchoolUpdate = true;

                //验证上传数据的正确性可用性
                boolean checkPass = checkUserData(info, titles, userList);
                if (!checkPass) {
                    continue;
                }

                if (info.getTotalCount() == 0) {
                    continue;
                }
                String ipAddress = ClientIpUtils.getIpAddress(request);
                insertUsers(userList,ipAddress);
            }
        }
        return info;
    }

    /**
     * 从文件中读取白名单用户
     *
     * @param excelFileName
     * @param info
     * @return
     */
    public ResponseVo<String[], User> readExcelUsers(String excelFileName, FileInformation info) {
        try {
            //读取excel数据到map
            ResponseVo<String[], User> dataVo = UserExcelReader.getExcelData(excelFileName);
            return dataVo;
        } catch (Exception ex) {
            if (ex instanceof SsmException) {
                info.setFatalMessage("文件内容异常." + ex.getMessage());
            } else {
                info.setFatalMessage("文件内容异常.");
                ex.printStackTrace();
            }
            return null;
        } finally {
            //删除上传的文件,避免占用磁盘
            try {
                File f = new File(excelFileName);
                if (f.exists()) {
                    f.delete();
                }
            } catch (Throwable t) {
            }
        }
    }

    /**
     * 目前对列名判断
     *
     * @param
     * @return
     */
    private String checkTitles(String[] titles) {
        if (titles == null || titles.length < 3 || titles[0] == null || titles[1] == null || titles[2] == null) {
            return "文件列名不符合模板";
        }
        if (!("姓名".equals(titles[0].trim()) && "用户名".equals(titles[1].trim()) &&  "手机号".equals(titles[2].trim()))) {
            return "列名称不对";
        }
        return null;
    }

    /**
     * 验证上传数据的完整性,包括：
     *
     * 1 标题是否符合模板；
     * 2 每行数据中姓名、用户名、手机号是否齐全；
     * 3 手机号格式是否有问题；
     * 4 手机号是否在本次文件中重复
     * 5 手机号是否在系统中重复
     */
    private boolean checkUserData(FileInformation info, String[] titles, List<User> list) {
        String result = checkTitles(titles);
        if (result != null) {
            info.setFatalMessage(result);
            return false;
        }

        //读取时，已经删除了空行
        info.setTotalCount(list.size());
        //万一这次有重复的不止数据库中有没有
        List<String> phones = new ArrayList<String>();
        User user = null;
        List<User> errorList = new ArrayList<User>();
        List<User> emptyList = new ArrayList<User>();
        //需要在用户系统注册的用户手机和记录行号
        List<KeyValueInfo<String, Integer>> needRegUserPhoneList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            user = list.get(i);
            if (StringUtils.isEmpty(user.getPhone()) && StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getLoginName())) {
                emptyList.add(user);
                continue;
            }
            if (StringUtils.isEmpty(user.getPhone()) && !StringUtils.isEmpty(user.getName())) {
                user.setDescription("没填全,缺少手机号;");
                errorList.add(user);
                continue;
            }
            if (!StringUtils.isEmpty(user.getPhone()) && StringUtils.isEmpty(user.getName())) {
                user.setDescription("没填全,缺少姓名;");
                errorList.add(user);
                continue;
            }
            if (!CommonUtil.isMobile(user.getPhone())) {
                String phone = user.getPhone();
                if (CommonUtil.isPhonePattern(phone)) {
                    user.setDescription(String.format("%s为运营商号段，请核实确为教师使用后申请由客服手动添加;", phone.substring(0, 3)));
                } else {
                    user.setDescription(String.format("手机号%s有问题;", phone));
                }
                errorList.add(user);
                continue;
            }

            if (phones.indexOf(user.getPhone()) >= 0) {
                user.setDescription(String.format("手机号%s在这个文件中多行存在;", user.getPhone()));
                errorList.add(user);
                continue;
            }
            User existUser = userService.getUserByPhone(user.getPhone());
            if (existUser != null) {
                user.setDescription(String.format("手机号%s已经存在", user.getPhone()));
                errorList.add(user);
                continue;
            }

            phones.add(user.getPhone());
        }
        info.setErrorUserList(errorList);
        list.removeAll(errorList);
        info.setNewCount(needRegUserPhoneList.size());
        return true;
    }

    /**
     * 批量插入白名单用户到DB
     *
     * @param userList
     * @return
     */
    private void insertUsers(List<User> userList,String ipAddress) {
        if(userList.size() <=0){
            return;
        }
        String md5Pwd = RadomNumberGenerator.getNumberRandom(6); //CommonUtil.encodeMD5("666666");
        userList.forEach(user->{
            user.setPassword(md5Pwd);
            user.setUserLastIp(ipAddress);
            userService.addUser(user);
        });
    }


}
