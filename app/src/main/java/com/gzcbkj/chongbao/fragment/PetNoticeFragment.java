package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.PetNoticeAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetNoticeFragment extends BaseFragment implements OnRefreshListener {

    private PetNoticeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_notice;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.pet_report);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
        setViewsOnClickListener(R.id.ivRight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivRight:
                break;
        }
    }

    private PetNoticeAdapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new PetNoticeAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().findorlostInfoList(1, 30, "",
                new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
                        if(getView()==null){
                            return;
                        }
                        refreshlayout.finishRefresh();
                        getAdapter().setDataList(bean.getFindorlostInfoList());
                    }
                }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
