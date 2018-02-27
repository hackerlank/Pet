package com.pet.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.pet.R;
import com.pet.adapter.ArticleAdapter;
import com.pet.bean.BaseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class HomeFragment extends BaseFragment implements OnRefreshListener {

    private ArticleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<BaseBean> list=new ArrayList<>();
        for(int i=0;i<10;++i){
            list.add(new BaseBean());
        }
        getAdapter().setDataList(list);
        View topView= LayoutInflater.from(getActivity()).inflate(R.layout.home_top_layout,null);
        listView.addHeaderView(topView);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    private ArticleAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new ArticleAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {

    }
}
