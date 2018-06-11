<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/27
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge   chrome=1">
    <title>学科网账号登录</title>
    <link href="/css/login.css?v=1" type="text/css" rel="stylesheet"/>
    <link href="/css/body.css?v=1" type="text/html" rel="stylesheet"/>
    <link href="/css/sso_login.css?v=1" type="text/css" rel="stylesheet"/>
    <style type="text/css">
        .wrap-login-top, .publicNumber {
            display: none;
        }

        .cten {
            position: relative;
        }

        .lcon-right-top {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .lcon-right-top div {
            width: 166px;
            height: 35px;
            float: left;
            background: url(/images/lcon_ts.png) no-repeat center right;
            padding-right: 23px;
            margin-right: 20px;

        }

        .lcon-right-top div span {
            border: 1px solid #feba01;
            border-right: 0px;
            background: #fffded;
            padding: 5px;
            display: inline-block;
            width: 156px;
            text-align: left;
            color: #FEBA01;
        }

        .lcon-weixin {
            width: 68px;
            height: 68px;
            display: inline-block;
            background: url(/images/lcon_aq.png) no-repeat;
        }

        .lcon-weixin-b {
            background-position-y: bottom;
        }

        .lock {
            width: 21px;
            height: 21px;
            display: inline-block;
            float: left;
            background: url(/images/lcon_ts_tion.png) no-repeat;
            background-position-y: -40px;
            margin-top: 3px;
            margin-right: 5px;
        }

        #weixin iframe {
            width: 100%;
        }

        .evr {
            width: 50% !important;
        }

        .selectedmail {
            background: #eee !important;
        }

        .Fr {
            float: right!important;
        }
        .t_c {
            text-align: center;
        }
    </style>

</head>
<body>
<div class="top">
    <div class="cten" style="margin-top: 181.5px;">
        <a href="http://www.zxxk.com" target="_self"><img src="images/logo125.png"></a>
        <div class="lcon-right-top">
            <div><span><b class="lock" style="background-position-y: 0px;"></b>扫码登录在这里</span></div>
            <a href="javascript:void(0);" class="lcon-weixin lcon-weixin-b "></a>
        </div>
    </div>
</div>
<div class="center">
    <div class="cten">
        <div class="wrap">
            <div class="wrap-list">
                <div id="weixin">
                </div>
                <div class="wrap-login-center" style="display: block;">
                    <h3>
                        <label class="Fl" ><span class="login_ordinary" id="login_ordinary">账号密码登录</span></label>
                        <label class="Fr"><span class="login_phone" id="login_phone" style="color: rgb(153, 153, 153);">手机动态码登录</span></label>
                        <span class="evr"></span>
                    </h3>
                    <form:form action="../login" method="post" name="loginForm"
                               id="ordinaryLoginForm" cssClass="F1" htmlEscape="true">
                        <div class="Prompt">

                        </div>
                        <ul class="wrap-login-list" id="ordinaryLoginTab">
                            <li class="li_top">
                                <div class="userName">
                                    <input id="username" name="username" class="text username" tabindex="1" placeholder="手机号/邮箱/用户名" type="text" value="" maxlength="40" autocomplete="off">
                                </div>
                            </li>
                            <li class="li_top">
                                <div class="password">
                                    <input type="password" class="text psd" maxlength="20" tabindex="2" name="password" placeholder="密码">
                                    <span style="right:0px;">A</span>
                                </div>
                            </li>

                                <li class="img_verification li_top" style="display: none;">
                                    <div>
                                        <input class="text psd" maxlength="4"
                                               placeholder="图形验证码" name="code">
                                    </div>
                                    <img class="Fr" src="#" alt=""
                                         onClick="this.src='captcha.png?r='+Math.random();"
                                         title="看不清,点击刷新."></li>

                            <li><input type="submit" class="btn" id="CommonLogin"
                                       value="登录"/></li>
                        </ul>
                    </form:form>
                    <form:form action="" method="post" id="mobileLoginForm"
                               name="loginMsgForm" style="display:none" cssClass="F1"
                               commandName="${commandName}" htmlEscape="true">
                        <div class="Prompt" id="phoneResult">

                        </div>

                        <ul class="wrap-login-list" id="phoneLoginTab">
                            <li class="li_top">
                                <div class="phone-name">

                                </div>
                            </li>
                            <li class="img_verification li_top" style="display: block;"
                                id="mobileLoginCode">
                                <div class="phone-psd phone-verification">
                                    <input type="text" class="text psd" maxlength="4" tabindex="7"
                                           placeholder="图形验证码" name="captcha"/>
                                </div>
                                <img src="captcha.png" alt=""
                                     onClick="this.src='captcha.png?r='+Math.random();"
                                     title="看不清,点击刷新."/>
                            </li>
                            <li class="li_top">
                                <div class="phone-psd">
                                    <input id="mobileCode" class="text psd"
                                           maxlength="6" tabindex="8" placeholder="短信验证码"
                                           name="mobileCode"/>
                                </div>
                                <input type="button" class="verification" value="获取验证码 "/>
                            </li>
                            <li><input class="btn" type="submit" id="MobilePhoneLogin"
                                       value="登录"/></li>
                        </ul>

                    </form:form>
                    <div class="Other">
                        <h4>
                            <span>使用其他方式登录</span>
                            <div>&nbsp;</div>
                        </h4>
                        <div class="oth">
                                    <a href="#" target="_parent" class="QQ"></a>
                                    <a href="#" target="_parent" class="WeChat"></a>
                                    <a href="#" target="_parent" class="Sina"></a>
                        </div>
                    </div>
                    <ul class="wrap-login-list">
                        <li class="automatic t_c" id="links">
							<span id="forgetPwdTags"><a
                                    href="#"
                                    class="color_gray">忘记密码？</a>
							<b>|&nbsp;</b></span>
                            <a href="#"
                               class="color_gray">免费注册</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

