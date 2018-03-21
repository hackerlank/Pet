package com.gzcbkj.chongbao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.DynamicAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.manager.IDataChangeListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class GroupFriendItemFragment extends BaseFragment implements OnRefreshListener,IDataChangeListener {

    private DynamicAdapter mAdapter;
    private View mTopView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_friend_item;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        mTopView= LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_top_layout,null);
        listView.addHeaderView(mTopView);
        initTopView(DataManager.getInstance().getMyUserInfo());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        ArrayList<SayBean> list= (ArrayList<SayBean>) DataManager.getInstance().getDate("sayList", new TypeToken<ArrayList<SayBean>>(){}.getType());
        getAdapter().setDataList(list);
        if(list==null || list.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    gotoPager(PublishFrigment.class,null);
                }else if(i>1) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,getAdapter().getItem(i-2));
                    gotoPager(DynamicDetailFragment.class, bundle);
                }
            }
        });
        DataManager.getInstance().addDataChangeListener(this);
    }

    private void initTopView(UserInfoBean userInfo){
        Utils.loadImages(R.drawable.default_1,userInfo.getSpaceImg(),(ImageView) mTopView.findViewById(R.id.ivBg));
        Utils.loadImages(R.drawable.touxiang,userInfo.getHeadPic(),(ImageView) mTopView.findViewById(R.id.ivAvater));
        ((TextView) mTopView.findViewById(R.id.tvName)).setText(userInfo.getUsername());
    }



    private DynamicAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new DynamicAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void updateUIText() {
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().querySpace(new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                if(bean!=null) {
                    initTopView(bean.getUserEntity());
                }
            }
        },getActivity(),false,(BaseActivity)getActivity()));

        HttpMethods.getInstance().querySayList(1, 20,1,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
                if(bean!=null){
                    DataManager.getInstance().saveData("sayList",bean.getSayList());
                    getAdapter().setDataList(bean.getSayList());
                }
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }

    public void onDestroyView(){
        super.onDestroyView();
        DataManager.getInstance().removeDataChangeListener(this);
    }

    @Override
    public void needRefrsh() {
        if(getView()!=null){
            ((SmartRefreshLayout) fv(R.id.smartLayout)).autoRefresh();
        }
    }
}
