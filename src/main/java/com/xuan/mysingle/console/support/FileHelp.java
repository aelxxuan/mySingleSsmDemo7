/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.alibaba.fastjson.JSON;
import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.console.user.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
@Component
public class FileHelp {
    @Autowired
    UserComponent userComponent;


    public Model uploadFile(Model model, HttpServletRequest request) throws IOException {
        ResponseVo vo = new ResponseVo();
        try {
            FileInformation info;
            info = userComponent.uploadFile(request, null, null, null);
            vo.setSuccess(true);
            vo.setMessage(info.getOutput());
            vo.setList(info.getErrorUserList());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMessage(e.getMessage());
        }

        String msg = vo.getMessage();
        String ua = request.getHeader("user-agent");
        String message = CommonUtil.cutMessageForSafari(msg, 497, ua);
        vo.setMessage(message);

        model.addAttribute("message", vo.getMessage());
        model.addAttribute("success", vo.isSuccess());
        model.addAttribute("errorList", JSON.toJSONString(vo.getList()));
        model.addAttribute("errorListCount", CollectionUtils.isEmpty(vo.getList()) ? 0 : vo.getList().size());
        return model;
    }


    /**
     * 生成错误的Excel数据
     *
     * @Author xuanzongjun
     */
    public byte[] downErrorExcel(List<User> userList) throws IOException {
        ExcelGenerator.ExcelGeneratorExtendInfo ext = new ExcelGenerator.ExcelGeneratorExtendInfo();
        ext.setColWidth(new int[]{15, 20, 20, 40});
        byte[] bytes = ExcelGenerator.generate(new String[]{"姓名", "用户名", "手机号", "备注"}, userList, (su, colIdx) -> {
            switch (colIdx) {
                case 0:
                    return su.getName() == null ? "" : su.getName();
                case 1:
                    return su.getLoginName() == null ? "" : su.getLoginName();
                case 2:
                    return su.getPhone() == null ? "" : su.getPhone();
                case 3:
                    return su.getDescription() == null ? "" : su.getDescription();
                default:
                    return "";
            }
        }, ext);
        return bytes;

    }

}
