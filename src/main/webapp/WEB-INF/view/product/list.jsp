<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/11
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    select {
        width: 300px;
        height: 32px;
        border: 1px solid #ccc;
        border-radius: 5px;
        line-height: 25px;
        border-top-left-radius: 0px;
        border-bottom-left-radius: 0px;
    }
</style>
<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12">
        <h1 class="page-header">用户列表</h1>
        <div class="form-inline clearfix">
            <div class="form-inline">
                <div class="form-group">
                    <div class="input-group">
                            <span class="input-group-addon" onclick="query()">
                                <i class="fa fa-search"></i>
                            </span>
                        <input type="text" name="keyword" placeholder="产品名称" class="form-control">

                    </div>
                </div>
                <div class="form-group pull-right control">
                    <button class="btn btn-primary" onclick="addProduct();"><i class="new"></i>新增</button>
                    <button class="btn btn-danger" style="background: #d9534f;" onclick="batchDeleteProduct();"><i
                            class="fa fa-trash"></i>批量删除
                    </button>

                </div>
            </div>
            <div id="content"/>
        </div>
    </div>
</div>
<!--新建产品信息-->
<form name="editForm" action="../product/add" id="editForm" method="post" onsubmit="return submitForm()">
    <div class="import import_pad0" id="myModal">
        <h3 class="back" id="myModalTitle" style="cursor:move;"><span id="userInfo">新建产品</span><a
                href="javascript:void(0)" class="close"></a></h3>
        <div class="user">
            <input type="hidden" id="userId"/>
            <div><label>产品 ID&nbsp;&nbsp;</label>
                <input type="text" class="text" style="color: black" name="id" id="productId" required=""
                       placeholder="请输入产品编号" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>产品名称</label>
                <input type="text" class="text" style="color: black" name="name" id="productName" required=""
                       placeholder="请输入产品名称" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>

            <div><label>应用名称</label>
                <select name="applicationId">
                    <c:forEach items="${apps}" var="app">
                        <option value="${app.id}">${app.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div><label>产 品 组&nbsp;</label>
                <select name="groupId">

                        <option value="1001">1001</option>

                </select>
            </div>
            <div><label>角色列表</label>
                <select name="roleId">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.id}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="user_btn back">
            <!--<button class="btn btn-primary" type="button" onclick="submitForm()">确认</button> -->
            <button class="btn btn-primary" type="submit">确认</button>
        </div>
    </div>
</form>



<script src="/js/jquery.js"/>
<script type="text/javascript" >
    $(function(){
        loadData(1);
    })

    $(".close").click(function () {
        $(".popup").hide();
        $("#myModal").hide();
    })

    //弹出框样式设置
    var setPosition = function (obj) {
        $(".popup").show();
        obj.css({"left": ($("body").width() - $(obj).width()) / 2});
        obj.css({"top": ($(window).height() - $(obj).height()) / 3});
        obj.show();
    };

    var loadData =function(pageNo){
        loadDataOfPage("../product/search",{
            "name":inputEncodeValue("input[name=keyword]")
        }, pageNo)
    }
    var query = function(){
        loadData(1);
    }

    var deleteProduct = function (productId) {
        showAlert("您确定要删除吗？","确定",function(){
            $.post("../product/delete",{"productId":productId},function(res){
                if(res.success){
                    loadData($("#currentPage").val())
                }else{
                    showAlert(res.message)
                }
            },"json")
        })
    }

    var addProduct = function(){
        init();
        setPosition($("#myModal"));

    }

    var editProduct = function(productId,productName,applicationId,groupId,roleId){
         $("#productId").val(productId);
        $("#productId").prop("disabled",true).prop("readonly",true).css("background","#ddd");
         $("#productName").val(productName);
         $("select[name=applicationId]").val(applicationId);
         $("select[name=groupId]").val(groupId);
         $("select[name=roleId]").val(roleId);
         $("#myModalTitle span").text("修改产品")
         $("form[name=editForm]").attr("action","../product/edit")
        setPosition($("#myModal"));
    }



    var submitForm = function () {
        var productId = $("#productId").val();
        var productName = $("#productName").val();
        var appId = $("select[name=applicationId]").val();
        var groupId = $("select[name=groupId]").val();
        var roleId = $("select[name=roleId]").val();

        var url = $("form[name=editForm]").attr("action")
        $.post(url,{
            "id":productId,
            "name":productName,
            "applicationId":appId,
            "groupId":groupId,
            "roleId":roleId
        },function(res){
            if(res.success){
                loadData($("#currentPage").val())
                $(".popup").hide();
                $("#myModal").hide();
            }else{
                showAlert(res.message)
            }

        },"json")
        return false;
    }

    var init = function(){
        $("#productId").val("");
        $("#productId").prop("disabled",false).prop("readonly",false).css("background","");
        $("#productName").val("");
        $("select[name=applicationId]").get(0).selectedIndex =0;
        $("select[name=groupId]").get(0).selectedIndex=0;
        $("select[name=roleId]").get(0).selectedIndex=0;
    }



</script>
