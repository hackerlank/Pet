package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetShideDetailFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_shide_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.pet_detail);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }
}
