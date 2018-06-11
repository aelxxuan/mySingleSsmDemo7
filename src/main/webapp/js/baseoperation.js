/****
 *
 * Author：miaoying
 * Date：2016-12-5
 * eg :
 */

var selectAll=function(){
    var flag= $("input[name=ckAll]").prop("checked");
    if(flag==true){
        $("input[name=ckObj]").prop("checked",true);
    }else{
        $("input[name=ckObj]").prop("checked",false);
    }
}
var selectOne=function(){
    var ch = $(":checkbox[name=ckObj]");
    var num=0;
    for(i=0;i<ch.length;i++){
        if(ch[i].checked){
            num++;
        }
    }
    if(num==ch.length){
        $("input[name=ckAll]").prop("checked",true);
    }else{
        $("input[name=ckAll]").prop("checked",false);
    }
}
