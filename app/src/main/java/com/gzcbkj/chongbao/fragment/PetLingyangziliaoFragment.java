package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangziliaoFragment extends BaseFragment implements OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_lingyang_ziliao;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.input_lingyang_ziliao);
        setViewsOnClickListener(R.id.tvSubmit);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvSubmit:
                break;
        }
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
    }
}
