package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
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
        int id = view.getId();
        switch (id) {

        }
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        ProgressSubscriber subscriber = new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
            }
        }, getActivity(), false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).connectError(e);
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).serverError(errorCode, errorMsg);
            }
        });
        HttpMethods.getInstance().fosterPetList(1, 30, subscriber);
    }
}
