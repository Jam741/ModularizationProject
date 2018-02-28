package com.yingwumeijia.baseywmj.function.web.jsbean;

import java.io.Serializable;

/**
 * Created by Jam on 2017/4/6 上午11:05.
 * Describe:
 */

public class JsBridgeInfo<T> implements Serializable {


    public int code;
    public T data;
    public String extra;
    public String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
