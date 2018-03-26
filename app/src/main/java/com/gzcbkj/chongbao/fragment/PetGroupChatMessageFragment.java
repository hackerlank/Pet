package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.PetGroupMessageAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetGroupChatMessageFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener {

    private PetGroupMessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_center;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
//        ArrayList<ResponseBean> list=new ArrayList<>();
//        for(int i=0;i<10;++i){
//            list.add(new ResponseBean());
//        }
//        getAdapter().setDataList(list);
//        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
//        smartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    private PetGroupMessageAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new PetGroupMessageAdapter(getActivity());
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
