package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.JilingguanliAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class PetCenterItemFragment extends BaseFragment implements OnRefreshListener {
    private JilingguanliAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.item_friend_other;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private JilingguanliAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new JilingguanliAdapter(getActivity());
        return mAdapter;
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
