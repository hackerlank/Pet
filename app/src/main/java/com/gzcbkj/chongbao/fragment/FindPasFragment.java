package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 */

public class FindPasFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_password;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.find_pas);
        setText(R.id.tvRight,R.string.submit);
        setViewVisible(R.id.tvRight);
        setViewsOnClickListener(R.id.tvRight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
