package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishArticleFrigment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_article;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.publish_article);
        TextView tvRight=fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        setText(tvRight,getString(R.string.publish));
        tvRight.setBackgroundResource(R.drawable.publish_bg);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setTextColor(Color.WHITE);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
