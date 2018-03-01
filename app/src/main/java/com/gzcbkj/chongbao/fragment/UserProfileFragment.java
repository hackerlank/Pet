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
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
