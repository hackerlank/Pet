package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetJiyangFragment extends BaseFragment implements OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_jiyang;
    }

    @Override
    protected void onViewCreated(View view) {
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){

        }
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {

    }
}
