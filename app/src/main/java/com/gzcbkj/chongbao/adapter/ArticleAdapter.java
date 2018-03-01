package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.BaseBean;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class ArticleAdapter extends MyBaseAdapter<BaseBean> {
    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=mLayoutInflater.inflate(R.layout.item_article,null);
        }
        return view;
    }
}
