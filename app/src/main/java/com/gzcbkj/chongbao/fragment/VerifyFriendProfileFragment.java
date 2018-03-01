package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 * 详细信息
 */

public class VerifyFriendProfileFragment extends UserProfileFragment {


    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        setText(R.id.tvTitle,R.string.verify_friend);
        setText(R.id.tvBtn1,R.string.pass_verify);
        fv(R.id.tvBtn1).setBackgroundResource(R.drawable.bg_pass_verify);
    }


    @Override
    public void onClick(View view) {

    }
}
