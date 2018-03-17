package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.text.ParseException;
import java.util.Calendar;

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
        setViewsOnClickListener(R.id.tvSubmit,R.id.tvRecSendWay,R.id.tvStartTime,R.id.tvEndTime,R.id.tvLingyangYuanyin);
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

    private void initSelect(final int selectType){
        final LinearLayout llSelect=fv(R.id.llSelect);
        llSelect.setVisibility(View.VISIBLE);
        int childCount=llSelect.getChildCount();
        TextView tv;
        String text;
        String currentText=getTextById(R.id.tvLingyangYuanyin);
        for(int i=0;i<childCount;++i){
            tv= (TextView) llSelect.getChildAt(i);
            text=getString(getResources().getIdentifier(selectType==1?("lingyang_yuanyin_"+(i+1)):("rec_send_way_"+(i+1)),"string",getActivity().getPackageName()));
            if((TextUtils.isEmpty(currentText) && i==0) || currentText.equals(text)){
                tv.setBackgroundColor(getResources().getColor(R.color.color_ff_73_73));
                tv.setTextColor(Color.WHITE);
            }else{
                tv.setBackgroundColor(Color.TRANSPARENT);
                tv.setTextColor(getResources().getColor(R.color.color_33_33_33));
            }
            tv.setText(text);
            tv.setTag(text);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectType==1) {
                        setText(R.id.tvLingyangYuanyin, (String) view.getTag());
                    }else{
                        setText(R.id.tvRecSendWay, (String) view.getTag());
                    }
                    llSelect.setVisibility(View.GONE);
                }
            });
        }
        RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams) llSelect.getLayoutParams();
        lp.topMargin=selectType==1?0:-Utils.dip2px(getActivity(),80);
        getView().setOnClickListener(this);
    }

    private void showSelectTime(final TextView tvTime){
        String time=tvTime.getText().toString();
        Calendar calendar=Calendar.getInstance();
        if(!TextUtils.isEmpty(time)){
            try {
                calendar.setTime(Utils.stringToDate(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        ((BaseActivity) getActivity()).showSelectTimeView(year, month, day, calendar.getTimeInMillis(),0, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                tvTime.setText(y+"-"+Utils.getNewText(m+1)+"-"+Utils.getNewText(d));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(fv(R.id.llSelect).getVisibility()==View.VISIBLE){
            setViewGone(R.id.llSelect);
            return;
        }
        int id = view.getId();
        switch (id) {
            case R.id.tvRecSendWay:
                initSelect(2);
                break;
            case R.id.tvStartTime:
            case R.id.tvEndTime:
                showSelectTime((TextView) view);
                break;
            case R.id.tvLingyangYuanyin:
                initSelect(1);
                break;
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
                String recSendWay=getTextById(R.id.tvRecSendWay);
                if(TextUtils.isEmpty(recSendWay)){
                    showToast(R.string.please_select_rec_send_way);
                    return;
                }
                String startTime=getTextById(R.id.tvStartTime);
                if(TextUtils.isEmpty(startTime)){
                    showToast(R.string.please_input_start_time);
                    return;
                }
                String endTime=getTextById(R.id.tvEndTime);
                if(TextUtils.isEmpty(endTime)){
                    showToast(R.string.please_input_end_time);
                    return;
                }
                if(endTime.compareTo(startTime)<=0){
                    showToast(R.string.end_time_must_much_thaan_start_time);
                    return;
                }
                String lingyangYuanyin=getTextById(R.id.tvLingyangYuanyin);
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
