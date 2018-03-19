package com.gzcbkj.chongbao.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/11.
 */

public class SayBean implements Serializable {

    private String content;
    private String createTime;
    private long id;
    private ArrayList<SayImg> sayImgList;
    private double sayLng;
    private double sqyLat;
    private String spaceImg;
    private String status;
    private String type;
    private String userHead;
    private String userId;
    private String userName;
    private int collectionNum;
    private int commentNum;
    private int complaintNum;
    private int praiseFlag;
    private int praiseNum;
    private int shareNum;

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getComplaintNum() {
        return complaintNum;
    }

    public void setComplaintNum(int complaintNum) {
        this.complaintNum = complaintNum;
    }

    public int getPraiseFlag() {
        return praiseFlag;
    }

    public void setPraiseFlag(int praiseFlag) {
        this.praiseFlag = praiseFlag;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public ArrayList<SayImg> getSayImgList() {
        return sayImgList;
    }

    public void setSayImgList(ArrayList<SayImg> sayImgList) {
        this.sayImgList = sayImgList;
    }

    public double getSayLng() {
        return sayLng;
    }

    public void setSayLng(double sayLng) {
        this.sayLng = sayLng;
    }

    public double getSqyLat() {
        return sqyLat;
    }

    public void setSqyLat(double sqyLat) {
        this.sqyLat = sqyLat;
    }

    public String getSpaceImg() {
        return spaceImg;
    }

    public void setSpaceImg(String spaceImg) {
        this.spaceImg = spaceImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class SayImg implements Serializable{
        private long id;
        private String imgUrl;
        private long sayId;

        public long getSayId() {
            return sayId;
        }

        public void setSayId(long sayId) {
            this.sayId = sayId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
