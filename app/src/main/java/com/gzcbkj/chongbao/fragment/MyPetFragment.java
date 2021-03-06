package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.MyPetAdapter;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.MyPetBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

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
        setText(R.id.tvTitle, R.string.my_pet);
        setViewVisible(R.id.ivRight);
        setImage(R.id.ivRight, R.drawable.add_pet);
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
        int id = view.getId();
        switch (id) {
            case R.id.ivRight:
                gotoPager(AddPetFragment.class,null);


//                String userId=DataManager.getInstance().getMyUserInfo().getUserId();
//                String fosterName = "HZF";
//                String fosterPhone = "18898352847";
//                String fosterSex = "2";
//                String fosterAge = "28";
//                String fosterAddress = "深圳福田";
//                String fosterCompay = "有限公司";
//                String fosterReason = "回老家";
//                String fosterBeginTime = "2018-03-20";
//                String fosterEndTime = "2018-05-13";
//                String fosterCycle = "2";
//                String fosterShuttle = "1";
//                String fosterPetType = "2";
//                String fosterPetVariety = "2";
//                String fosterPetAge = "2";
//                String fosterPetSex = "1";
//                String fosterPetPic = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1670658909,3056786610&fm=27&gp=0.jpg";
//                String feedRequire = "每天帮洗澡";
//                String remake = "没有备注";
//                String immuneTime = "90";
//                String immuneCondition = "3";
//                String immuneNewlyday = "2018-03-16";
//
//                HttpMethods.getInstance().fosterPetSave(userId,fosterName, fosterPhone, fosterSex, fosterAge, fosterAddress,
//                        fosterCompay, fosterReason, fosterBeginTime, fosterEndTime, fosterCycle,
//                        fosterShuttle, fosterPetType, fosterPetVariety, fosterPetAge, fosterPetSex,
//                        fosterPetPic, feedRequire, remake, immuneTime, immuneCondition, immuneNewlyday,
//                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
//                            @Override
//                            public void onNext(ResponseBean bean) {
//                                if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
//                                    showToast(bean.getMsg());
//                                }
//                                //      goBack();
//                            }
//                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }

    private MyPetAdapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new MyPetAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        final UserInfoBean myInfo = DataManager.getInstance().getMyUserInfo();
        HttpMethods.getInstance().findOwnPetList(myInfo.getUserId(), new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ArrayList<MyPetBean> list1=bean.getOwnPetList();
                ArrayList<AdoptPetBean> list2=bean.getTobeAdoptList();
                ArrayList<BaseBean> myPetList=new ArrayList<>();
                if(list1!=null){
                    myPetList.addAll(list1);
                }
                if(list2!=null){
                    myPetList.addAll(list2);
                }
                getAdapter().setDataList(myPetList);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
