package com.gzcbkj.chongbao.bean;

import java.util.ArrayList;

/**
 * Created by gigabud on 18-3-5.
 */

public class ArticleListResponse extends ResponseBean {

    private ArrayList<ArticleBean> articleList;

    public ArrayList<ArticleBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<ArticleBean> articleList) {
        this.articleList = articleList;
    }
}
