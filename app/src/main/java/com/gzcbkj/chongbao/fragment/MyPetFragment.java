package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.MyPetAdapter;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
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

    private int index = 1;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivRight:
//                String ownPetHeadurl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1670658909,3056786610&fm=27&gp=0.jpg";
//                String ownPetName = index % 2 == 0 ? "多多" : "西西";
//                int ownPetType = 1;
//                int ownPetVariety = 1;
//                String ownPetBirth = "2016-08-12";
//                String ownPetSex = index % 2 == 0 ? "1" : "2";
//                String ownPetWeight = "10";
//                String ownPetSterilization = index % 2 == 0 ? "1" : "2";
//                String ownPetInterest = index % 2 == 0 ? "凶猛异常" : "温顺可爱";
//                ++index;
//                String ownUserId = DataManager.getInstance().getMyUserInfo().getUserId();
//                HttpMethods.getInstance().ownPetSave(ownPetHeadurl, ownPetName, ownPetType, ownPetVariety, ownPetBirth,
//                        ownPetSex, ownPetWeight, ownPetSterilization, ownPetInterest, ownUserId,
//                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
//                            @Override
//                            public void onNext(ResponseBean bean) {
//                                if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
//                                    showToast(bean.getMsg());
//                                }
//                          //      goBack();
//                            }
//                        }, getActivity(), (BaseActivity) getActivity()));


//                String fosterName = "HZF";
//                String fosterPhone = "18898352847";
//                String fosterSex = "1";
//                String fosterAge = "28";
//                String fosterAddress = "深圳福田";
//                String fosterCompay = "有限公司";
//                String fosterReason = "回老家";
//                String fosterBeginTime = "2018-03-15";
//                String fosterEndTime = "2018-04-13";
//                String fosterCycle = "2";
//                String fosterShuttle = "1";
//                String fosterPetType = "1";
//                String fosterPetVariety = "1";
//                String fosterPetAge = "2";
//                String fosterPetSex = "2";
//                String fosterPetPic = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1670658909,3056786610&fm=27&gp=0.jpg";
//                String feedRequire = "每天帮洗澡";
//                String remake = "没有备注";
//                String immuneTime = "90";
//                String immuneCondition = "3";
//                String immuneNewlyday = "2018-03-15";
//
//                HttpMethods.getInstance().fosterPetSave(fosterName, fosterPhone, fosterSex, fosterAge, fosterAddress,
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
                ArrayList<AdoptPetBean> list=bean.getTobeAdoptList();
                ArrayList<MyPetBean> myPetList=bean.getOwnPetList();
                if(myPetList==null){
                    myPetList=new ArrayList<>();
                }
                if(list!=null && !list.isEmpty()){
                    for(AdoptPetBean adoptPetBean:list){
                        MyPetBean myPetBean=new MyPetBean();
                        myPetBean.setCreatetime(adoptPetBean.getCreatetime());
                        myPetBean.setOwnPetName(adoptPetBean.getPetName());
                        myPetBean.setTypeName(adoptPetBean.getPetTypeName());
                        myPetBean.setPetVarietyName(adoptPetBean.getPetVarietyName());
                      //  myPetBean.setO
                        myPetList.add(myPetBean);
                    }
                }
                getAdapter().setDataList(myPetList);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
