package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishFrigment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.publish);
        setViewsOnClickListener(R.id.tvPublishPic, R.id.tvPublishVideo, R.id.tvPublishArticle);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvPublishPic:
                gotoPager(PublishPictureFrigment.class, null);
                break;
            case R.id.tvPublishVideo:
                gotoPager(PublishVideoFrigment.class, null);
                break;
            case R.id.tvPublishArticle:
                gotoPager(PublishArticleFrigment.class, null);
                break;
        }
    }
}
