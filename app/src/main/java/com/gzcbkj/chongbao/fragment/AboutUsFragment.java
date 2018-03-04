package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class AboutUsFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_us;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.about);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
