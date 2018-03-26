package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class LocationRingFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location_ring;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewInvisible(R.id.ivLeft);
        setText(R.id.tvTitle,R.string.location_ring);
        setViewVisible(R.id.tvRight);
        setText(R.id.tvRight,R.string.manager);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
