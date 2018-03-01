package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class EditProfileFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.personal_prdfile);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
