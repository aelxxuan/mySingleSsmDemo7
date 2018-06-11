//文件上传
var uploaderInit = false ;
var showUploadConfirm = true;
var uploadLayerId = -1;
var createFileUploader = function (uploadAction,submitButton,showMsg,reloadData,params) {
    $('input[type="file"]').ajaxfileupload({
        action: uploadAction,
        valid_extensions : ['xls','xlsx'],
        params: params,
        submit_button:submitButton,//配置了此项则在此按钮的click事件触发上传动作
        onComplete: function(response) {
            layer.close(uploadLayerId);
            submitButton.text("确定").prop("disabled",false);
            if(showMsg){
                console.dir(response)
                var message = response.message;
                var count = response.count;
                if(message.lastIndexOf("总共")<0) {
                    showAlert(message);
                }else if(message && count>0){
                    var jsonstr= JSON.stringify(response.errorList);
                    $("#failList").attr("data",jsonstr); //隐藏域，用于存放错误数据
                    message = message.replaceAll(";",",");
                    message = message+"<a onclick='downErrorList()' style='color:red; cursor:pointer'>下载失败清单</a>";
                    showAlert(message);
                }else if(message){
                    showAlert(message);//显示上传结果
                }else{
                    showAlert("上传失败！");
                }
            }
            if(reloadData){
                loadData(1);
            }
        },
        onStart: function() {
            if(showUploadConfirm){
                showAlert('您要上传文件吗？','确定',function () {
                    showUploadConfirm = false;
                    submitButton.click();//触发无需确认的上传
                },function () {
                    showUploadConfirm = true;
                });
                return false;
            }else{
                showUploadConfirm = true;
                uploadLayerId = layer.load({shade: [0.1, '#fff']});
                submitButton.text("上传中").prop("disabled",true);
                return true;
            }
        },
        onCancel: function() {
            showAlert('请先选择上传文件');
        }
    });
}

/**
 * 初始化文件上传组件
 * @param uploadAction 上传处理后台url
 * @param showMsg 是否显示上传结果,默认是
 * @param reloadData 是否重新载入列表数据，默认是并调用loadData(1)
 */
var initFileUploader= function (uploadAction,submitBtn,params,showMsg,reloadData) {
    if(!uploaderInit){
        $.getScript("/js/plugins/fileupload/jquery.ajaxfileupload.js",function () {
            uploaderInit = true;
            createFileUploader(uploadAction,submitBtn,showMsg||true,reloadData||true,params||{});
        });
    }
}

//下载错误数据
var downErrorList = function () {
    var list = $("#failList").attr("data");
    var form = $('<form action="/user/exportErrorList" method="post"></form>');
    var zk_input = $('<input type="hidden" name="errorList" value=' + list + ' /><input type="submit"  value=""/>)');
    form.append(zk_input);
    $('#submitForm').append(form);
    form.submit();

}