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
import com.gzcbkj.chongbao.adapter.GuangchangAdapter;
import com.gzcbkj.chongbao.adapter.PetGroupFriendAdapter;
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

public class GroupFriendItemFragment extends BaseFragment implements OnRefreshListener {

    private PetGroupFriendAdapter mAdapter1;
    private GuangchangAdapter mAdapter2;
    private View mTopView;
    private int mIndex;

    public GroupFriendItemFragment setIndex(int index) {
        mIndex = index;
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_friend_item;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        ArrayList<SayBean> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add(new SayBean());
            ArrayList<SayBean.SayImg> imgs = new ArrayList<>();
            list.get(i).setSayImgList(imgs);
            for (int j = 0; j <= i; ++j) {
                imgs.add(new SayBean.SayImg());
            }
        }
        if (mIndex == 0) {
            mTopView = LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_top_layout, null);
            listView.addHeaderView(mTopView);
            initTopView(DataManager.getInstance().getMyUserInfo());
            listView.setAdapter(getAdapter1());
            getAdapter1().setDataList(list);
        }else{
            listView.setAdapter(getAdapter2());
            setViewVisible(R.id.view1);
            getAdapter2().setDataList(list);
        }
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle=new Bundle();
                    if(mIndex==0) {
                        bundle.putSerializable(Constants.KEY_BASE_BEAN, getAdapter1().getItem(i - 1));
                    }else{
                        bundle.putSerializable(Constants.KEY_BASE_BEAN, getAdapter1().getItem(i));
                    }
                    gotoPager(DynamicDetailFragment.class, bundle);
            }
        });
    }

    private void initTopView(UserInfoBean userInfo) {
        Utils.loadImages(R.drawable.default_1, userInfo.getSpaceImg(), (ImageView) mTopView.findViewById(R.id.ivBg));
        Utils.loadImages(R.drawable.touxiang, userInfo.getHeadPic(), (ImageView) mTopView.findViewById(R.id.ivAvater));
        ((TextView) mTopView.findViewById(R.id.tvName)).setText(userInfo.getUsername());
        mTopView.findViewById(R.id.tvNewMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPager(PetGroupMessageFragment.class, null);
            }
        });
    }


    private PetGroupFriendAdapter getAdapter1() {
        if (mAdapter1 == null)
            mAdapter1 = new PetGroupFriendAdapter(getActivity());
        return mAdapter1;
    }

    private GuangchangAdapter getAdapter2() {
        if (mAdapter2 == null)
            mAdapter2 = new GuangchangAdapter(getActivity());
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
        if (mIndex == 0) {
            HttpMethods.getInstance().querySpace(new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                @Override
                public void onNext(ResponseBean bean) {
                    if (getView() == null) {
                        return;
                    }
                    if (bean != null) {
                        initTopView(bean.getUserEntity());
                    }
                }
            }, getActivity(), false, (BaseActivity) getActivity()));
        }

    }

    public void onDestroyView() {
        super.onDestroyView();
    }

}
