package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.MyPetAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MyPetFragment extends BaseFragment implements OnRefreshListener {

    private MyPetAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_pet;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.my_pet);
        setViewVisible(R.id.ivRight);
        setImage(R.id.ivRight,R.drawable.more);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
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

    private MyPetAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new MyPetAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        UserInfoBean myInfo= DataManager.getInstance().getMyUserInfo();
        HttpMethods.getInstance().findOwnPetList(myInfo.getUserId(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }
}
