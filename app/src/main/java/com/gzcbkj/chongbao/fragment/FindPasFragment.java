package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/1.
 */

public class FindPasFragment extends RegisterFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_password;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.find_pas);
        setText(R.id.tvRight,R.string.submit);
        setViewVisible(R.id.tvRight);
        setViewsOnClickListener(R.id.tvRight,R.id.tvGetVerCode);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvGetVerCode:
                getCode("modify");
                break;
            case R.id.tvRight:
                String phone=getTextById(R.id.etPhone);
                if(TextUtils.isEmpty(phone)){
                    showToast(R.string.please_input_phone);
                    return;
                }
                String verCode=getTextById(R.id.etVerCode);
                if(TextUtils.isEmpty(verCode)){
                    showToast(R.string.please_input_ver_code);
                    return;
                }
                String newPas=getTextById(R.id.etNewPas);
                if(TextUtils.isEmpty(newPas)){
                    showToast(R.string.please_input_new_pas);
                    return;
                }
                String newPas2=getTextById(R.id.etSurePas);
                if(TextUtils.isEmpty(newPas2)){
                    showToast(R.string.please_input_sure_pas);
                    return;
                }
                if(!newPas.equals(newPas2)){
                    showToast(R.string.new_pas_not_equal_sure_pas);
                    return;
                }
                hideKeyBoard();
                HttpMethods.getInstance().forgetPassword(phone, Utils.getMessageDigest(newPas.getBytes()),verCode,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(!TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                goBack();
                            }
                        },getActivity(),(BaseActivity) getActivity()));
                break;
        }
    }
}
