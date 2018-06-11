<%--
  Created by IntelliJ IDEA.
  User: wendy
  Date: 2017/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge   chrome=1">
    <title>错误信息</title>
    <link rel="shortcut icon" type="image/x-icon" href="/images/logo.ico">
    <link rel="stylesheet" href="/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link href="/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .errorInfo{
            width:450px;
            margin:auto;
            margin-top: 100px;
            text-align: center;
            color:#555;
            padding:20px 50px;
            line-height: 30px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
         <a  href="/" style="margin-right:30px;" class="navbar-brand"><i class="fa fa-university" style="color:#EB4F38;"></i>
        <strong><span class="hidden-xs">学科网学校自助服务系统</span></strong> </a>
    </div>
</nav>
<div class="errorInfo">
    <p style="font-size:16px;font-weight: bold;" >系统出点故障，请注销后重试!</p>
    <p>
        <%=
            "错误信息: " + message
        %>
    </p>
</div>
</body>
</html>
