package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by gigabud on 18-3-5.
 * 首页顶部banner图
 */

public class BannerBean implements Serializable {

    private String imgUrl;  //图片地址
    private String jumpUrl; //图片跳转地址
    private String position;  //显示位置.1:首页顶部;2:首页第二个部位

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
