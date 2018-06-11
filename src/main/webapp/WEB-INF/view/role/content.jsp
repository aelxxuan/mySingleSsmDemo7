<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/8
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-responsive" id="result">
    <table cellpadding="0" cellspacing="0" class="table table-head">
        <thead>
        <tr>
            <th><input type="checkbox" name="ckAll" onclick="selectAll()"/></th>
            <th>角色ID</th>
            <th>角色名称</th>
            <th>应用ID</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="role">
            <tr>
                <td><input type="checkbox" name="ckObj" onclick="selectOne()" value="${role.id}"/></td>
                <td>${role.id}</td>
                <td>${role.name}</td>
                <td>${role.applicationName}</td>
                <td>
                    <button class="btn btn-warning btn-xs" onclick="editRole('${role.id}','${role.name}','${role.applicationId}');"><i
                            class="fa fa-edit"></i>修改
                    </button>
                    <button class="btn btn-danger btn-xs" onclick="deleteRole('${role.id}','${role.name}');"><i
                            class="fa fa-trash"></i>删除
                    </button>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../include/page.jsp"/>
