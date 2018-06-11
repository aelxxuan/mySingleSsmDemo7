/**
 * 弹窗js
 */
function Popup(){
    layer.config({
        //anim: 1, //默认动画风格
        skin: 'layui-layer-lan'//,'layui-layer-molv' //默认皮肤
    });
}

Popup.prototype = {
 //alert
 alert : function(txt){
     return layer.alert(txt,{title:'提示'});
 },
 //alert
 alert : function(txtOrHtml,title,btnFunc,cancelFunc){
     return layer.alert(txtOrHtml,{
        title:title||'提示',
        cancel:function(index){
            popup.callbackFunc(cancelFunc);
            return true;
        }
     },function(index){
         layer.close(index);
         popup.callbackFunc(btnFunc);
     });
 },
 //confirm
 confirm : function (confirmTxtOrHtml,btnTxtArray,btnFuncArray,cancelFunc) {
     var options = popup.createOptions(btnTxtArray,btnFuncArray,cancelFunc);
     options.title = '请确认';
     return layer.confirm(confirmTxtOrHtml,options);
 },
 //自动关闭的提示框
 msg : function(msg,time) {
     time = time || 3000;
     return layer.msg(msg,{time:time});
 },
 //在元素旁边显示1个tips文本
 tips: function(msg,jquerySelector,pos){
     pos = pos || 2;//1:top,2:right,3:bottom,4:left,default right
     if(pos < 1) pos = 1;
     if(pos > 4) pos = 4;
     return layer.tips(msg, jquerySelector, {tips:pos});
 },
 //弹框打开html
 open:function(html,title,btnTxtArray,btnFuncArray,cancelFunc){
     var options = popup.createOptions(btnTxtArray,btnFuncArray,cancelFunc);
     options.type = 1;
     options.content = html;
     options.title = title||'';
     return layer.open(options);
 },
 //弹框打开一个隐藏的dom元素使之显示在页面上
 openDom:function(jquerySelector,title,btnTxtArray,btnFuncArray,cancelFunc){
     var options = popup.createOptions(btnTxtArray,btnFuncArray,cancelFunc);
     options.type = 1;
     options.content = $(jquerySelector);
     options.title = title||'';
     return layer.open(options);
 },
 //弹框打开1个网址,通过iframe实现
 openPage:function(url,title,widthHeightArray,btnTxtArray,btnFuncArray,cancelFunc){
     var options = popup.createOptions(btnTxtArray,btnFuncArray,cancelFunc);
     options.type = 2;
     options.content = [url, 'no'];//no表示无滚动条
     options.title = title||'';
     if(widthHeightArray != null && widthHeightArray != undefined && $.isArray(widthHeightArray)){
         options.area = widthHeightArray;
     }
     return layer.open(options);
 },
 
 //获取openPage方法打开的iframe页面内某个dom元素
 getOpenPageDom:function(pageDomSelector,pageIndex){
    return layer.getChildFrame(pageDomSelector,pageIndex);
 },
 
 //在openPage页面内部关闭当前页面
 closeOpenPage:function(iframeName){
    try{
       var index = parent.layer.getFrameIndex(iframeName); //先得到当前iframe层的索引
       parent.layer.close(index); //再执行关闭
    }catch(e){
        if (window['console'] && console['error']) console.error(e);
    }
 },
  
 //弹框显示1个单行文本录入框
 prompt:function(title,callback){
     var index = layer.prompt({
         title: title, 
         formType: 1
     }, function(inputTxt, index){
         popup.callbackFunc(callback,[inputTxt]);
         layer.close(index);
     });
     return index;
 },
 
 //弹框显示1个多行文本录入框
 prompt2:function(title,callback){
    var index = layer.prompt({
        title: title, 
        formType: 2
    }, function(inputTxt, index){
        popup.callbackFunc(callback,[inputTxt]);
        layer.close(index);
    });
    return index;
 },
 
 //关闭弹窗
 closePopup:function(index){
    layer.close(index);
 },
 //关闭所有弹窗
 closeAllPopup:function(){
    layer.closeAll();
 },
 
 //显示加载进度
 showLoading:function(){
     var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
     return index;
 },
 
 //动态按钮、事件，构造options.其中按钮最多2个，按钮事件最多2个，cancelFunc表示右上角关闭按钮事件,如不可关闭则方法返回false即可.
 createOptions:function(btnTxtArray,btnFuncArray,cancelFunc){
    var options = {
    };
    if(btnTxtArray){
        if($.isArray(btnTxtArray))
            options.btn = btnTxtArray;
        else
            options.btn = [btnTxtArray];
        if(btnFuncArray){
            if($.isArray(btnFuncArray)){
                if(btnFuncArray.length >= 1){
                    options.yes = function(index){
                        layer.close(index);
                        popup.callbackFunc(btnFuncArray[0]);
                    }
                }
                if(btnFuncArray.length >= 2){
                    options.btn2 = function(index){
                        layer.close(index);
                        popup.callbackFunc(btnFuncArray[1]);
                    }
                }
            }else{
                options.yes = function(index){
                    layer.close(index);
                    popup.callbackFunc(btnFuncArray);
                }
            }            
        }
    }
    if(cancelFunc){
        options.cancel = function(index){
            layer.close(index);
            popup.callbackFunc(cancelFunc);
        }
    }
    return options;
 },
 
 //回调函数检查，如果是函数则直接返回否则处理成函数
 callbackFunc : function (func,args) {
    if(func == null ||typeof(func) == "undefined" || func == undefined) 
        return ;
        
    if($.isFunction(func)) {
        //func.apply(this, args);
        func(args);
        return;
    } 
    
    try{
        var realFunc = eval(func);
        //realFunc.apply(this,args);
        realFunc(args);
    }catch(e){
        if (window['console'] && console['error']) console.error(e);
    }
 }
}
//初始化实例
var popup = new Popup();
