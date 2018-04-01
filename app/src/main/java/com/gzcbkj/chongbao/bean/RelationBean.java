package com.gzcbkj.chongbao.bean;

/**
 * Created by gigabud on 18-2-2.
 */

public class RelationBean extends ResponseBean {

    private String createTime;
    private String headPic1;
    private String headPic2;
    private long id;
    private String relationship; //用户与用户关系;2:关注;3:粉丝;4:好友
    private String updateTime;
    private String userId;
    private String userIdTwo;
    private String username;
    private String username2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeadPic1() {
        return headPic1;
    }

    public void setHeadPic1(String headPic1) {
        this.headPic1 = headPic1;
    }

    public String getHeadPic2() {
        return headPic2;
    }

    public void setHeadPic2(String headPic2) {
        this.headPic2 = headPic2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIdTwo() {
        return userIdTwo;
    }

    public void setUserIdTwo(String userIdTwo) {
        this.userIdTwo = userIdTwo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
}
