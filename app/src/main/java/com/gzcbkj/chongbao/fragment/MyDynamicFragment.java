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
import com.gzcbkj.chongbao.bean.PublishBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
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

public class MyDynamicFragment extends BaseFragment implements OnRefreshListener, IDataChangeListener {

    private DynamicAdapter mAdapter;
    private View mTopView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_dynamic;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewVisible(R.id.ivRight);
        setImage(R.id.ivRight, R.drawable.more);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        mTopView = LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_top_layout, null);
        listView.addHeaderView(mTopView);
        initTopView(DataManager.getInstance().getMyUserInfo());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        ArrayList<PublishBean> list = (ArrayList<PublishBean>) DataManager.getInstance().getDate("publishList", new TypeToken<ArrayList<PublishBean>>() {
        }.getType());
        getAdapter().setDataList(list);
        if (list == null || list.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    gotoPager(PublishFrigment.class, null);
                } else if (i > 1) {
                    Bundle bundle = new Bundle();
                    PublishBean bean = getAdapter().getItem(i - 2);
                    if (bean.getUserSay() != null) {
                        bundle.putSerializable(Constants.KEY_BASE_BEAN, bean.getUserSay());
                        gotoPager(DynamicDetailFragment.class, bundle);
                    } else if (bean.getArticle() != null) {
                        bundle.putSerializable(Constants.KEY_BASE_BEAN, bean.getArticle());
                        gotoPager(ArticleDetailFragment.class, bundle);
                    }
                }
            }
        });
        DataManager.getInstance().addDataChangeListener(this);
    }

    private void initTopView(UserInfoBean userInfo) {
        Utils.loadImages(R.drawable.default_1, userInfo.getSpaceImg(), (ImageView) mTopView.findViewById(R.id.ivBg));
        Utils.loadImages(R.drawable.touxiang, userInfo.getHeadPic(), (ImageView) mTopView.findViewById(R.id.ivAvater));
        ((TextView) mTopView.findViewById(R.id.tvName)).setText(userInfo.getUsername());
    }

    private DynamicAdapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new DynamicAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean = DataManager.getInstance().getMyUserInfo();
        setText(R.id.tvTitle, bean.getUsername());
        setText(R.id.tvName, bean.getUsername());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
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

        HttpMethods.getInstance().querySayList(1, Constants.PAGE_COUNT, 1, new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                if (bean != null) {
                    DataManager.getInstance().saveData("publishList", bean.getPublishList());
                    getAdapter().setDataList(bean.getPublishList());
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
                if (errorCode == 300) {
                    DataManager.getInstance().saveData("publishList", null);
                    getAdapter().setDataList(null);
                }
                ((BaseActivity) getActivity()).serverError(errorCode, errorMsg);
            }
        }));
    }

    public void onDestroyView() {
        super.onDestroyView();
        DataManager.getInstance().removeDataChangeListener(this);
    }

    @Override
    public void needRefrsh() {
        if (getView() != null) {
            ((SmartRefreshLayout) fv(R.id.smartLayout)).autoRefresh();
        }
    }
}
