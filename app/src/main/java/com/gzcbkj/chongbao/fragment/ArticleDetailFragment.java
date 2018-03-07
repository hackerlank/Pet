package com.gzcbkj.chongbao.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.DynamicCommentAdapter;
import com.gzcbkj.chongbao.adapter.LikeCommentAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class ArticleDetailFragment extends BaseFragment implements OnRefreshListener {
    private DynamicCommentAdapter mAdapter;
    private LikeCommentAdapter mLikeCommentAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.detail);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
        View topView= LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_detail_top_layout,null);
        listView.addHeaderView(topView);
        initTopView(topView);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    private void initTopView(View topView){
        RecyclerView recyclerView=topView.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setAdapter(getLikeCommentAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<20;++i){
            list.add(new ResponseBean());
        }
        getLikeCommentAdapter().setDataList(list);
    }

    private DynamicCommentAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new DynamicCommentAdapter(getActivity());
        return mAdapter;
    }

    private LikeCommentAdapter getLikeCommentAdapter(){
        if(mLikeCommentAdapter==null)
            mLikeCommentAdapter=new LikeCommentAdapter(getActivity());
        return mLikeCommentAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
