<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <style>.error{color:red;}</style>
</head>
<body>

<div class="error">${error}</div>
<form action="../login" method="post">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>

<br/>
<a href="../login">登录</a><br/>
<a href="../authenticated">已身份认证</a><br/>
<a href="../role">角色授权</a><br/>
<a href="../permission">权限授权</a><br/>

</body>
</html>