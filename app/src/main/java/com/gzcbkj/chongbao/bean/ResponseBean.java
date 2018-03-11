package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by gigabud on 18-2-2.
 */

public class ResponseBean implements Serializable {
    private int code; // 0:成功;403:需要登录;300:参数错误;500:程序错误
    private String msg; // 错误信息
    private UserInfoBean user;

    public boolean isSuccess() {
        // TODO Auto-generated method stub
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }
}
