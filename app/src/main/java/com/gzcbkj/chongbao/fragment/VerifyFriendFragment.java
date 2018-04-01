package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.VerifyFriendAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class VerifyFriendFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener {

    private VerifyFriendAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify_friend;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.verify_friend);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoPager(i%2==0?VerifyFriendProfileFragment.class:UserProfileFragment.class,null);
            }
        });
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    private VerifyFriendAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new VerifyFriendAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
        HttpMethods.getInstance().applyList(1, Constants.PAGE_COUNT,
                new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
                        if(getView()==null){
                            return;
                        }
                        refreshlayout.finishRefresh();
                        getAdapter().setDataList(bean.getApplyList());
                    }
                },getActivity(),false,(BaseActivity)getActivity()));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
    }
}
