package com.gzcbkj.chongbao.fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.activity.HomeActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/1.
 */

public class LoginFragment extends BaseFragment {

    private boolean mIsPasCanSee;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRegister,R.id.ivQQ,R.id.ivWeChat,R.id.ivWeibo,
                R.id.tvRegister,R.id.tvForgetPas,R.id.tvLogin,R.id.ivCanVisible);
        mIsPasCanSee=false;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivQQ:
                break;
            case R.id.ivWeChat:
                break;
            case R.id.ivWeibo:
                break;
            case R.id.ivCanVisible:
                mIsPasCanSee=!mIsPasCanSee;
                if(mIsPasCanSee){
                    ((EditText)fv(R.id.etPassword)).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    ((EditText)fv(R.id.etPassword)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.tvRegister:
                gotoPager(RegisterFragment.class,null);
                break;
            case R.id.tvForgetPas:
                gotoPager(FindPasFragment.class,null);
                break;
            case R.id.tvLogin:
                String phone=getTextById(R.id.etPhone);
                if(TextUtils.isEmpty(phone)){
                    showToast(R.string.please_input_phone_number);
                    return;
                }
                String password=getTextById(R.id.etPassword);
                if(TextUtils.isEmpty(password)){
                    showToast(R.string.please_input_pas);
                    return;
                }
                hideKeyBoard();
                HttpMethods.getInstance().login(phone, Utils.getMessageDigest(password.getBytes()),
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(!TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                DataManager.getInstance().saveMyUserInfo(bean.getUser());
                                gotoPager(HomeActivity.class,null);
                                getActivity().finish();
                            }
                        },getActivity(),(BaseActivity) getActivity()));
                break;
        }
    }
}
