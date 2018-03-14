package com.gzcbkj.chongbao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.JilingguanliAdapter;
import com.gzcbkj.chongbao.adapter.PetLingyangAdapter;
import com.gzcbkj.chongbao.adapter.PetShideAdapter;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.PetFindorlostInfo;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
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
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        ListView listView = fv(R.id.listView);
        if (mItemIndex == PetCenterFragment.PET_LINGYANG_INDEX) {
            listView.setAdapter(getAdapter1());
            getAdapter1().setDataList((ArrayList<AdoptPetBean>) DataManager.getInstance().getDate("pet_lingyang_list",new TypeToken<ArrayList<AdoptPetBean>>(){}.getType()));
            if(getAdapter1().getCount()==0){
                smartRefreshLayout.autoRefresh();
            }
        } else if(mItemIndex == PetCenterFragment.PET_SHIDE_INDEX){
            listView.setAdapter(getAdapter2());
            getAdapter2().setDataList((ArrayList<PetFindorlostInfo>) DataManager.getInstance().getDate("pet_shide_list",new TypeToken<ArrayList<PetFindorlostInfo>>(){}.getType()));
            if(getAdapter2().getCount()==0){
                smartRefreshLayout.autoRefresh();
            }
        }else if(mItemIndex == PetCenterFragment.PET_ZOUSHI_INDEX){
            listView.setAdapter(getAdapter2());
            getAdapter2().setDataList((ArrayList<PetFindorlostInfo>) DataManager.getInstance().getDate("pet_zoushi_list",new TypeToken<ArrayList<PetFindorlostInfo>>(){}.getType()));
            if(getAdapter2().getCount()==0){
                smartRefreshLayout.autoRefresh();
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mItemIndex == PetCenterFragment.PET_LINGYANG_INDEX) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,getAdapter1().getItem(i));
                    gotoPager(PetLingyangDetailFragment.class, bundle);
                } else if (mItemIndex == PetCenterFragment.PET_SHIDE_INDEX) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,getAdapter2().getItem(i));
                    bundle.putInt(Constants.KEY_BASE_BEAN_2,2);
                    gotoPager(PetDetailFragment.class, bundle);
                } else if (mItemIndex == PetCenterFragment.PET_ZOUSHI_INDEX) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.KEY_BASE_BEAN,getAdapter2().getItem(i));
                    bundle.putInt(Constants.KEY_BASE_BEAN_2,1);
                    gotoPager(PetDetailFragment.class, bundle);
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
    public void onRefresh(final RefreshLayout refreshlayout) {
        ProgressSubscriber subscriber = new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                if (bean == null) {
                    return;
                }
                if (mItemIndex == PetCenterFragment.PET_LINGYANG_INDEX) {
                    DataManager.getInstance().saveData("pet_lingyang_list",bean.getTobeAdoptList());
                    getAdapter1().setDataList(bean.getTobeAdoptList());
                } else if (mItemIndex == PetCenterFragment.PET_SHIDE_INDEX) {
                    DataManager.getInstance().saveData("pet_shide_list",bean.getFindorlostInfoList());
                    getAdapter2().setDataList(bean.getFindorlostInfoList());
                } else if (mItemIndex == PetCenterFragment.PET_ZOUSHI_INDEX) {
                    DataManager.getInstance().saveData("pet_zoushi_list",bean.getFindorlostInfoList());
                    getAdapter2().setDataList(bean.getFindorlostInfoList());
                }
            }
        }, getActivity(), false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).connectError(e);
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).serverError(errorCode, errorMsg);
            }
        });
        if (mItemIndex == PetCenterFragment.PET_LINGYANG_INDEX) {
            HttpMethods.getInstance().tobeAdoptList(1, 30, subscriber);
        } else if (mItemIndex == PetCenterFragment.PET_SHIDE_INDEX) {
            HttpMethods.getInstance().findorlostInfoList(1,30,"2",subscriber);
            //HttpMethods.getInstance().queryTypeInfoList(subscriber);
        } else if (mItemIndex == PetCenterFragment.PET_ZOUSHI_INDEX) {
            HttpMethods.getInstance().findorlostInfoList(1,30,"1",subscriber);
        }
    }
}
