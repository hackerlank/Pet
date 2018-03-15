package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetShideGaoshiFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_shide_gaoshi;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.shide_pet_gaoshi);
        setViewsOnClickListener(R.id.tvSubmit);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvSubmit:
                String findorlostTime = "2018-03-15";
                String findorlostAddress = "深圳福田";
                int findorlostType = 1;
       //         String typeName = "狗";
                int findorlostVariety = 1;
       //         String petVarietyName = "土狗";
                String findorlostPetSex = "1";
                String findorlostPeopleName = "HZF";
                String findorlostPeoplePhone = "18898352847";
                String findorlostRemake = "宠物丢失拾得公告描述";
                String findorlostLmg = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1670658909,3056786610&fm=27&gp=0.jpg";
                String findorlostLat="120.78";
                String findorlostLng="120.45";
                HttpMethods.getInstance().findorlostInfoSave(getType(), findorlostTime, findorlostAddress, findorlostType,
                        findorlostVariety, findorlostPetSex, findorlostPeopleName, findorlostPeoplePhone,
                        findorlostRemake, findorlostLmg, findorlostLat,findorlostLng,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                    showToast(bean.getMsg());
                                }
                                goBack();
                            }
                        }, getActivity(), (BaseActivity) getActivity()));

                break;
        }
    }

    protected String getType() {
        return "2";
    }
}
