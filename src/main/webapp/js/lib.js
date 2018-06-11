/****
 * function:通用库，服务于SSO，一些代码固话样式
 * Author：wendy
 * Date：2016-10-28
 * eg : TLibSSO.isEmail("ywg369@126.com")
 */
(function(){	
    var emailLists = ["163.com", "qq.com", "gmail.com", "126.com", "hotmail.com", "yahoo.com", "yahoo.com.cn", "live.com", "sohu.com", "sina.com"];
    //var phonePattern = /^(13[0-9]\d{8}|15[0-35-9]\d{8}|18[0-9]\d{8}|17[0-9]\d{8}|14[57]\d{8})$/;
    var phonePattern = /^1[3-9]\d{9}$/;
    var emailPattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var illegalPhonePattern = /^17[0,1]\d{8}$/;
    var legalLoginNamePattern = /^[a-zA-Z0-9_\u4e00-\u9fa5]{3,20}$/;
    var imageSuffixPatten=/^(png|gif|jpg)$/;
    var isLoginNamelegal =function(loginname){
        return legalLoginNamePattern.test(loginname);
    }
    var isEmail = function(email){
        return emailPattern.test(email);
    };
    var isPhone = function(phone){
        return phonePattern.test(phone) && !illegalPhonePattern.test(phone);
    };
    var isPhoneIllegal = function(phone){
        return illegalPhonePattern.test(phone);
    }
    var isNum = function(str){
	    var regex = /^[0-9]*[1-9][0-9]*$/;
	    return regex.test(str);
	}
    var isChina = function(msg){
    	var china=/[\u4E00-\u9FA0]/;
    	return china.test(msg)
    };
    var pwdInputIsLegal = function(pwd){
    	var numRegex = /[0-9]/;
		var charRegex =/[a-zA-Z]/;
		var markRegex=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
		if (!numRegex.test(pwd) && !charRegex.test(pwd) && !markRegex.test(pwd)) {
			return false;
		} 
		return true;
    }
    var pwdInputIsLegalForStr=function (pwd) {
        var pwdPattern=/^[0-9a-zA-Z`~!@#$%^&*()_+<>?:"{},.\/;'[\]]{6,20}$/g;
        if(!pwdPattern.test(pwd)) return false;
        return true;
    }
    var pwdLengthIsLegal=function (pwd) {
        if(pwd!=undefined&&typeof pwd=="string"&&pwd.length>=6&&pwd.length<=20)
            return true;
        return false;
    }
    var showEmailList = function(email_list) {
        if($(email_list).val()==undefined)
            return;
        var chinaPattern=/[\u4E00-\u9FA0]/;
        if(chinaPattern.test($(email_list).val()))
            return;
        if ($(email_list).val().indexOf("@") != -1) {
            $("ul.email-list").remove();
            $(email_list).after("<ul class='email-list'></ul>");
            $(email_list).parents("li").css({"position":"relative","z-index":"1000"});
            var email = $(email_list).val();
            var emailPre = email.substring(0, email.indexOf("@"));
            var emailAfter = email.substring(email.indexOf("@")+1);
            for (var i = 0; i < emailLists.length; i++) {
                if(emailLists[i].substring(0,emailAfter.length)==emailAfter && emailAfter!="")
                    $("ul.email-list").append("<li index='"+i+"'>" + emailPre + "@" + emailLists[i] + "</li>");
                else if(emailAfter=="")
                    $("ul.email-list").append("<li index='"+i+"'>" + emailPre + "@" + emailLists[i] + "</li>");
            }
            $("ul.email-list li").click(function () {
                $(email_list).val($(this).text());
                $("ul.email-list").remove();
                $(email_list).parent().parent().next().find("input").focus();
            });
        } else {
            $("ul.email-list").remove();
        }
    };
    var hideEmailList=function(){
    	$("ul.email-list").hide();
    }
    var showPromptMsg = function(targetObj,msg){
        if(msg==undefined){
            return;
        }
        if(msg.indexOf("<img")==-1)
            msg  = "<img src='../images/prompt.png' >"+msg;
        setTimeout(function(){//防止点击按钮时导致点击不上的状况
            var promptObj = $(".Prompt").stop().animate({"min-height":"20px"},200).html(msg).css({"color":"red"});
            if(msg.indexOf("promptok.png")>-1)
                promptObj.css({"color":"#1ac20d"});
            if(targetObj!=null)
                targetObj.css({"border":"1px solid #ff1f1f"});
        },100);
    };
    var clearPromptMsg = function(){
    	setTimeout(function(){//防止点击按钮时导致点击不上的状况
    		$(".Prompt").stop().animate({"min-height":"0px"},200).html("");
    	},100);
    };
    var timeCountDown =  function(timeCtrlId){
        $(timeCtrlId).val("60s后重新获取").css({"background":"#ddd","color":"#999","border":"1px solid #eee"}).attr("disabled",true);
        var countdown=59;
        var timer = setInterval(function() {
            if (countdown==0) {
                $(timeCtrlId).val("获取验证码").css({"background":"#288ff2","color":"#fff","border":"0px"}).attr("disabled",false);
                countdown=59;
                window.clearInterval(timer);
            } else {
                $(timeCtrlId).val(countdown+"s后重新获取").css({"background":"#ddd","color":"#999","border":"1px solid #eee"}).attr("disabled",true);
                countdown--;
            }
        },1000);
    };
    var inputBase = function(){
        // 禁止文本框输入空格
        // $("input").keyup(function(){
        //     $(this).val($(this).val().replace(/\s/g,''));
        // });
        $("input[type='text'],input[type='password']").focus(function(){
            $(this).parent("div").css({"border":"1px solid #a9d4fd"});
        }).blur(function(){
            $(this).parent("div").css({"border":"1px solid #ccc"});
        });
        $(document).click(function() {
            $("ul.email-list").remove();
        });

        //$("ul.email-list").click(function (e) {
        //    console.log(" email list click!");
        //    e.stopPropagation();
        //});
    }
    
    //日期format支持
    Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "H+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    //sample
    //var time1 = new Date().Format("yyyy-MM-dd");
    //var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");

    var isImageTypeLegal=function(fileName){
        if(fileName.length>0){
            var suffix=fileName.split(".").length>1?fileName.split(".")[fileName.split(".").length-1]:null;
            return imageSuffixPatten.test(suffix);
        }
        return false;
    }
    var codeNum = {SUCCESS:"SUCCESS",PARAMETERERROR:"PARAMETERERROR",SIGNATUREERROE:"SIGNATUREERROE",NOPOWER:"NOPOWER",APPKEYERROR:"APPKEYERROR",APIINVOKEERROR:"APIINVOKEERROR",INTERIORERROR:"INTERIORERROR"}
    return TLibSSO ={
        isEmail:isEmail,
        isPhone:isPhone,
        isPhoneIllegal:isPhoneIllegal,
        isChina:isChina,
        isNum:isNum,
        pwdInputIsLegal:pwdInputIsLegal,
        pwdInputIsLegalForStr:pwdInputIsLegalForStr,
        pwdLengthIsLegal:pwdLengthIsLegal,
        showEmailList:showEmailList,
        hideEmailList:hideEmailList,
        showPromptMsg:showPromptMsg,
        timeCountDown:timeCountDown,
        inputBase:inputBase,
        clearPromptMsg:clearPromptMsg,
        codeNum:codeNum,
        isLoginNamelegal:isLoginNamelegal,
        isImageLegal:isImageTypeLegal
    };
   
})();
