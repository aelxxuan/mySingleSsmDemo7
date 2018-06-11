<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/19
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="table-responsive" id="result">
    <table cellpadding="0" cellspacing="0" class="table table-head">
        <thead>
        <tr>
            <th><input type="checkbox" name="ckAll" onclick="selectAll()"/></th>
            <th>用户ID</th>
            <th>登录名</th>
            <th>用户姓名</th>
            <th>手机号</th>
            <th>图片地址</th>
            <th>用户IP</th>
            <th>添加时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td><input type="checkbox" name="ckObj" onclick="selectOne()"/></td>
                <td>${user.userId}</td>
                <td>${user.loginName}</td>
                <td>${user.name}</td>
                <td>${user.phone}</td>
                <td>${user.picture}</td>
                <td>${user.userLastIp}</td>
                <td><fmt:formatDate value="${user.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                <td>
                    <button class="btn btn-warning btn-xs" onclick="editUser('${user.userId}');"><i
                            class="fa fa-edit"></i>修改
                    </button>
                    <button class="btn btn-danger btn-xs" onclick="deleteUser('${user.userId}','${user.name}');"><i
                            class="fa fa-trash"></i>删除
                    </button>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../include/page.jsp"/>


