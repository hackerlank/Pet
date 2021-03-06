package com.gzcbkj.chongbao.fragment;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;

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
        setViewsOnClickListener(R.id.ivRight);
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean = DataManager.getInstance().getMyUserInfo();
        if (bean != null) {
            setText(R.id.tvName, bean.getUsername());
            setText(R.id.tvPhone, bean.getMobile());
            Utils.loadImages(R.drawable.touxiang,bean.getHeadPic(),(ImageView)fv(R.id.ivAvater));
            Utils.loadImages(R.drawable.wall,bean.getSpaceImg(),(ImageView)fv(R.id.ivWall));
        } else {
            setText(R.id.tvName, R.string.not_login);
            setText(R.id.tvPhone, R.string.not_login);
            setImage(R.id.ivAvater,R.drawable.touxiang);
            setImage(R.id.ivWall,R.drawable.wall);
        }
    }

    public void onResume(){
        super.onResume();
        if (DataManager.getInstance().getObject() != null) {
            int objectType = DataManager.getInstance().getObjectType();
            if (objectType == Constants.OBJECT_TYPE_AVATER) {
                final File wallFile = new File((String) DataManager.getInstance().getObject());
                DataManager.getInstance().setObject(null);
                ArrayList<File> files = new ArrayList<>();
                files.add(wallFile);
                HttpMethods.getInstance().uploadFile(files, new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(final ResponseBean responseBean) {
                        if (responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            HttpMethods.getInstance().updateUserInfo(null,null, responseBean.getMsg(),
                                    new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                                        @Override
                                        public void onNext(ResponseBean bean) {
                                            UserInfoBean userInfoBean = DataManager.getInstance().getMyUserInfo();
                                            userInfoBean.setSpaceImg(responseBean.getMsg());
                                            DataManager.getInstance().saveMyUserInfo(userInfoBean);
                                            if(getView()==null){
                                                return;
                                            }
                                            if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                                showToast(bean.getMsg());
                                            }
                                            Utils.loadImage(R.drawable.default_1, Uri.fromFile(wallFile), (ImageView) fv(R.id.ivWall));
                                        }
                                    }, getActivity(), (BaseActivity) getActivity()));
                        }
                    }
                }, getActivity(), (BaseActivity) getActivity()));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivWall:
                if (!DataManager.getInstance().isLogin()) {
                    gotoPager(LoginFragment.class, null);
                    return;
                }
                ((BaseActivity) getActivity()).showSelectPhotoWindow();
                break;
            case R.id.ivSetting:
                gotoPager(DataManager.getInstance().isLogin() ? SettingFragment.class : LoginFragment.class, null);
                break;
            case R.id.ivMessage:
                gotoPager(DataManager.getInstance().isLogin() ?MessageCenterFragment.class:LoginFragment.class, null);
                break;
            case R.id.rlProfile:
                gotoPager(DataManager.getInstance().isLogin() ?EditProfileFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl1:
                gotoPager(DataManager.getInstance().isLogin() ?MyDynamicFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl2:
                gotoPager(DataManager.getInstance().isLogin() ?MyPetFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl3:
                gotoPager(DataManager.getInstance().isLogin() ?FriendOther2Fragment.class:LoginFragment.class, null);
                break;
            case R.id.rl4:
                gotoPager(DataManager.getInstance().isLogin() ?MyCollectFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl5:
                gotoPager(DataManager.getInstance().isLogin() ?PetNoticeFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl6:
                gotoPager(DataManager.getInstance().isLogin() ?JilingguanliFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl7:
                gotoPager(DataManager.getInstance().isLogin() ?OrderListFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl8:
                gotoPager(DataManager.getInstance().isLogin() ?MoneyAccountFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl9:
                gotoPager(DataManager.getInstance().isLogin() ?SuggestionFragment.class:LoginFragment.class, null);
                break;
            case R.id.rl10:
                gotoPager(AboutUsFragment.class, null);
                break;
        }
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().queryUserInfo(new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() != null) {
                    refreshlayout.finishRefresh();
                    updateUIText();
                }
                DataManager.getInstance().saveMyUserInfo(bean.getUser());
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
