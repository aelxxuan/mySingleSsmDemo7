<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
</head>
<body>
欢迎${subject.principal}登录成功！<a href="../logout">退出</a>

<br/>
<a href="../login">登录</a><br/>
<a href="../authenticated">已身份认证</a><br/>
<a href="../role">角色授权</a><br/>
<a href="../permission">权限授权</a><br/>
</body>
</html>