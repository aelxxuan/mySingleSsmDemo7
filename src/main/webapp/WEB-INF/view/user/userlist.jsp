<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<style>
    .user-pic:hover img, .portrait:hover img {
        -webkit-transform: rotateY(180deg);
        -moz-transform: rotateY(180deg);
        transform: rotateY(180deg)
    }
    .user-pic:hover .mask, .portrait:hover .mask {
        opacity: 0.8;
        filter: alpha(opacity=80);
        -webkit-transform: rotateY(0);
        -moz-transform: rotateY(0);
        transform: rotateY(0);
    }
    .user-pic:hover .mask p, .portrait:hover .mask p {
        opacity: 1;
        filter: alpha(opacity=100)
    }
     .user-pic {
        position: relative;
        margin-right: 25px;
        width: 106px !important;
        height: 106px;
        overflow: hidden;
        cursor: pointer;
    }

     .user-pic img, .portrait img {
        width: 100px;
        height: 100px;
        padding: 2px;
        border: 1px solid #e0e0e0;
        border-radius: 50%;
        -webkit-transition: all .5s ease;
        -moz-transition: all .5s ease;
        transition: all .5s ease;
    }

    .mask {
        position: absolute !important;
        border-radius: 50%;
        top: 3px;
        left: 3px;
        right: 3px;
        bottom: 3px;
        background-color: #333;
        background-color: rgba(0, 0, 0, 0.5);
        opacity: 0;
        filter: alpha(opacity=0);
        backface-visibility: hidden;
        -webkit-transform: rotateY(-180deg);
        -moz-transform: rotateY(-180deg);
        transform: rotateY(-180deg);
        -webkit-transition: all .5s ease;
        -moz-transition: all .5s ease;
        transition: all .5s ease;
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
                        <input type="text" name="keyword" placeholder="姓名" class="form-control">

                    </div>
                </div>
                <div class="form-group pull-right control">
                    <button class="btn btn-primary" onclick="addUser();"><i class="new"></i>新增</button>
                    <button class="btn btn-primary" onclick="importUser();"><i class="import_img"></i>导入</button>
                    <button class="btn btn-primary" onclick="exportUser();"><i class="export_img"></i>导出</button>
                </div>
            </div>
            <div id="content"/>
        </div>
    </div>
</div>

<!--新建用户信息-->
<form name="editForm" action="../user/addUser" id="editForm" method="post" onsubmit="return submitForm()">
    <div class="import import_pad0" id="myModal">
        <h3 class="back" id="myModalTitle" style="cursor:move;"><span id="userInfo">新建用户</span><a
                href="javascript:void(0)" class="close"></a></h3>
        <div class="user">
            <input type="hidden" id="userId"/>
            <div><label>用户名</label>
                <input type="text" class="text" style="color: black" name="loginName" id="loginName" required=""
                       onkeyup="isIllegal('#loginName')" placeholder="请输入用户名" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>姓名</label>
                <input type="text" class="text" style="color: black" name="userName" id="userName" required=""
                       onkeyup="isIllegal('#userName')" placeholder="请输入姓名" maxlength="32"
                       data-placeholder-style="top:-20px"/>
            </div>
            <div><label>密码</label>
                <input type="text" class="text" style="color: black" name="password" id="pwd" required=""
                       placeholder="请输入密码" maxlength="32" data-placeholder-style="top:-20px"/>
            </div>
            <div><label>IP地址</label>
                <input type="text" class="text" style="color: black" name="userLastIp" id="userLastIp"
                       placeholder="请输入IP地址" maxlength="32" data-placeholder-style="top:-20px"/>
            </div>
            <div><label>手机</label>
                <input type="text" class="text" placeholder="请输入手机号" name="phone" id="phone" required=""
                       onkeydown="if(event.keyCode==32) return false" maxlength="11"
                       style="color: black" data-placeholder-style="top:-20px"/>
            </div>
            <div><label>用户头像</label>
                <input type="hidden" class="text" placeholder="用户头像地址" name="picture" id="picture"
                       style="color: black" data-placeholder-style="top:-20px"/>

                <div class="user-pic fl">
                    <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00001.jpg" alt="">
                    <div class="mask">
                        <p>修改头像</p>
                    </div>
                </div>

            </div>
        </div>
        <div class="user_btn back">
            <!--<button class="btn btn-primary" type="button" onclick="submitForm()">确认</button> -->
            <button class="btn btn-primary" type="submit">确认</button>
        </div>
    </div>
</form>

<!--批量导入用户信息-->
<div class="import import_pad0" id="import1">
    <form class="pull-right">
        <h3 class="back"><span>批量导入用户信息</span><a href="javascript:;" class="close"></a></h3>
        <div style="padding:10px;">第一步：<a href="../user/download" value="点击下载">点击下载</a>EXCEL模板，按要求填写完整</div>
        <div style="padding:10px;"><span style="float:left">第二步：</span>
            <div style="float:left">
                <input type="file" name="file" id="fileValue" placeholder="点击上传" class="up_list" required=""/><input
                    type="button" class="btn btn-primary" style="line-height: 16px;" value="点击上传"/>
            </div>
            <span>填写完整的EXCEL模板，即完成用户信息的批量导入</span>
        </div>
        <div style="padding:10px;">
            <h4>备注：</h4>
            <div>1.请务必按照EXCEL模板信息要求填写，不能修改、删除模板信息任何格式，按要求填写完整才能顺利提交</div>
            <div>2.EXCEL格式文件不得超过1000条，多于1000条，请分批导入</div>
        </div>
        <div class="user_btn back">
            <button class="btn btn-primary" id="sureImport">确认</button>
        </div>
    </form>
</div>
<!-- 报错错误数据 -->
<input id="failList" type="hidden"/>
<!-- 保存错误的form表单-->
<div id="submitForm" style="display: none"></div>
<script src="/js/jquery.js" type="text/javascript"></script>
<script src="/js/userUpload.js" type="text/javascript"></script>
<script src="/js/popup.js" type="text/javascript"></script>
<script type="text/javascript">
    //设置ajax为同步，如果个别的使用同步只能使用$.ajax方法
    $.ajaxSetup({
        async: false
    });

    $(function () {
        loadData(1);
        $(".close").click(function () {
            $(".popup").hide();
            $("#myModal").hide();
            $("#import1").hide();
        })
    })
    var loadData = function (pageIndex) {
        loadDataOfPage("../user/searchUser", {
            "name": inputEncodeValue("input[name=keyword]")
        }, pageIndex);
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

    //弹出框样式设置
    var setPosition = function (obj) {
        $(".popup").show();
        obj.css({"left": ($("body").width() - $(obj).width()) / 2});
        obj.css({"top": ($(window).height() - $(obj).height()) / 3});
        obj.show();
    };
    //添加
    var addUser = function () {
        init();
        setPosition($("#myModal"));
        $('form[name=editForm]').attr("action", "../user/addUser");
    }
    //修改
    var editUser = function (userId) {
        init();
        $("#userInfo").text("修改用户");
        $.post("../user/getUserById", {"userId": userId}, function (res) {
            if (res != null) {
                var user = res.Entity;
                $("#loginName").val(res.loginName);
                $("#userName").val(res.name);
                $("#pwd").val(res.password);
                $("#userLastIp").val(res.userLastIp);
                $("#phone").val(res.phone);
                $("#picture").val(res.picture);
                $("#userId").val(res.userId);
                if(res.picture !="" && res.picture !=null){
                    $(".user-pic img").attr("src",res.pictureUrl);
                }

            }
            else {
                showAlert("修改失败");
                return;
            }
        })
        var phone = $("#phone").val();
        if (phone) {
            $("#phone").prop("readonly", true).prop("disabled", true).css("background", "#ddd")
        } else {
            $("#phone").prop("readonly", false).prop("disabled", false).css("background","");
        }
        var pwd = $("#pwd").val();
        if(pwd){
            $("#pwd").prop("readonly",true).prop("disabled",true).css("background","#ddd")
        }else{
            $("#pwd").prop("readonly",false).prop("disabled",false).css("background","");
        }
        setPosition($("#myModal"));

    }
    //修改或添加的确认操作
    var submitForm = function () {
        var loginName = $.trim($("#loginName").val());
        var userName = $("#userName").val();
        var pwd = $("#pwd").val();
        var userLastIp = $("#userLastIp").val();
        var phone = $.trim($("#phone").val());
        var picture = $("#picture").val();
        var url = $("#editForm").attr("action");
        var userId = $("#userId").val();
        if (userId == "") userId = 0;

        if (loginName == "") {
            showAlert("请输入有效字符");
            return false;
        }
        var phoneInput = $("input[name=phone]");
        if (phoneInput.attr("disabled") || phoneInput.attr("readonly")) {
            // 手机号不可改
        } else {
            if (!phone.isPhone()) {
                showAlert("手机号格式错误！")
                return false;
            }
        }
        $.post(url, {
            "loginName": loginName, "name": userName, "password": pwd,
            "userLastIp": userLastIp, "phone": phone, "picture": picture, "userId": userId
        }, function (res) {
            if (res.success) {
                loadData($("#currentPage").val());
                $(".popup").hide();
                $("#myModal").hide();
            } else {
                showAlert(res.message);
            }
        }, "json")
        return false;
    }
    //删除
    var deleteUser = function (userId, name) {
        showAlert("确认要删除吗？","确定",function(){
            $.post("../user/deleteUser", {"userId": userId}, function (res) {
                if (res.success) {
                    showAlert("用户：" + name + ",删除成功！");
                    loadData($("#currentPage").val());
                } else {
                    showAlert(res.message);
                }
            }, "json")
        })

    }


    //初始化添加输入框
    var init = function () {
        $("#loginName").val("");
        $("#userName").val("");
        $("#pwd").val("");
        $("#userLastIp").val("");
        $("#phone").val("");
        $("#picture").val("/images/userface/00001.jpg");
        $("#userId").val("");
        $('form[name=editForm]').attr("action", "../user/editUser");
        $(".user-pic img").attr("src","https://zxxkstatic.zxxk.com/uc/images/userface/00001.jpg");
        $("#phone").prop("readonly", false).prop("disabled", false).css("background","");
        $("#pwd").prop("readonly",false).prop("disabled",false).css("background","");
    }
    //导出
    var exportUser = function(){
        window.location.href = "/user/exportuser?name="+inputEncodeValue("input[name=keyword]");
    }
    //导入
    var importUser = function(){
        setPosition($("#import1"));
        //文件上传组件设置
        var params = {};//参数值的获取方式是传递引用而不是传值，因为值会变
        initFileUploader('../user/uploadFile', $('#sureImport'), params);
    }
    $(".user-pic").click(function(){
        uploadPicture();
    })

    //上传图片
    var uploadPicture = function(){
        popup.openPage("/user/modifyUserFace", "修改头像", ["720px", "610px"]);
    }
    //展示：自定义图片页签
    var getAvatarSrc = function () {
        return $(".user-pic img").attr("src");
    }

    //保存图片到输入框和img
    var saveSystemPhoto=function(photoUrl,fullPhotoUrl){
        $(".user-pic img").attr("src",fullPhotoUrl);
        $("#picture").val(photoUrl);
    }



</script>
