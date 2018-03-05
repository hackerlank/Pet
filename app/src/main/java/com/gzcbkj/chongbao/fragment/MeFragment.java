package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.BaseBean;
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

public class MeFragment extends BaseFragment implements OnRefreshListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.ivWall, R.id.ivSetting, R.id.ivMessage, R.id.rlProfile,
                R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6, R.id.rl7, R.id.rl8, R.id.rl9, R.id.rl10);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
        setViewsOnClickListener(R.id.ivRight);
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean = DataManager.getInstance().getMyUserInfo();
        if (bean != null) {
            setText(R.id.tvName, bean.getUsername());
            setText(R.id.tvPhone, bean.getMobile());
        } else {
            setText(R.id.tvName, "");
            setText(R.id.tvPhone, "");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivWall:
                break;
            case R.id.ivSetting:
                gotoPager(SettingFragment.class, null);
                break;
            case R.id.ivMessage:
                gotoPager(MessageCenterFragment.class, null);
                break;
            case R.id.rlProfile:
                gotoPager(EditProfileFragment.class, null);
                break;
            case R.id.rl1:
                gotoPager(MyDynamicFragment.class, null);
                break;
            case R.id.rl2:
                gotoPager(MyPetFragment.class, null);
                break;
            case R.id.rl3:
                gotoPager(FriendOtherFragment.class, null);
                break;
            case R.id.rl4:
                gotoPager(MyCollectFragment.class, null);
                break;
            case R.id.rl5:
                gotoPager(PetNoticeFragment.class, null);
                break;
            case R.id.rl6:
                gotoPager(JilingguanliFragment.class, null);
                break;
            case R.id.rl7:
                gotoPager(OrderListFragment.class, null);
                break;
            case R.id.rl8:
                gotoPager(MoneyAccountFragment.class, null);
                break;
            case R.id.rl9:
                gotoPager(SuggestionFragment.class, null);
                break;
            case R.id.rl10:
                gotoPager(AboutUsFragment.class, null);
                break;
        }
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().queryUserInfo(new ProgressSubscriber(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean bean) {
                if (getView() != null) {
                    refreshlayout.finishRefresh();
                }
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
