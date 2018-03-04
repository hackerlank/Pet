package com.gzcbkj.chongbao.bean;

import com.google.gson.Gson;

public class BasicResponse<T> {
    private int code; // 0:成功;403:需要登录;300:参数错误;500:程序错误
    private String msg; // 错误码 错误码 GBxxxxxxx
    private T data;

    public boolean isSuccess() {
        // TODO Auto-generated method stub
        return code == 0;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

