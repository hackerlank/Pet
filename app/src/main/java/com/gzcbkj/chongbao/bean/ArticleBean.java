package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by gigabud on 18-3-5.
 * 精选文章
 */

public class ArticleBean implements Serializable {
    private long id;
    private String articleGrade;  //文章等级
    private String mainPic;  //文章主图
    private String userId;
    private String userName;  //用户名
    private String userHead;  //用户头像
    private String title;  //文章标题
    private String content;  //文章内容
    private String shareNum;  //分享次数
    private String praiseNum;  //点赞次数
    private String commentNum;  //评论次数
    private String collectionNum;  //收藏次数
    private String createTime;  //发表时间
    private String type;  //文章类型;宠物指南:guide|宠物公益:welfare|宠物百科:encyclopedias
    private int shareFlag;  //是否分享过;1:是
    private int praiseFlag;  //是否点赞过;1:是
    private int collectionFlag;  //是否收藏过:1:是
    private long updateTime;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getArticleGrade() {
        return articleGrade;
    }

    public void setArticleGrade(String articleGrade) {
        this.articleGrade = articleGrade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(int shareFlag) {
        this.shareFlag = shareFlag;
    }

    public int getPraiseFlag() {
        return praiseFlag;
    }

    public void setPraiseFlag(int praiseFlag) {
        this.praiseFlag = praiseFlag;
    }

    public int getCollectionFlag() {
        return collectionFlag;
    }

    public void setCollectionFlag(int collectionFlag) {
        this.collectionFlag = collectionFlag;
    }
}
