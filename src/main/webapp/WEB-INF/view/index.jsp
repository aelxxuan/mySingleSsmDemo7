<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<jsp:include page="include/header.jsp"/>

<jsp:include page="include/menu.jsp"/>
<!-- 右侧内容区 -->
<div id="pageMain">
    <div id="page-wrapper">

    </div>
</div>
<!--弹出层背景层-->
<div class="popup"></div>


<jsp:include page="include/footer.jsp"/>
<script type="text/javascript">
    //getToken在常规用户界面返回空即可在客服销售页面需返回实际token
    //获得当前页面的token
    var getToken = function () {
        var hideToken = $("#hiddenToken");
        if (hideToken.length > 0)
            return hideToken.val();
        return "";
    }
</script>

<!-- 提示区，各种提示显示使用，在ssmConfig.js里调用 -->
<div id="showAlertPopup" style="width: 100%;
height: 100%;
position: fixed;
z-index: 1999;
background: #000000;
opacity: 0.5;
left: 0px;
top: 0px;
display: none;"></div>
<div id="modalAlert" style="z-index: 2000;" class="import import_pad0">
    <h3 class="back"><span id="alertTitle">提示</span><a href="javascript:void(0);" id="iconAlertClose" class="close"></a></h3>
    <div class="text-center">
        <p id="alertContent"></p>
    </div>
    <div class="user_btn back">
        <button class="btn btn-primary" id="btnAlertConfirm">确认</button>
    </div>
</div>
