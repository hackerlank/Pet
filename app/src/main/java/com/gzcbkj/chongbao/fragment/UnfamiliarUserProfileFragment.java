package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 * 陌生用户
 */

public class UnfamiliarUserProfileFragment extends UserProfileFragment{

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        setText(R.id.tvBtn1,R.string.add_friend);
        setText(R.id.tvBtn2,R.string.follow_he);
        setViewVisible(R.id.tvBtn2);
    }
}
