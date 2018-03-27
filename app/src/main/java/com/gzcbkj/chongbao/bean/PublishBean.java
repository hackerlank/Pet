package com.gzcbkj.chongbao.bean;

import java.io.Serializable;


public class PublishBean implements Serializable {
    private ArticleBean article;
    private String createTime;
    private long id;
    private long sayArticleId;
    private String type;
    private String userId;
    private SayBean userSay;


    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSayArticleId() {
        return sayArticleId;
    }

    public void setSayArticleId(long sayArticleId) {
        this.sayArticleId = sayArticleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SayBean getUserSay() {
        return userSay;
    }

    public void setUserSay(SayBean userSay) {
        this.userSay = userSay;
    }
}
