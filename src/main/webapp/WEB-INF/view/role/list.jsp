<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/8
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>

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
                        <input type="text" name="keyword" placeholder="角色名称" class="form-control">

                    </div>
                </div>
                <div class="form-group pull-right control">
                    <button class="btn btn-primary" onclick="addRole();"><i class="new"></i>新增</button>
                    <button class="btn btn-danger" style="background: #d9534f;" onclick="batchDeleteRole();"><i
                            class="fa fa-trash"></i>批量删除
                    </button>

                </div>
            </div>
            <div id="content"/>
        </div>
    </div>
</div>

<!--新建用户信息-->
<form name="editForm" action="../role/addrole" id="editForm" method="post" onsubmit="return submitForm()">
    <div class="import import_pad0" id="myModal">
        <h3 class="back" id="myModalTitle" style="cursor:move;"><span id="userInfo">新建角色</span><a
                href="javascript:void(0)" class="close"></a></h3>
        <div class="user">
            <input type="hidden" id="userId"/>
            <div><label>角色 ID&nbsp;&nbsp;</label>
                <input type="text" class="text" style="color: black" name="id" id="roleId" required=""
                       placeholder="请输入角色编号" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>角色名称</label>
                <input type="text" class="text" style="color: black" name="name" id="roleName" required=""
                       placeholder="请输入角色名称" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>

            <div><label>应用名称</label>
                <select name="applicationId">
                    <c:forEach items="${apps}" var="app">
                        <option value="${app.id}">${app.name}</option>
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


<script src="/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        loadData(1);

    })

    $(".close").click(function () {
        $(".popup").hide();
        $("#myModal").hide();
    })


    var loadData = function (pageNo) {
        loadDataOfPage("../role/search", {
            "name": inputEncodeValue("input[name=keyword]")
        }, pageNo);
    }
    //弹出框样式设置
    var setPosition = function (obj) {
        $(".popup").show();
        obj.css({"left": ($("body").width() - $(obj).width()) / 2});
        obj.css({"top": ($(window).height() - $(obj).height()) / 3});
        obj.show();
    };

    var query = function () {
        loadData(1)
    }

    var addRole = function () {
        init();
        setPosition($("#myModal"))
    }

    var editRole =function(id,name,appId){
        init();
        $("form").attr("action","../role/updaterole");
        $("h3 span").text("修改角色")
        $("#roleId").val(id);
        $("#roleId").prop("disabled",true).prop("readonly",true).css("background","#ddd");
        $("#roleName").val(name);
        $("select").val(appId);
        setPosition($("#myModal"))
        placeHolderAdaptee();//ie9以下浏览器不显示placeholder问题
    }


    var submitForm = function () {
        var roleId = $("#roleId").val();
        var roleName = $("#roleName").val();
        var appId = $("select option:selected").val();
        if (roleId == null || roleName == null || roleId == "" || roleName == "") {
            showAlert("输入项不能为空")
            return false;
        }
        var url = $("form").attr("action")
        console.log(url)
        $.post(url, {"id": roleId, "name": roleName, "applicationId": appId}, function (res) {
            console.log(res)
            if (res.success) {
                loadData($("#currentPage").val())
                closeModal();
            } else {
                showAlert(res.message);
            }
        }, "json")
        return false;
    }

    var closeModal =function(){
        $(".popup").hide();
        $("#myModal").hide();
    }

    var init=function(){
        $("#roleId").val("");
        $("#roleId").prop("disabled",false).prop("readonly",false).css("background","");
        $("#roleName").val("");
        $("select[name=applicationId]").get(0).selectedIndex = 0;
        placeHolderAdaptee();//ie9以下浏览器不显示placeholder问题
    }
    //单个删除
    var deleteRole=function(id,name){
        deletePost(id);
    }



    var batchDeleteRole=function(){
        var ck =$("input[name=ckObj]");
        var ids ="";
        for(var i=0;i<ck.length;i++){
            if(ck[i].checked){
                ids +=ck[i].value+",";
            }
        }
        if(ids==""){
            showAlert("请选择要删除的项");
            return false;
        }
        deletePost(ids);
    }

    var deletePost=function(ids){
        showAlert("确定要删除吗？","确定",function(){
            $.post("../role/deleterole",{"ids":ids},function(res){
                if(res.success){
                    loadData($("#currentPage").val())
                }else{
                    showAlert(res.message)
                }
            },"json")
        })
    }


</script>




















