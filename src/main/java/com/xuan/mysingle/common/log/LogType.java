package com.xuan.mysingle.common.log;

/**
 * 系统操作日志类型
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public enum LogType {
    SYS("系统","SYS"),
    DATA("数据","DATA"),
    TEACHER("教师","TEACHER"),
    SALER("销售","SALER");


    private String description;
    private String key;
    //构造方法
    private LogType(String description,String key){
        this.key = key;
        this.description = description;
    }

    /**
     * 根据Key获取描述
     *
     * @param key
     * @return
     */
    public static String getDescription(String key){
        for(LogType type: LogType.values()){
            if(type.getKey().equals(key)){
                return type.getDescription();
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
