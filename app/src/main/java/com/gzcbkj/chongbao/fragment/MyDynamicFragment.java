package com.gzcbkj.chongbao.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.DynamicAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.manager.DataManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class MyDynamicFragment extends BaseFragment implements OnRefreshListener {

    private DynamicAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_dynamic;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewVisible(R.id.ivRight);
        setImage(R.id.ivRight,R.drawable.more);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
        View topView= LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_top_layout,null);
        listView.addHeaderView(topView);
        initTopView(topView);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    return;
                }
                gotoPager(i==1?PublishFrigment.class:DynamicDetailFragment.class,null);
            }
        });
    }

    private void initTopView(View topView){
        topView.findViewById(R.id.ivAvater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPager(UserProfileFragment.class,null);
            }
        });
    }

    private DynamicAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new DynamicAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean= DataManager.getInstance().getMyUserInfo();
        setText(R.id.tvTitle,bean.getUsername());
        setText(R.id.tvName,bean.getUsername());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
