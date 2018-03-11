package com.gzcbkj.chongbao.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gigabud on 18-3-5.
 */

public class SayDetailResponse extends ResponseBean {
    private ArrayList<PraiseUser> sayPraiseList;  //点赞人列表
    private SayBean userSayEntity;

    public ArrayList<PraiseUser> getSayPraiseList() {
        return sayPraiseList;
    }

    public void setSayPraiseList(ArrayList<PraiseUser> sayPraiseList) {
        this.sayPraiseList = sayPraiseList;
    }

    public SayBean getUserSayEntity() {
        return userSayEntity;
    }

    public void setUserSayEntity(SayBean userSayEntity) {
        this.userSayEntity = userSayEntity;
    }

    public class PraiseUser implements Serializable{
        private long id;
        private String userId;
        private String createTime;
        private long sayId;
        private String userName;
        private String userHead;
        private int type;  //说说操作类型;1:点赞；2：收藏;3:分享

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getSayId() {
            return sayId;
        }

        public void setSayId(long sayId) {
            this.sayId = sayId;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
