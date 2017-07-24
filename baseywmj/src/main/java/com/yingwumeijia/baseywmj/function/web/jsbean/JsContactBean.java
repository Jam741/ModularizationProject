package com.yingwumeijia.baseywmj.function.web.jsbean;

import java.io.Serializable;

/**
 * Created by Jam on 2017/4/18 下午3:10.
 * Describe:
 */

public class JsContactBean implements Serializable{

    private String phone;
    private String name;


    public JsContactBean(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
