package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangDetailFragment extends BaseFragment implements OnRefreshListener {

    private AdoptPetBean mBean;

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
        mBean = (AdoptPetBean) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
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
        HttpMethods.getInstance().tobeAdoptInfo(mBean.getId(), new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {

            }
        }, getActivity(), false, (BaseActivity)getActivity()));
    }
}
