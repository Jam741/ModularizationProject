package com.yingwumeijia.commonlibrary.net.exception;

/**
 * Created by Jam on 2017/2/17 下午5:38.
 * Describe:
 */

public class ApiException extends RuntimeException {


   private int error_codel;
    private String error_message;

    public ApiException(int error_codel, String error_message) {
        this.error_codel = error_codel;
        this.error_message = error_message;
    }


    public int getError_codel() {
        return error_codel;
    }

    public void setError_codel(int error_codel) {
        this.error_codel = error_codel;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
