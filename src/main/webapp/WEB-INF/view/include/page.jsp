<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/19
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="clearfix">
    <c:if test="${pager!=null}">
        <div class="text-muted mart10 pull-left" id="total">
        <c:choose>
            <c:when test="${pagerTotalText!=null}">${pagerTotalText}</c:when>
            <c:otherwise>共 ${pager.totalRow} 条记录</c:otherwise>
        </c:choose>
        </div>

        <input type="hidden" name="currentPage" id="currentPage" value="${pager.page}"/>
        <c:if test="${pager.totalPage>0}">
            <nav class="text-right" id="pagebody">
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${pager.page>1}">
                            <li><a href="javascript:void(0)" onclick="loadData(1)">&laquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:void(0)" style="color: #555">&laquo;</a></li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${pager.startDisplay>1}">
                        <li><a href="javascript:void(0)" onclick="loadData(${pager.leftToPage})">...</a></li>
                    </c:if>



                    <c:forEach begin="${pager.startDisplay}" end="${pager.endDisplay}" var="pageNo" step="1">
                        <c:choose>
                            <c:when test="${pager.page==pageNo}">
                                <li class="active"><a href="javascript:void(0)"> ${pageNo}<span class="sr-only">${pageNo}</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0)" onclick="loadData(${pageNo})">${pageNo}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${pager.endDisplay<pager.totalPage}">
                        <li><a href="javascript:void(0)" onclick="loadData(${pager.rightToPage})">...</a></li>
                    </c:if>

                    <c:choose>
                        <c:when test="${pager.page<pager.totalPage}">
                            <li><a href="javascript:void(0)" onclick="loadData(${pager.totalPage})">&raquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:void(0)" style="color: #555">&raquo;</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </c:if>
    </c:if>
</div>

