package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.MessageCenterAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MessageCenterFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener {

    private MessageCenterAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_center;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.message_center);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<10;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    private MessageCenterAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new MessageCenterAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
    }
}
