<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/28
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="table-responsive" id="result">
    <table cellpadding="0" cellspacing="0" class="table table-head">
        <thead>
        <tr>
            <th><input type="checkbox" name="ckAll" onclick="selectAll()"/></th>
            <th>应用ID</th>
            <th>应用名称</th>
            <th>应用地址</th>
            <th>SSOID</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applications}" var="app">
            <tr>
                <td><input type="checkbox" name="ckObj" onclick="selectOne()"/></td>
                <td>${app.id}</td>
                <td>${app.name}</td>
                <td>${app.url}</td>
                <td>${app.ssoId}</td>
                <td>
                    <button class="btn btn-warning btn-xs" onclick="editApplication('${app.id}','${app.name}','${app.url}','${app.ssoId}');"><i
                            class="fa fa-edit"></i>修改
                    </button>
                    <button class="btn btn-danger btn-xs" onclick="deleteApplication('${app.id}','${app.name}');"><i
                            class="fa fa-trash"></i>删除
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../include/page.jsp"/>
