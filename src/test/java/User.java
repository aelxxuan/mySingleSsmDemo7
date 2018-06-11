/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/15
 */
public class User {

    private String name;
    private int age;
    private String phone;

    public User(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
