package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.JilingguanliAdapter;
import com.gzcbkj.chongbao.adapter.PetLingyangAdapter;
import com.gzcbkj.chongbao.adapter.PetShideAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class PetCenterItemFragment extends BaseFragment implements OnRefreshListener {
    private PetLingyangAdapter mAdapter1;
    private PetShideAdapter mAdapter2;
    private int mItemIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.item_friend_other;
    }

    public PetCenterItemFragment setItemIndex(int index) {
        mItemIndex = index;
        return this;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        ArrayList<ResponseBean> list = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            list.add(new ResponseBean());
        }
        if (mItemIndex == 0) {
            listView.setAdapter(getAdapter1());
            getAdapter1().setDataList(list);
        } else {
            listView.setAdapter(getAdapter2());
            getAdapter2().setDataList(list);
        }

        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mItemIndex == PetCenterFragment.PET_LINGYANG_INDEX) {
                    gotoPager(PetLingyangDetailFragment.class, null);
                }else if(mItemIndex==PetCenterFragment.PET_SHIDE_INDEX){
                    gotoPager(PetShideGaoshiFragment.class, null);
                }else if(mItemIndex==PetCenterFragment.PET_ZOUSHI_INDEX){
                    gotoPager(PetDiushiGaoshiFragment.class, null);
                }
            }
        });
    }

    private PetLingyangAdapter getAdapter1() {
        if (mAdapter1 == null)
            mAdapter1 = new PetLingyangAdapter(getActivity());
        return mAdapter1;
    }

    private PetShideAdapter getAdapter2() {
        if (mAdapter2 == null)
            mAdapter2 = new PetShideAdapter(getActivity());
        return mAdapter2;
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
