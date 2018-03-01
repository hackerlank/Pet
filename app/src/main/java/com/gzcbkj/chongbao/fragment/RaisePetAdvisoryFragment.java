package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/1.
 * 养宠咨询
 */

public class RaisePetAdvisoryFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_raise_pet_advisory;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.raise_pet_advisory);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
