package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 * 投诉
 */

public class ComplaintFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complaint;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.complaint);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
