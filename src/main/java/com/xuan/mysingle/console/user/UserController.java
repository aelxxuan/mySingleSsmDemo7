/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.user;

import com.alibaba.fastjson.JSON;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.log.support.Loggable;
import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.console.common.AvatarData;
import com.xuan.mysingle.console.menu.ResponseCodeEnum;
import com.xuan.mysingle.console.support.*;
import com.xuan.mysingle.core.log.LogService;
import com.xuan.mysingle.core.redis.JRedisHelp;
import com.xuan.mysingle.core.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.xuan.mysingle.console.common.PictureTool.ImageUtils.uploadCutImg;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/19
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final java.util.List<String> imgTypes = Arrays.asList(ImageIO.getReaderFormatNames());
    //region 注入实体
    @Autowired
    UserService userService;
    @Autowired
    UserComponent userComponent;
    @Autowired
    FileHelp fileHelp;
    @Autowired
    LogService logService;
    @Autowired
    JRedisHelp jRedisHelp;


    private long maxUploadSize = 20991520;

    static final int MAX_TRUMB_SIZE = 160;
    //endregion

    @RequestMapping("/getlist")
    public String getView() {
        return "user/userlist";
    }

    @RequestMapping("/searchUser")
    public String searchUser(Model model, @RequestParam String name, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Pager pager = new Pager(pageIndex, pageSize);
        List<User> userList = userService.getUserByPage(name, pager);
        model.addAttribute("userList", userList);
        model.addAttribute("pager", pager);
        logger.debug("这个是测试数据");
        return "user/usercontent";
    }
    @Loggable(logType= LogType.DATA, value = "增加用户：#{#user}")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addUser(@Valid User user) {
        ResponseVo vo = new ResponseVo();
        String md5Pwd = CommonUtil.encodeMD5(user.getPassword());
        user.setPassword(md5Pwd);
        boolean res = userService.addUser(user);
        vo.setSuccess(res);
        if (!res) {
            vo.setMessage("添加失败！");
        }
        jRedisHelp.setRedisValue("test",123);
        //int value= jRedisHelp.getRedisValue("test");
        return vo;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public User getByUserId(@RequestParam int userId, HttpServletRequest request) {
        User user = userService.getById(userId);
        String url = request.getRequestURL().toString();
        if (!StringUtils.isEmpty(user.getPicture())) {
            if (user.getPicture().contains("/images/userface")) {
                url = url.substring(0, url.indexOf("/user") + 1);
                user.setPictureUrl(url + user.getPicture());
            }
        }
        return user;
    }

    /**
     * 修改用户
     *
     * @param user 用户
     * @return
     */
    @Loggable(logType = LogType.DATA,value = "修改用户：#{#user}",operator = "alex")
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo updateUser(@Valid User user) {
        ResponseVo vo = new ResponseVo();
        if (user.getPassword().length() != 32) {
            user.setPassword(CommonUtil.encodeMD5(user.getPassword()));
        }
        boolean res = userService.updateUser(user);
        vo.setSuccess(res);
        if (!res) {
            vo.setMessage("修改失败");
        }
        return vo;
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return
     */
    @Loggable(logType= LogType.DATA, value = "删除用户：#{#userId}")
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseVo deleteUser(@RequestParam int userId) {
        ResponseVo vo = new ResponseVo();
        boolean res = userService.deleteUser(userId);
        vo.setSuccess(res);
        if (!res) {
            vo.setMessage("删除失败");
        }
        return vo;
    }

    @RequestMapping(value = "/exportuser")
    public void exportUser(HttpServletResponse response, @RequestParam String name) throws IOException {
        Pager page = new Pager(0, Integer.MAX_VALUE);
        List<User> list = userService.getUserByPage(name, page);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] title = new String[]{"用户ID", "姓名", "用户名", "手机号", "IP地址", "开通时间"};
        ExcelGenerator.ExcelGeneratorExtendInfo ext = new ExcelGenerator.ExcelGeneratorExtendInfo();
        ext.setColWidth(new int[]{10, 15, 20, 20, 30, 25});//设置每列的宽度，默认为20
        ext.setTailLeft(String.format("总的条数为%s条", list.size()));
        ext.setBigTitle("用户列表数据");
        byte[] bytes = ExcelGenerator.generate(title, list, (su, colIdx) -> {
            switch (colIdx) {
                case 0:
                    return String.valueOf(su.getUserId());
                case 1:
                    return su.getName() == null ? "" : su.getName();
                case 2:
                    return su.getLoginName() == null ? "" : su.getLoginName();
                case 3:
                    return su.getPhone() == null ? "" : su.getPhone();
                case 4:
                    return su.getUserLastIp();
                case 5:
                    return sdf.format(su.getAddTime());
                default:
                    return "";
            }
        }, ext);
        //只能到处扩展名为：xls的文件，xlsx不支持
        DownloadWriter.writeToResponse(response, bytes, "application/x-msdownload", "用户列表.xls");
    }

    /**
     * 下载成员信息表模板
     *
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, HttpServletRequest request) throws IOException {
        userComponent.downloadUserImportFile(response, request);
    }

    /**
     * 文件上传
     *
     * @param
     * @return
     * @see UserController
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(Model model, HttpServletRequest request) throws IOException {
        //如果想在controller中能用Value注解 必须修改配置，配置文件中只放一个路径不同的业务配置不同的文件就一个配置就可以
        fileHelp.uploadFile(model, request);
        return "user/upload_result";
    }

    /**
     * 下载错误列表
     *
     * @param response  response
     * @param errorList 错误列表
     * @throws IOException
     */
    @RequestMapping(value = "/exportErrorList")
    public void exportErrorList(HttpServletResponse response, String errorList) throws IOException {
        List<User> userList = JSON.parseArray(errorList, User.class);
        byte[] bytes = fileHelp.downErrorExcel(userList);
        DownloadWriter.writeToResponse(response, bytes, "application/x-msdownload", "错误教师数据.xls");
    }

    /**
     * 打开上传图片页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyUserFace", method = RequestMethod.GET)
    public String modifyUserFace(Model model) {

        return "user/modifyUserFace";
    }

    /**
     * 保存系统图片
     */
    @RequestMapping(value = "/uploadSystemUserFace", method = RequestMethod.GET)
    public void uploadSystemUserFace() {

    }

    /**
     * 保存自定义图片
     */
    @RequestMapping(value = "/uploadCustomUserFace", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo uploadCustomUserFace(@RequestParam("avatar_file") MultipartFile file, @RequestParam("avatar_data") String avatar_data, HttpServletRequest request) {
        if (file == null) {
            return new ResponseVo(ResponseCodeEnum.PARAMETERERROR, "上传失败，文件不能为空");
        }
        if (file.getSize() > maxUploadSize) {
            return new ResponseVo(ResponseCodeEnum.PARAMETERERROR, "您上传的文件过大,不能超过" + maxUploadSize / 1024 / 1024 + "M");
        }
        AvatarData ad = null;
        if (!StringUtils.isEmpty(avatar_data)) {
            try {//获取图像截取的位置
                ad = JSON.parseObject(avatar_data, AvatarData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ad == null) {
            return new ResponseVo(ResponseCodeEnum.PARAMETERERROR, "参数有误");
        }
        String originalFilename = file.getOriginalFilename();//得到上传时的文件名。
        //获取图片后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (suffix == null || !imgTypes.contains(suffix)) {
            return new ResponseVo(ResponseCodeEnum.PARAMETERERROR, "不支持的图片格式");
        }
        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        int path = LocalDateTime.now().getMonthValue();
        fileName = "/" + path + "/" + fileName;
        String realPath = request.getSession().getServletContext().getRealPath("/images/userface") + fileName;
        String url = request.getRequestURL().toString();
        url = url.substring(0, url.indexOf("/user") + 1) + "/images/userface";

        //处理图片
        byte[] bytes = uploadCutImg(file, ad, suffix, fileName, MAX_TRUMB_SIZE, MAX_TRUMB_SIZE);
        if (bytes == null) {
            return new ResponseVo(ResponseCodeEnum.INTERIORERROR, "服务器处理图片异常，请换一张图片试试");
        }
        try {
            FileUtils.writeFileContent(realPath, bytes);
        } catch (IOException e) {
            return new ResponseVo(ResponseCodeEnum.INTERIORERROR, "服务器处理异常，请换一张图片试试");
        }
        ResponseVo vo = new ResponseVo();
        vo.setData("/images/userface" + fileName);
        vo.setEntity(url + fileName);
        vo.setCode(ResponseCodeEnum.SUCCESS);
        return vo;
    }


}
