package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangDetailFragment extends BaseFragment implements OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_lingyang_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.lingyang_detail);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        setViewsOnClickListener(R.id.tvLingyang);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvLingyang:
                gotoPager(PetLingyangziliaoFragment.class, null);
                break;
        }
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
    }
}
