package com.gzcbkj.chongbao.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gigabud on 18-2-2.
 */

public class ResponseBean implements Serializable {
    private int code; // 0:成功;403:需要登录;300:参数错误;500:程序错误
    private String msg; // 错误信息
    private UserInfoBean user;
    private UserInfoBean userEntity;
    private ArticleBean article;
    private ArrayList<SayBean> sayList;

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

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public UserInfoBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserInfoBean userEntity) {
        this.userEntity = userEntity;
    }

    public ArrayList<SayBean> getSayList() {
        return sayList;
    }

    public void setSayList(ArrayList<SayBean> sayList) {
        this.sayList = sayList;
    }
}
