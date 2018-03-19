package com.gzcbkj.chongbao.fragment;

import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class EditProfileFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.personal_prdfile);
        setViewsOnClickListener(R.id.rlAvater, R.id.rlNickname);
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean = DataManager.getInstance().getMyUserInfo();
        if (bean != null) {
            setText(R.id.tvNick, bean.getUsername());
            Utils.loadImages(R.drawable.touxiang, bean.getHeadPic(), (ImageView) fv(R.id.ivAvater));
        } else {
            setText(R.id.tvNick, "");
            setImage(R.id.ivAvater, R.drawable.touxiang);
        }
    }

    public void onResume() {
        super.onResume();
        if (DataManager.getInstance().getObject() != null) {
            int objectType = DataManager.getInstance().getObjectType();
            if (objectType == Constants.OBJECT_TYPE_AVATER) {
                final File avaterFile = new File((String) DataManager.getInstance().getObject());
                DataManager.getInstance().setObject(null);
                ArrayList<File> files = new ArrayList<>();
                files.add(avaterFile);
                HttpMethods.getInstance().uploadFile(files, new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(final ResponseBean responseBean) {
                        if (responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            HttpMethods.getInstance().updateUserInfo(null, responseBean.getMsg(), null,
                                    new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                                        @Override
                                        public void onNext(ResponseBean bean) {
                                            UserInfoBean userInfoBean = DataManager.getInstance().getMyUserInfo();
                                            userInfoBean.setHeadPic(responseBean.getMsg());
                                            DataManager.getInstance().saveMyUserInfo(userInfoBean);
                                            if (getView() == null) {
                                                return;
                                            }
                                            if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                                showToast(bean.getMsg());
                                            }
                                            Utils.loadImage(R.drawable.touxiang, Uri.fromFile(avaterFile), (ImageView) fv(R.id.ivAvater));
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
            case R.id.rlAvater:
                ((BaseActivity) getActivity()).showSelectPhotoWindow();
                ;
                break;
            case R.id.rlNickname:
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.KEY_BASE_BEAN, Constants.EDIT_USER_NICK);
                bundle.putString(Constants.KEY_BASE_BEAN_2, getTextById(R.id.tvNick));
                gotoPager(EditNicknameFragment.class, bundle);
                break;
        }
    }
}
