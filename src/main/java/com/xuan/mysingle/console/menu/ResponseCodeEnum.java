package com.xuan.mysingle.console.menu;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public enum ResponseCodeEnum {
    SUCCESS("操作成功"),PARAMETERERROR("参数错误"),
    SIGNATUREERROE("签名错误"),NOPOWERERROR("无访问权限"),
    APPKEYERROR("appkey错误"), APIINVOKEERROR("错误"),
    INTERIORERROR("内部错误");

    private  String description;

    private ResponseCodeEnum(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
