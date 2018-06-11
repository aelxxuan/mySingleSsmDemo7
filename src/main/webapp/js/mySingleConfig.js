/****
 * function:便于管理，js常用参数设置，服务于ssm
 * 在用户登录成功进入主页后载入，执行页面样式和事件的初始化。
 * Author：wendy
 * Date：2016-11-28
 * eg :
 * dp：jquery
 */
var pageSize=10;

//执行函数回调
var doCallback = function (callback) {
    eval(callback).apply(this);
}


//实现菜单的折叠和单击事件 start
//启用菜单的点击事件
var enableMenuClick = function () {

    $(".sidebar-nav").find("ul>li>a").each(function(){
        var url = $(this).attr("url");
        if (typeof(url) == "undefined"||url==null||url=="") {
            return;
        }
        $(this).on("click",function(){
            loadPageOfMenu(url,false);
        });
        $(this).on("mouseover",function(){
            this.style.cursor="pointer";
        });
    });

    $("#side-menu11>li a").click(function () {
        var menuLink = $(this);
        activeNavMenu(menuLink);
    });
}

/**
 * 载入菜单对应的页面.右侧页面整体替换
 * @param url
 * @param activeMenu 是否同时激活左侧按钮的active状态和二级子菜单的展开状态
 */
var loadPageOfMenu = function (url,activeMenu) {
    $('#showAlertPopup').hide();
    $('#modalAlert').hide();//可能存在showAlert提示框未关闭的情况因为不是模态的

    if(activeMenu){
        var menuLink = $("#side-menu11 a[url='"+url+"']");
        activeNavMenu(menuLink);
    }
    //console.debug('载入页面:'+url);
    $("#page-wrapper").load(url,function(){
        var newHash = url.replace(/\.\.\//g,"");
        if(newHash.indexOf("?")>=0){
            newHash = newHash.substring(0,newHash.indexOf("?"));
        }
        //console.debug('载入页面后设置hash:'+newHash+',url='+url);
        lastManualSetHash = newHash;
        window.location.hash = newHash;
        placeHolderAdaptee(true);//延迟生成placeholder
    });
}

//在左侧菜单的自动收缩并兼容手机浏览模式
var activeNavMenu = function(menuLink){
    var menuIsActive = menuLink.hasClass("active") || menuLink.parent("li").hasClass("active");
    if(menuIsActive){
        //当前按钮上再次点击则对子菜单进行展开收缩的切换
        menuLink.siblings("ul:not('#side-menu11')").toggle();
    }else{
        //将其他展开的子菜单都收起来并将当前点击的子菜单展开
        $("#side-menu11 ul").removeClass("active").filter(":visible").hide();//展开的都合拢
        $("#side-menu11 li").removeClass("active");
        $("#side-menu11 a").removeClass("active");
        menuLink.parents("ul:not('#side-menu11')").filter(":hidden").show();//当前点击的父菜单展开
        menuLink.siblings("ul:not('#side-menu11')").filter(":hidden").show();//当前点击的子菜单展开
    }
    menuLink.addClass("active").parents("li").addClass("active");//激活active

    if(menuLink.is("[url]") && $("body").hasClass("mini-navbar")){//手机浏览模式下叶子菜单的点击
        var miniLeftMenu = true;
        var siblingsUl = menuLink.siblings("ul");
        if(siblingsUl.length > 0 && siblingsUl.is(":hidden")){
            miniLeftMenu = false;
        }
        if(miniLeftMenu){
            $("#side-menu11 ul").hide();
        }
    }
}
//实现菜单的折叠和单击事件 end

//针对ie9以下版本的placeholder的设置
var supportPlaceholder = true;
var placeholderLoaded = false;//js载入成功
var placeholderInvokeLater = false;//在js还没载入时就有调用需求
var checkPlaceHolder = function () {//不支持placeholder则载入模拟器
    supportPlaceholder = 'placeholder' in document.createElement('input');
    if(!supportPlaceholder){//
        $.getScript("/js/placeholder.js",function () {
            placeholderLoaded = true;
            if(placeholderInvokeLater){
                placeHolderAdaptee(true);//第1次载入时延迟生成placeHolder
            }
        });
    }
}


//执行placeHolder模拟器
var placeHolderAdaptee = function (wait) {
    //当浏览器不支持placeholder属性时，调用placeholder函数
    if (!supportPlaceholder) {
        if(placeholderLoaded){
            wait = wait||false;
            $('input').each(function () {
                if ($(this).attr("type") == "text") {
                    placeholder($(this),wait);//see placeholder.js
                }
            });
        }else{
            placeholderInvokeLater = true;
        }
    }
}

/**
 * 显示提示信息或确认信息
 * @param content 提示文字
 * @param confirmBtnLabel 是否显示需要用户确认的按钮，如果需要传入确认按钮的文字，如"确认删除?"
 * @param confirmCallback 显示确认按钮后，当用户点击按钮执行的回调函数
 */
var showAlert = function (content,confirmBtnLabel,confirmCallback,closeCallback,opts) {
    var modalPopup = $("#showAlertPopup");
    var modal = $('#modalAlert');
    var alertTitle = $('#alertTitle');
    var alertContent = $('#alertContent');
    var confirmBtn = $('#btnAlertConfirm');
    var closeIcon = $('#iconAlertClose');

    //去掉以前的事件,避免之前事件被触发
    closeIcon.unbind('click');
    confirmBtn.unbind('click');
    if (typeof(content) == "undefined" || content == null || content == ""){
        content = "未知内容";
    }
    content = content+"";
    alertContent.attr("title",window.utils ? utils.delHtmlTag(content):content);
    if(content.length>100){
        content = content.substring(0,97)+"...";
    }
    alertContent.html(content);

    opts = opts || {};
    opts.title = opts.title||'提示';
    alertTitle.text(opts.title);

    //位置设置屏幕居中向上1/7
    var w = modal.outerWidth();
    var h = modal.outerHeight();
    var winW = $("body").outerWidth();
    var winH = $(window).outerHeight();
    var posX = (winW - w)/2;
    var posY = (winH - h)/2 -(winH/7);// 再调高1/7的高度
    modal.css({ left: posX, top: posY });
    modalPopup.show();//遮罩层
    modal.show();

    if(confirmBtnLabel){
        confirmBtn.text(confirmBtnLabel).show();
        confirmBtn.click(function(e){
            e.stopPropagation();
            e.preventDefault();
            confirmBtn.unbind('click');//关闭点击事件为下次他人使用做准备避免事件重复
            modal.hide('normal',function(){
                modalPopup.hide();
                if(confirmCallback){
                    doCallback(confirmCallback);
                }
            });//先关闭当前弹窗dialog再执行回调
        })
    }else{
        confirmBtn.hide().unbind('click');//不显示确认按钮并且去掉click事件
    }

    if(closeCallback){
        closeIcon.click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            closeIcon.unbind('click');//关闭点击事件为下次他人使用做准备避免事件重复
            modal.hide('normal',function(){
                modalPopup.hide();
                doCallback(closeCallback);
            });//先关闭当前弹窗dialog再执行回调
        });
    }else{
        closeIcon.click(function(e){
            e.stopPropagation();
            e.preventDefault();
            modal.hide();
            modalPopup.hide();
        });
    }
}

var resize = function () {
    var width=$("body").width();
    if(width<768){
        $("body").addClass("mini-navbar");
    }else{
        $("body").removeClass();
    }
}

//设置body的size
var bodyResize = function () {
    $(window).resize(function(){
        resize();
    });
    resize();
}

/**
 * 页面input值变化触发函数执行
 * 为兼容ie9 onpropertychange事件并判断时value变化才触发引入event，但是在firefox里直接使用event会提示undefined
 * @param event
 * @param callback
 */
var inputValueChange=function(callback){
    if(supportPlaceholder){
        doCallback(callback);
        return;
    }

    var event= window.event||arguments.callee.caller.arguments[0] ;//兼容firefox和其他浏览器
    if(event.propertyName){//兼容ie9
        if(event.propertyName.toLowerCase () == "value"){
            //ie的propertyChange事件太宽泛限制为value的变化触发
            // alert('input value 变化:'+callback);
            doCallback(callback);
        }
    }
}

var lastManualSetHash='';//记录页面载入后自动设置的hash值避免popstate触发页面二次刷新
//监听浏览器的后退前进操作进而刷新页面
var enableHistory = function () {
    //chrome、safari可以,ie,firefox不行
    if (window.history && window.history.pushState) {
        $(window).on('popstate', function (e) {
            //console.debug("popstate.href="+window.location.href);
            loadDefaultPage();
        });
    }
}

//模拟点击 刷新时，显示当前页面，而不是首页
//载入登录后首页右侧的默认页面
var loadDefaultPage = function () {
    //如果当前页面不是首页则载入对应页面否则载入首页
    var hash = window.location.hash;
    if(hash.length>0){
        hash = hash.substring(1);//去掉#号
        if(hash != lastManualSetHash){//非人工设置的hash才自动载入
            var url = "../"+hash+"?token="+getToken();
            loadPageOfMenu(url,true);
        }
        return;
    }

    var defaultLoadLink = $("#defaultLoad");
    if(defaultLoadLink.length>0){
        defaultLoadLink.click();//模拟click能触发class变更
    }
}

/**
 * 获得1个input域的encode编码后的值
 * @param inputSelector
 * @returns {string}
 */
var inputEncodeValue = function (inputSelector) {
    var val=$.trim($(inputSelector).val());
    return encodeURIComponent(val);//如果输入了特殊字符需要encode否则get请求url会错误
}

/**
 * 在#content内载入某1页数据
 * @param url 查询url
 * @param paramMap 查询条件参数
 * @param pageNo 页码
 */
var loadDataOfPage = function(url,paramMap,pageNo,callback){
    var fullUrl = url+(url.indexOf("?")==-1?"?":"&");
    $.each(paramMap,function(k,v){
        fullUrl += (k+"="+v+"&");
    });
    fullUrl += "pageIndex="+pageNo+"&pageSize="+pageSize;
    // console.debug('loadDataOfPage:'+fullUrl);
    var msgId = layer.load({shade: [0.1, '#fff']});
    $("#content").load(fullUrl, function () {
        layer.close(msgId);
        if(callback)
            doCallback(callback);
    });
}



//登录成功后页面操作
$(function(){
    bodyResize(); //改变页面大小
    checkPlaceHolder();
    enableMenuClick();
    loadDefaultPage();
    enableHistory();
});