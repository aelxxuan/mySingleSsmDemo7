<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/28
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12">
        <h1 class="page-header">应用列表</h1>
        <div class="form-inline clearfix">
            <div class="form-inline">
                <div class="form-group">
                    <div class="input-group">
                            <span class="input-group-addon" onclick="query()">
                                <i class="fa fa-search"></i>
                            </span>
                        <input type="text" name="keyword" placeholder="应用名" class="form-control">

                    </div>
                </div>
                <div class="form-group pull-right control">
                    <button class="btn btn-primary" onclick="addApplication();"><i class="new"></i>新增</button>
                </div>
            </div>
            <div id="content"/>
        </div>
    </div>
</div>

<!--新建或修改引用-->
<form name="editForm" action="../application/edit" id="editForm" method="post" onsubmit="return submitForm()">
    <div class="import import_pad0" id="myModal">
        <h3 class="back" id="myModalTitle" style="cursor:move;"><span id="application">新建应用</span><a
                href="javascript:void(0)" class="close"></a></h3>
        <div class="user">
            <div><label>应用ID</label>
                <input type="text" class="text" style="color: black" name="id" id="appId" required=""
                       onkeyup="isIllegal('#loginName')" placeholder="请输入应用ID" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>应用名称</label>
                <input type="text" class="text" style="color: black" name="name" id="name" required="true"
                       onkeyup="isIllegal('#name')" placeholder="请输入应用名称" maxlength="64"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>URL</label>
                <input type="text" class="text" style="color: black" name="url" id="url" required="true"
                       placeholder="请输入url" maxlength="100" data-placeholder-style="top:-20px"/>
            </div>
            <div><label>SSOID</label>
                <input type="text" class="text" style="color: black" name="ssoId" id="ssoId" required="true"
                       placeholder="请输入ssoid" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
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
    var addApplication = function () {
        init();
        setPosition($("#myModal"));
        $("#application").text('新增应用');
        $("form[name=editForm]").attr("action", "../application/add");
    }

    <!--修改 -->
    var editApplication = function (id, name, url, ssoid) {
        init();
        $("input[name=id]").val(id).attr("readonly",true).css("background","#ddd").attr("disabled",true);
        $("input[name=name]").val(name);
        $("input[name=url]").val(url);
        $("input[name=ssoId]").val(ssoid);
        setPosition($("#myModal"));
        $("#application").text("修改应用");
    }

    <!-- 删除-->
    var tempid;
    var deleteApplication = function (id, name) {
        showAlert("确定要应用{0}删除吗?".format(name),"确定",function(){
            tempid = id;
            delApplicationPost(tempid,name);
        })
    }

    var delApplicationPost=function(id,name){
        $.post("../application/delete",{"id":id},function (res) {
            if(res.success){
                showAlert("应用："+name+",删除成功")
                loadData($("#currentPage").val())
            }else{
                showAlert(res.message)
            }
        })
    }



    var init =function(){
        $("form[name=editForm]").attr("action", "../application/edit");
        $("input[name=id]").val("");
        $("input[name=id]").attr("disabled",false).attr("readonly",false).css("background","");
        $("input[name=name]").val("");
        $("input[name=url]").val("");
        $("input[name=ssoId]").val("");
        placeHolderAdaptee();//ie9以下浏览器不显示placeholder问题
    }

    var submitForm = function () {
        var applicationId = $("input[name=id]").val();
        var applicationname = $("input[name=name]").val();
        var applicationurl = $("#url").val();
        var ssoId = $("#ssoId").val();
        if(applicationId.trim()=="" || applicationname.trim()=="" || applicationurl.trim() == "" || ssoId.trim()==""){
            showAlert("输入框不能为空");
            return false;
        }

        if(!applicationurl.isUrl()){
            showAlert("url格式不符合");
            return false;
        }

        var url = $("form[name=editForm]").attr("action");
        $.post(url,{"id":applicationId,"name":applicationname,"url":applicationurl,"ssoId":ssoId},function(res){
            if(res.success){
                $("#myModal").hide();
                $(".popup").hide();
                loadData($("#currentPage").val());
            }else{
                showAlert(res.message);
            }
        },"json")
        return false;

    }

    //弹出框样式设置
    var setPosition = function (obj) {
        $(".popup").show();
        obj.css({"left": ($("body").width() - $(obj).width()) / 2});
        obj.css({"top": ($(window).height() - $(obj).height()) / 3});
        obj.show();
    };


    $(function () {
        loadData(1);
        $(".close").click(function(){
            $(".popup").hide();
            $("#myModal").hide();
        })
    })

    var loadData = function (pageIndex) {
        loadDataOfPage("../application/searchApp", {
            "name": inputEncodeValue("input[name=keyword]")
        }, pageIndex)
    }
    var query = function () {
        loadData(1);
    }

    //如果有一下特殊字符，清除
    var isIllegal = function (resId) {
        var mark = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im;
        var vall = $(resId).val();
        if (!mark.test(vall)) {
            return false;
        }
        $(resId).val("");
    }


</script>
