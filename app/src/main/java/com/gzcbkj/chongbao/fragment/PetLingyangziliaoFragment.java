package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
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

import okhttp3.internal.Util;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangziliaoFragment extends BaseFragment{

    private AdoptPetBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_lingyang_ziliao;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.input_lingyang_ziliao);
        setViewsOnClickListener(R.id.tvSubmit);
        mBean = (AdoptPetBean) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
        Utils.loadImage(R.drawable.default_1,mBean.getPetHeadUrl(),(ImageView) fv(R.id.ivPetAvater));
        setText(R.id.tvPetName,mBean.getPetName());
        setImage(R.id.ivPetSex,"1".equals(mBean.getPetSex())?R.drawable.male:R.drawable.female);
        setText(R.id.tvPetAge,mBean.getPetAge()+getString(R.string.age));
        setText(R.id.tvPetType,mBean.getPetVarietyNmae());
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvSubmit:
                String userName=getTextById(R.id.etLingyangName);
                if(TextUtils.isEmpty(userName)){
                    showToast(R.string.please_input_your_name);
                    return;
                }
                String phone=getTextById(R.id.etPhone);
                if(TextUtils.isEmpty(phone)){
                    showToast(R.string.please_input_your_phone);
                    return;
                }
                String address=getTextById(R.id.etAddress);
                if(TextUtils.isEmpty(address)){
                    showToast(R.string.please_input_your_address);
                    return;
                }
                String recSendWay=getString(R.string.rec_send_way_1);//getTextById(R.id.tvRecSendWay);
                if(TextUtils.isEmpty(recSendWay)){
                    showToast(R.string.please_select_rec_send_way);
                    return;
                }
                String startTime="2018-03-14";//getTextById(R.id.tvStartTime);
                if(TextUtils.isEmpty(startTime)){
                    showToast(R.string.please_input_start_time);
                    return;
                }
                String endTime="2018-05-01";getTextById(R.id.tvEndTime);
                if(TextUtils.isEmpty(endTime)){
                    showToast(R.string.please_input_end_time);
                    return;
                }
                String lingyangYuanyin=getString(R.string.lingyang_yuanyin_1);//getTextById(R.id.tvLingyangYuanyin);
                if(TextUtils.isEmpty(lingyangYuanyin)){
                    showToast(R.string.please_input_lingyang_yuanying);
                    return;
                }
                final String beizhu=getTextById(R.id.etBeizhu);
                if(TextUtils.isEmpty(beizhu)){
                    showToast(R.string.add_beizhu);
                    return;
                }
                UserInfoBean myInfo= DataManager.getInstance().getMyUserInfo();
                HttpMethods.getInstance().adpotPetSave(userName,address,startTime,endTime,phone,
                        lingyangYuanyin,recSendWay,myInfo.getUserId(),mBean.getId(),beizhu,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(bean!=null && !TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                getActivity().finish();
                            }
                        },getActivity(),(BaseActivity)getActivity()));
                break;
        }
    }
}
