package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 * 详细信息
 */

public class UserProfileFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_profile;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.detail_msg);
//        setViewVisible(R.id.ivRight);
//        setImage(R.id.ivRight,R.drawable.more);
        setViewsOnClickListener(R.id.tvBtn1,R.id.ivRight,R.id.llAlbum);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvBtn1:
                break;
            case R.id.ivRight:
                break;
            case R.id.llAlbum:
                gotoPager(MyDynamicFragment.class,null);
                break;
        }
    }
}
