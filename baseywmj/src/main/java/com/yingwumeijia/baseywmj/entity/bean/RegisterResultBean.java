package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by jamisonline on 2017/6/15.
 */

public class RegisterResultBean {

    boolean needConfirm;

    String message;

    String token;

    UserBean customerDto;


    public UserBean getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(UserBean customerDto) {
        this.customerDto = customerDto;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
