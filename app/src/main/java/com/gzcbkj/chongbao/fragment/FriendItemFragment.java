package com.gzcbkj.chongbao.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.FriendAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class FriendItemFragment extends BaseFragment implements OnRefreshListener {

    public static final int INDEX_FRIEND=0;  //好友
    public static final int INDEX_FOLLOWED=1; //关注
    public static final int INDEX_FOLLOWING=2; //粉丝

    private int mCurrentIndex;

    private FriendAdapter mAdapter;
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
        getAdapter().setCurrentIndex(mCurrentIndex);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoPager(UserProfileFragment.class,null);
            }
        });
        setTotalStr();
    }

    private void setTotalStr(){
        String toatlStr=getString(R.string.total,String.valueOf(getAdapter().getCount()));
        if(mCurrentIndex==INDEX_FRIEND){
            toatlStr+=getString(R.string.friend);
        }else if(mCurrentIndex==INDEX_FOLLOWED){
            toatlStr+=getString(R.string.followed);
        }else{
            toatlStr+=getString(R.string.following);
        }
        setText(R.id.tvTotal,toatlStr);
    }

    public FriendItemFragment setCurrentIndex(int index){
        mCurrentIndex=index;
        return this;
    }

    private FriendAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new FriendAdapter(getActivity());
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
