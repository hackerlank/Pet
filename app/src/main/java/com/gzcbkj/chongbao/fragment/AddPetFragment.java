package com.gzcbkj.chongbao.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.MyPetBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by huangzhifeng on 2018/3/17.
 */

public class AddPetFragment extends BaseFragment {

    private String mPetAvaterPath;
    private String mPetNick;
    private int mPetType=-1;
    private int mPetVariety=-1;
    private int mPetSex=-1;
    private String mPetWeigth;
    private int mIsPetIsSterilization=-1;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_pet;
    }

    @Override
    protected void onViewCreated(View view) {
        TextView tvRight=fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.save));
        setText(R.id.tvTitle,R.string.add_pet);
        setViewsOnClickListener(R.id.rlAvater,R.id.llNickname,
                R.id.llPetType,R.id.llBirth,R.id.llPetSex,
                R.id.llPetWeight,R.id.llPetIsSterilization,R.id.tvRight);
    }

    public void onResume(){
        super.onResume();
        if(DataManager.getInstance().getObject()!=null){
            int objectType=DataManager.getInstance().getObjectType();
            if(objectType== Constants.OBJECT_TYPE_AVATER){
                mPetAvaterPath=(String)DataManager.getInstance().getObject();
                Utils.loadImage(R.drawable.touxiang, Uri.fromFile(new File(mPetAvaterPath)),(ImageView)fv(R.id.ivAvater));
                DataManager.getInstance().setObject(null);
            }else if(objectType==Constants.OBJECT_TYPE_NICKNAME){
                mPetNick=(String)DataManager.getInstance().getObject();
                setText(R.id.tvNick,mPetNick);
                DataManager.getInstance().setObject(null);
            }else if(objectType==Constants.OBJECT_TYPE_PET_TYPE){
                String text=(String)DataManager.getInstance().getObject();
                String[] strs=text.split("_");
                mPetType=Integer.parseInt(strs[2]);
                mPetVariety=Integer.parseInt(strs[3]);
                setText(R.id.tvPetType,strs[1]);
                DataManager.getInstance().setObject(null);
            }else if(objectType==Constants.OBJECT_TYPE_PET_WEIGHT){
                mPetWeigth=(String)DataManager.getInstance().getObject();
                setText(R.id.tvPetWeight,mPetWeigth+"KG");
                DataManager.getInstance().setObject(null);
            }
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvRight:
                if(TextUtils.isEmpty(mPetAvaterPath)){
                    showToast(R.string.please_seclect_avater);
                    return;
                }
                if(TextUtils.isEmpty(mPetNick)){
                    showToast(R.string.please_input_nickname_2);
                    return;
                }
                if(mPetType==-1 || mPetVariety==-1){
                    showToast(R.string.please_select_pet_type);
                    return;
                }
                final String birth=getTextById(R.id.tvBirth);
                if(TextUtils.isEmpty(birth)){
                    showToast(R.string.please_select_pet_birth);
                    return;
                }

                if(mPetSex==-1){
                    showToast(R.string.please_select_pet_sex);
                    return;
                }

                if(TextUtils.isEmpty(mPetWeigth)){
                    showToast(R.string.your_pet_weight);
                    return;
                }
                if(mIsPetIsSterilization==-1){
                    showToast(R.string.please_seclect_pet_is_sterilization);
                    return;
                }

                final String remark=getTextById(R.id.etJianjie);
                if(TextUtils.isEmpty(mPetWeigth)){
                    showToast(R.string.pet_jianjie_sample);
                    return;
                }
                ArrayList<String> files=new ArrayList<>();
                files.add(mPetAvaterPath);
                HttpMethods.getInstance().uploadFile(files,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if(responseBean!=null && !TextUtils.isEmpty(responseBean.getMsg())){
                                            String ownUserId = DataManager.getInstance().getMyUserInfo().getUserId();
                HttpMethods.getInstance().ownPetSave(responseBean.getMsg(), mPetNick, mPetType, mPetVariety,birth ,
                        String.valueOf(mPetSex), mPetWeigth, String.valueOf(mIsPetIsSterilization), remark, ownUserId,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                    showToast(bean.getMsg());
                                }
                                goBack();
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                        }
                    }
                },getActivity(),(BaseActivity)getActivity()));

                break;
            case R.id.rlAvater:
                ((BaseActivity) getActivity()).showSelectPhotoWindow();
                break;
            case R.id.llNickname:
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.KEY_BASE_BEAN,Constants.EDIT_PET_NICK);
                bundle.putString(Constants.KEY_BASE_BEAN_2,getTextById(R.id.tvNick));
                gotoPager(EditNicknameFragment.class,bundle);
                break;
            case R.id.llPetType:
                gotoPager(PetSelectTypeFragment.class,null);
                break;
            case R.id.llBirth:
                showSelectTime((TextView)fv(R.id.tvBirth));
                break;
            case R.id.llPetSex:
                final MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.setLayout(R.layout.layout_select_gender_dialog);
                dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        dialogFragment.setDialogViewsOnClickListener(view,R.id.tv1,R.id.tv2);
                    }

                    @Override
                    public void onViewClick(int viewId) {
                        switch (viewId){
                            case R.id.tv1:
                                mPetSex=1;
                                setText(R.id.tvPetSex,R.string.male);
                                break;
                            case R.id.tv2:
                                mPetSex=2;
                                setText(R.id.tvPetSex,R.string.female);
                                break;
                        }
                    }
                });
                dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
                break;
            case R.id.llPetWeight:
                bundle=new Bundle();
                bundle.putString(Constants.KEY_BASE_BEAN,getTextById(R.id.tvPetWeight));
                gotoPager(PetWeightFragment.class,bundle);
                break;
            case R.id.llPetIsSterilization:
                final MyDialogFragment dialogFragment1 = new MyDialogFragment();
                dialogFragment1.setLayout(R.layout.layout_select_gender_dialog);
                dialogFragment1.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        dialogFragment1.setDialogViewsOnClickListener(view,R.id.tv1,R.id.tv2);
                        ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.yes));
                        ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.no));
                    }

                    @Override
                    public void onViewClick(int viewId) {
                        switch (viewId){
                            case R.id.tv1:
                                mIsPetIsSterilization=1;
                                setText(R.id.tvPetSterilization,R.string.yes);
                                break;
                            case R.id.tv2:
                                mIsPetIsSterilization=2;
                                setText(R.id.tvPetSterilization,R.string.no);
                                break;
                        }
                    }
                });
                dialogFragment1.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
                break;
        }
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

        ((BaseActivity) getActivity()).showSelectTimeView(year, month, day, 0,System.currentTimeMillis(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                tvTime.setText(y+"-"+Utils.getNewText(m+1)+"-"+Utils.getNewText(d));
            }
        });
    }
}
