/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * shiro 安全控制
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/5
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
    @RequestMapping(method = RequestMethod.GET)
    public String loginView(){
        return "/jsp/login";
    }
    @RequestMapping("/login")
    public String  login(Model model, HttpServletRequest request, HttpServletResponse response){
        String error = null;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }

        if(error != null) {//出错了，返回登录页面
            model.addAttribute("error",error);
            return "/jsp/login";
        } else {//登录成功
            model.addAttribute("subject",subject);
            return "/jsp/loginSuccess";
        }
    }
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "/jsp/logoutSuccess";
    }
    @RequestMapping("/permission")
    public String Permission(Model model) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("user:create");
        model.addAttribute("subject",subject);
        return "/jsp/hasPermission";
    }

    @RequestMapping("/role")
    public String role(Model model) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");
        model.addAttribute("subject",subject);
        return "/jsp/hasRole";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() throws IOException {
        return "/jsp/unauthorized";
    }

    @RequestMapping("/authenticated")
    public String authenticated(Model model) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            model.addAttribute("subject",subject);
            return "/jsp/authenticated";
        }else{
            return "/jsp/login";
        }
    }



}
