package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.JilingguanliAdapter;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class JilingguanliFragment extends BaseFragment implements OnRefreshListener {

    private JilingguanliAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jiling_guanli;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.jiling_guanli);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<BaseBean> list=new ArrayList<>();
        for(int i=0;i<10;++i){
            list.add(new BaseBean());
        }
        getAdapter().setDataList(list);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        setViewsOnClickListener(R.id.ivRight);
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

    private JilingguanliAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new JilingguanliAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {

    }
}
