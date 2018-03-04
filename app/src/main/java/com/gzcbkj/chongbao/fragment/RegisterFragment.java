package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.BasicResponse;
import com.gzcbkj.chongbao.bean.ValiCodeBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * Created by huangzhifeng on 2018/3/1.
 */

public class RegisterFragment extends BaseFragment {

    private Timer mTimer;
    private int mTotalTime;
    private TimerTask mTask;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvAgreeRegister,R.id.tvPetPrivacy,R.id.tvGetVerCode);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id) {
            case R.id.tvGetVerCode:
                getCode("register");
                break;
            case R.id.tvAgreeRegister:
                String phone=getTextById(R.id.etPhone);
                if(TextUtils.isEmpty(phone)){
                    showToast(R.string.please_input_phone_number);
                    return;
                }
                if(!Utils.isPhoneCorrect(phone)){
                    showToast(R.string.please_input_correct_phone);
                    return;
                }
                String verCode=getTextById(R.id.etVerCode);
                if(TextUtils.isEmpty(verCode)){
                    showToast(R.string.please_input_ver_code);
                    return;
                }
                String nick=getTextById(R.id.etNickName);
                if(TextUtils.isEmpty(nick)){
                    showToast(R.string.please_input_nickname);
                    return;
                }
                String password=getTextById(R.id.etPassword);
                if(TextUtils.isEmpty(password)){
                    showToast(R.string.please_input_pas);
                    return;
                }
                hideKeyBoard();
                HttpMethods.getInstance().register(phone,verCode,nick,Utils.getMessageDigest(password.getBytes()),"广东","深圳",
                        new ProgressSubscriber(new SubscriberOnNextListener<BaseBean>() {
                    @Override
                    public void onNext(BaseBean bean) {
                        if(!TextUtils.isEmpty(bean.getMsg())){
                            showToast(bean.getMsg());
                        }
                        goBack();
                    }
                },getActivity(),(BaseActivity) getActivity()));
                break;
            case R.id.tvPetPrivacy:
                break;
        }
    }

    protected void getCode(String type) {
        String phone=getTextById(R.id.etPhone);
        if(TextUtils.isEmpty(phone)){
            showToast(R.string.please_input_phone);
            return;
        }
        if(!Utils.isPhoneCorrect(phone)){
            showToast(R.string.please_input_correct_phone);
            return;
        }
        mTotalTime = 60;
        mTimer = new Timer();
        initTimerTask(type);
        mTimer.schedule(mTask, 1000, 1000);
        TextView tvGetcode=fv(R.id.tvGetVerCode);
        tvGetcode.setEnabled(false);
        tvGetcode.setText(getString(R.string.resend_code,String.valueOf(mTotalTime)));
        if(type.equals("register")) {
            tvGetcode.setTextColor(getResources().getColor(R.color.color_ff_73_73));
        }
        HttpMethods.getInstance().queryValiCode(phone,type,new ProgressSubscriber(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean bean) {
                if(!TextUtils.isEmpty(bean.getMsg())){
                    showToast(bean.getMsg());
                }
            }
        },getActivity(),false,(BaseActivity) getActivity()));
    }

    private void initTimerTask(final String type) {
        mTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(getView()==null){
                            return;
                        }
                        --mTotalTime;
                        TextView tvGetcode=fv(R.id.tvGetVerCode);
                        if (mTotalTime <= 0) {
                            tvGetcode.setEnabled(true);
                            mTimer.cancel();
                            mTimer = null;
                            tvGetcode.setText(getString(R.string.get_ver_code));
                            if(type.equals("register")) {
                                tvGetcode.setTextColor(getResources().getColor(R.color.color_255_255_255));
                            }
                        } else {
                            tvGetcode.setText(getString(R.string.resend_code,String.valueOf(mTotalTime)));
                            if(type.equals("register")) {
                                tvGetcode.setTextColor(getResources().getColor(R.color.color_ff_73_73));
                            }
                        }
                    }
                });
            }
        };
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
    }
}
