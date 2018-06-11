/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/24
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(method= RequestMethod.GET)
    public String loginVew(){
        return "/login/load";
    }

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String loadPage(){
        return "index";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(Model model, @RequestParam String username, @RequestParam String password){
        User user= userService.getUserByLoginName(username);
        if(user!=null && CommonUtil.encodeMD5(password).equals(user.getPassword())){
            return "redirect:/index";
        }else{
            model.addAttribute("message","用户名或密码错误！");
            return "";
        }

    }

}
