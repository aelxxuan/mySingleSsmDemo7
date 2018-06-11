/****
 * function:便于管理，js常用参数设置，服务于ssm
 * 在用户登录成功进入主页后载入，执行页面样式和事件的初始化。
 * Author：wendy
 * Date：2016-11-28
 * eg :
 * dp：jquery
 */
var pageSize=6;

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
var loadDataOfPage = function(url,paramMap,pageIndex,callback){
    var fullUrl = url+(url.indexOf("?")==-1?"?":"&");
    $.each(paramMap,function(k,v){
        fullUrl += (k+"="+v+"&");
    });
    fullUrl += "pageIndex="+pageIndex+"&pageSize="+pageSize;
    // console.debug('loadDataOfPage:'+fullUrl);
    var msgId = layer.load({shade: [0.1, '#fff']});
    $("#content").load(fullUrl, function () {
        layer.close(msgId);
        if(callback)
            doCallback(callback);
    });
}






