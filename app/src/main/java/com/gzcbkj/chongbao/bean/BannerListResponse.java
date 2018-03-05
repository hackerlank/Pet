package com.gzcbkj.chongbao.bean;

import java.util.ArrayList;

/**
 * Created by gigabud on 18-3-5.
 */

public class BannerListResponse extends ResponseBean {
    private ArrayList<BannerBean> bannerList1;  //首页顶部banner图
    private ArrayList<BannerBean> bannerList2;
    private ArrayList<ArticleBean> articleList; //精选文章,固定显示三篇

    public ArrayList<BannerBean> getBannerList1() {
        return bannerList1;
    }

    public void setBannerList1(ArrayList<BannerBean> bannerList1) {
        this.bannerList1 = bannerList1;
    }

    public ArrayList<BannerBean> getBannerList2() {
        return bannerList2;
    }

    public void setBannerList2(ArrayList<BannerBean> bannerList2) {
        this.bannerList2 = bannerList2;
    }

    public ArrayList<ArticleBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<ArticleBean> articleList) {
        this.articleList = articleList;
    }
}
