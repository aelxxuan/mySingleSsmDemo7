<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/4
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>系统头像</title>
    <link rel="stylesheet" href="/css/account.css">
    <link rel="stylesheet" href="/css/cropper/cropper.css">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/lib.js"></script>
    <script type="text/javascript" src="/js/jquery.form.js"></script>
</head>
<body>
<div class="portrait-popup" id="avatar-modal">
    <div class="portrait-bd">
        <ul class="portrait-nav">
            <li class="current">系统头像</li>
            <li style="display: none;">自定义头像</li>
            <li class="underline"></li>
        </ul>
        <div class="portrait-content">
            <div class="inner">
                <ul class="system-head">
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00001.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00002.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00003.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00004.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00005.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00006.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00007.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00008.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00009.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00010.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00011.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00012.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00013.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00014.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00015.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00016.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00017.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00018.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00019.jpg" alt="">
                        <i></i>
                    </li>
                    <li>
                        <img src="https://zxxkstatic.zxxk.com/uc/images/userface/00020.jpg" alt="">
                        <i></i>
                    </li>
                </ul>
                <div class="buttons">
                    <a href="javascript:void(0);" id="btnSure">确认修改</a>
                    <a href="javascript:void(0);" class="cancel">取消</a>
                </div>
            </div>
            <div class="inner" style="display:none;">
                <form class="avatar-form"  action="/user/uploadCustomUserFace" enctype="multipart/form-data" method="post">
                    <div class="avatar-body clearfix">

                        <div class="avatar-upload">
                            <button type="button" class="avatar-btn">点击上传</button>
                            <span style="margin-left:20px">支持jpg、jpeg、gif、png格式的图片</span>
                            <input type="hidden" class="avatar-src" name="avatar_src">
                            <input type="hidden" class="avatar-data" name="avatar_data">
                            <input type="file" class="avatar-input" id="avatarInput" name="avatar_file">
                        </div>

                        <div class="avatar-wrapper fl">
                            <div class="avatar-img">

                            </div>
                            <p>选择合适的区域<span><i class="ico-move"></i>移动</span><span><i class="ico-zoom"></i>缩放</span></p>
                        </div>

                        <div class="avatar-preview fr">
                            <div class="preview-container">

                            </div>
                            <p class="text-center">头像预览</p>
                        </div>

                    </div>
                    <div class="buttons">
                        <a href="javascript:void(0);" id="avatar-save">确认修改</a>
                        <a href="javascript:void(0);" class="cancel">取消</a>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
<script src="/js/cropper/cropper.js" type="text/javascript"></script>
<script src="/js/cropper/main.js?v=5" type="text/javascript"></script>
<script>
    $(function () {
        $(".portrait-nav li").click(function () {
            var pos = $(this).position().left;
            $(".portrait-nav").find(".underline").animate({
                left: pos
            }, 400);
            $(this).addClass("current").siblings().removeClass("current");
            $(".portrait-content").find(".inner:eq(" + $(this).index() + ")").show().siblings().hide();
        });
        //系统图片选中
        $(".system-head li").click(function () {
            $(this).addClass("current").siblings().removeClass("current");
        });
        //单击上传按钮，同时单击File，选择图片
        $(".avatar-btn").click(function () {
            $("#avatarInput").trigger('click');
        })
    });
</script>
</body>
</html>
