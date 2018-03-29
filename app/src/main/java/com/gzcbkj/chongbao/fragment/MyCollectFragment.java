package com.gzcbkj.chongbao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.MyCollectAdapter;
import com.gzcbkj.chongbao.bean.CollectionBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MyCollectFragment extends BaseFragment implements OnRefreshListener {

    private MyCollectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.my_collection);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        setViewsOnClickListener(R.id.ivRight);
        smartRefreshLayout.autoRefresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CollectionBean bean=getAdapter().getItem(i);
                Bundle bundle=new Bundle();
                if(bean.getArticle()!=null){
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,bean.getArticle());
                    gotoPager(ArticleDetailFragment.class,bundle);
                }else{
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,bean.getUserSay());
                    gotoPager(DynamicDetailFragment.class,bundle);
                }
            }
        });
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivRight:
                break;
        }
    }

    private MyCollectAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new MyCollectAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().queryCollenction(1, Constants.PAGE_COUNT,
                new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
                        if(getView()==null){
                            return;
                        }
                        refreshlayout.finishRefresh();
                        getAdapter().setDataList(bean.getColectionList());
                    }
                },getActivity(),false,(BaseActivity)getActivity()));
    }
}
