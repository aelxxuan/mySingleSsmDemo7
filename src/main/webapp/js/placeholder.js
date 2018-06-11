/**
 *  ie9以下浏览器不支持placeholder的处理
 *  原理：
    在原input的相同位置生成1个div，宽高和input一样,在input没有获得焦点且value=''时把div显示在input上面；
    当用户点击div时，将div的z-index降低并隐藏同时使input获得焦点。
    用户离开div时，判断value=''则显示div盖住input否则不显示div.
 *
 *  使用此方法后，因为input的z-index会动态调整
    在ie9以下版本中针对input的onpropertychange事件会被触发(没有修改value也会触发)，导致页面列表数据自动载入，因此修改载入函数加上判断
    同时发现ie9以下版本中,在input中不断增加输入文字会触发onpropertychange事件，而删除文字不会触发，即使删除干净焦点离开也不会触发onpropertychange
 * **/
var fakeIdCounter = 0;

/**
 * 使某个input支持placeHolder的效果
 * @param input
 */
var placeholder = function (input,wait) {
    var phText = input.attr('placeholder');
    if(!phText){
        return;
    }

    var fakeId = input.attr("fake-id");
    if(!fakeId){
        // alert('新建placehoder');
        fakeId = 'fake-placeholder-' + new Date().getTime()+"-"+(++fakeIdCounter);
        var fakeDiv = $("<div id='"+fakeId+"' class='phcolor' >"+phText+"</div>");
        fakeDiv.css({"display":"none","overflow":"hidden","text-overflow":"ellipsis","word-break":"keep-all","white-space":"nowrap",
            "font-size":"14px","position":"absolute","padding-left":"14px"});
        fakeDiv.attr("input-name",input.attr("name"));
        input.attr("fake-id",fakeId);
        input.parent().append(fakeDiv);
        fakeDiv.unbind('click',fakeDivClick).click(fakeDivClick);
    }
    // $("#"+fakeId).hide();//默认隐藏

    input.unbind('focus',inputFocusPlaceHolder).focus(inputFocusPlaceHolder);
    input.unbind('blur',inputBlurPlaceHolder).blur(inputBlurPlaceHolder);
    // input.unbind('input',inputValueChangePlaceHolder).bind('input',inputValueChangePlaceHolder);//其他js修改了value也要同步更改placeholder的显示
    input.unbind('propertychange',inputValueChangePlaceHolder).bind('propertychange',inputValueChangePlaceHolder);//其他js修改了value也要同步更改placeholder的显示
    // input[0].onpropertychange=inputValueChangePlaceHolder;//这样写才有效

    //console.debug('延迟执行placeholder:'+wait);
    if(!wait){
        placeHolderShowHide(input);
    }

    //延迟否则input width获取不准确
    setTimeout(function(){
        resetSize(input,fakeId,wait);
    },500);
};

var resetSize = function (input,fakeId,checkPlaceHolder) {
    var w = input.outerWidth()-14;//padding-left=14
    var h = input.outerHeight();
    // alert("input宽度:"+w+",w2="+input.width());
    // console.debug("input宽度:"+w+",w2="+input.width());
    var fakeDiv = $("#"+fakeId);
    fakeDiv.css({"width":w+"px","height":h+"px","line-height":h+"px"});//
    var style = input.attr("data-placeholder-style");
    if(style){
        var styleArr = style.split(";");
        $.each(styleArr,function (idx,sty) {
            var styleArr2 = sty.split(":");
            // console.debug(idx+ ':special style:'+sty);
            fakeDiv.css(styleArr2[0],styleArr2[1]);
        });
    }
    checkPlaceHolder = checkPlaceHolder||false;
    if(checkPlaceHolder)
        placeHolderShowHide(input);
}

//根据Input的value和是否有焦点来设置placeholder显示或隐藏
var placeHolderShowHide = function (input) {
    if (input.val() == "" && !input.is(":focus")){
        //input没有值也没有获得焦点则把placeHolder放到前面
        //alert('placeHolderShowHide 显示placeholder.'+input.attr('fake-id'));
        showPlaceHolder(input,true);
    }else{
        showPlaceHolder(input,false);
    }
}

//显示或隐藏placeholder
var showPlaceHolder = function (input,show) {
    var fakeId = input.attr('fake-id');
    if(show){//显示
        input.css("z-index",0);
        $("#"+fakeId).css("z-index",100).show();
    }else{//隐藏
        $("#"+fakeId).css("z-index",0).hide();
        input.css("z-index",100);
    }
}

//placeholder上执行了点击的处理
var fakeDivClick = function () {
    var fakeDiv = $(this);
    var fakeId = fakeDiv.attr("id");
    var input = $("input[fake-id='"+fakeId+"']");
    showPlaceHolder(input,false);
    input.focus();
};

//input上获得焦点
var inputFocusPlaceHolder = function () {
    var input = $(this);
    // alert('input得到焦点:'+fakeId);
    showPlaceHolder(input,false);
};

//input失去焦点
var inputBlurPlaceHolder = function () {
    var input = $(this);
    input.val(input.val());//失去焦点时再设置1次value,以触发onpropertychange事件.它不会自动触发
    placeHolderShowHide(input);
}

//input的值发生了变化的处理.1是用户输入导致的变化,2是其他js设置值导致了变化
var inputValueChangePlaceHolder = function () {
    var input = $(this);
    //其他js将input的value进行了修改
    inputValueChange(function(){
        placeHolderShowHide(input);
    });
}