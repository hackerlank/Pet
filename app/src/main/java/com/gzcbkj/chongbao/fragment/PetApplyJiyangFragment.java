package com.gzcbkj.chongbao.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.AddPetJiyangPhotoAdapter;
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
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetApplyJiyangFragment extends BaseFragment {

    private int mPetType=-1;
    private int mPetVariety=-1;
    private int mPetSex=-1;
    private int mMianyiQingkuang=-1;  //免疫情况(1:是   2:否   3:未知)
    private ArrayList<File> mPetPictures;

    private AddPetJiyangPhotoAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_apply_jiyang;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.pet_jiyang);
        setViewsOnClickListener(R.id.tvSubmit,R.id.tvRecSendWay,R.id.tvStartTime,R.id.tvEndTime,R.id.tvjiyangYuanyin,
                R.id.tvSelectPetType,R.id.tvSelectPetType2,R.id.tvSelectPetSex,R.id.tvMianyiQiangkuang,
                R.id.tvLastMianyishijian);
        RecyclerView recyclerView = fv(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setAdapter(getAdapter());
        getAdapter().setData(getPetPictures());
    }

    public ArrayList<File> getPetPictures(){
        if(mPetPictures==null){
            mPetPictures=new ArrayList<>();
        }
        return mPetPictures;
    }

    @Override
    public void updateUIText() {

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

        ((BaseActivity) getActivity()).showSelectTimeView(year, month, day, 0,0,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                tvTime.setText(y+"-"+Utils.getNewText(m+1)+"-"+Utils.getNewText(d));
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (DataManager.getInstance().getObject() != null) {
            int objectType = DataManager.getInstance().getObjectType();
            if(DataManager.getInstance().getObject() instanceof Bitmap){
                String fileStr=Utils.saveJpeg((Bitmap)DataManager.getInstance().getObject(),getActivity());
                getPetPictures().add(new File(fileStr));
                getAdapter().notifyDataSetChanged();
                final RecyclerView recyclerView = fv(R.id.recyclerView);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(getAdapter().getItemCount()-1);
                    }
                });
                DataManager.getInstance().setObject(null);
            }else if(objectType== Constants.OBJECT_TYPE_PET_TYPE){
                String text=(String)DataManager.getInstance().getObject();
                String[] strs=text.split("_");
                mPetType=Integer.parseInt(strs[2]);
                mPetVariety=Integer.parseInt(strs[3]);
                setText(R.id.tvSelectPetType,strs[0]);
                setText(R.id.tvSelectPetType2,strs[1]);
                DataManager.getInstance().setObject(null);
            }
        }
    }

    private void initSelect(final int selectType){
        final LinearLayout llSelect=fv(R.id.llSelect);
        llSelect.setVisibility(View.VISIBLE);
        int childCount=llSelect.getChildCount();
        TextView tv;
        String text;
        String currentText;
        if(selectType==1) {
            currentText=getTextById(R.id.tvjiyangYuanyin);
        }else{
            currentText=getTextById(R.id.tvRecSendWay);
        }
        for(int i=0;i<childCount;++i){
            tv= (TextView) llSelect.getChildAt(i);
            text=getString(getResources().getIdentifier(selectType==1?("jiyang_yuanyin_"+(i+1)):("rec_send_way_"+(i+1)),"string",getActivity().getPackageName()));
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
                        setText(R.id.tvjiyangYuanyin, (String) view.getTag());
                    }else{
                        setText(R.id.tvRecSendWay, (String) view.getTag());
                    }
                    llSelect.setVisibility(View.GONE);
                }
            });
        }
        RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams) llSelect.getLayoutParams();
        lp.topMargin=-(selectType==1?2:5)*Utils.dip2px(getActivity(),40);
        getView().setOnClickListener(this);
    }

    private AddPetJiyangPhotoAdapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new AddPetJiyangPhotoAdapter(getActivity());
        return mAdapter;
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvRecSendWay:
                initSelect(2);
                break;
            case R.id.tvjiyangYuanyin:
                initSelect(1);
                break;
            case R.id.tvStartTime:
            case R.id.tvEndTime:
            case R.id.tvLastMianyishijian:
                showSelectTime((TextView) view);
                break;
            case R.id.tvSelectPetType:
            case R.id.tvSelectPetType2:
                gotoPager(PetSelectTypeFragment.class,null);
                break;
            case R.id.tvSelectPetSex:
                final MyDialogFragment sexDialogFragment = new MyDialogFragment();
                sexDialogFragment.setLayout(R.layout.layout_select_dialog);
                sexDialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        sexDialogFragment.setDialogViewsOnClickListener(view,R.id.tv1,R.id.tv2);
                    }

                    @Override
                    public void onViewClick(int viewId) {
                        switch (viewId){
                            case R.id.tv1:
                                mPetSex=1;
                                setText(R.id.tvSelectPetSex,R.string.male);
                                break;
                            case R.id.tv2:
                                mPetSex=2;
                                setText(R.id.tvSelectPetSex,R.string.female);
                                break;
                        }
                    }
                });
                sexDialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
                break;
            case R.id.tvMianyiQiangkuang:
                final MyDialogFragment miaoyiDialogFragment = new MyDialogFragment();
                miaoyiDialogFragment.setLayout(R.layout.layout_select_dialog);
                miaoyiDialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        miaoyiDialogFragment.setDialogViewsOnClickListener(view,R.id.tv1,R.id.tv2,R.id.tv3);
                        ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.had_mianyi));
                        ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.not_mianyi));
                        ((TextView) view.findViewById(R.id.tv3)).setText(getString(R.string.weizhi));
                        view.findViewById(R.id.line3).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onViewClick(int viewId) {
                        switch (viewId){
                            case R.id.tv1:
                                mMianyiQingkuang=1;
                                setText(R.id.tvMianyiQiangkuang,R.string.had_mianyi);
                                break;
                            case R.id.tv2:
                                mMianyiQingkuang=2;
                                setText(R.id.tvMianyiQiangkuang,R.string.not_mianyi);
                                break;
                            case R.id.tv3:
                                mMianyiQingkuang=3;
                                setText(R.id.tvMianyiQiangkuang,R.string.weizhi);
                                break;
                        }
                    }
                });
                miaoyiDialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
                break;
            case R.id.tvSubmit:
                final String name = getTextById(R.id.etJiyangName);
                if(TextUtils.isEmpty(name)){
                    showToast(R.string.please_input_your_name);
                    return;
                }
                final String phone = getTextById(R.id.etPhone);
                if(TextUtils.isEmpty(phone)){
                    showToast(R.string.please_input_your_phone);
                    return;
                }
                final String address = getTextById(R.id.etAddress);
                if(TextUtils.isEmpty(address)){
                    showToast(R.string.please_input_your_address);
                    return;
                }

                final String recSendWay = getTextById(R.id.tvRecSendWay);
                if(TextUtils.isEmpty(recSendWay)){
                    showToast(R.string.please_select_rec_send_way);
                    return;
                }
                final String startTime = getTextById(R.id.tvStartTime);
                if(TextUtils.isEmpty(startTime)){
                    showToast(R.string.please_input_start_time);
                    return;
                }
                final String endTime = getTextById(R.id.tvEndTime);
                if(TextUtils.isEmpty(endTime)){
                    showToast(R.string.please_input_end_time);
                    return;
                }
                if(endTime.compareTo(startTime)<=0){
                    showToast(R.string.end_time_must_much_thaan_start_time);
                    return;
                }
                final String jyyuanyin = getTextById(R.id.tvjiyangYuanyin);
                if(TextUtils.isEmpty(jyyuanyin)){
                    showToast(R.string.please_select_jiyang_reason);
                    return;
                }
                if(mPetType==-1){
                    showToast(R.string.please_select_pet_type);
                    return;
                }
                if(mPetVariety==-1){
                    showToast(R.string.please_select_pet_type_2);
                    return;
                }

                final String petAge = getTextById(R.id.etPetAge);
                if(TextUtils.isEmpty(petAge)){
                    showToast(R.string.please_input_pet_age);
                    return;
                }
                if(mPetSex==-1){
                    showToast(R.string.please_select_pet_sex);
                    return;
                }
                if(getPetPictures().isEmpty()){
                    showToast(R.string.please_add_pet_photos);
                    return;
                }


                final String weiyangYaoqiu = getTextById(R.id.etWeiyangyaoqiu);
                if(TextUtils.isEmpty(weiyangYaoqiu)){
                    showToast(R.string.please_input_weiyang_yaoqiu);
                    return;
                }

                if(mMianyiQingkuang==-1){
                    showToast(R.string.please_select_mianyi_qingkuang);
                    return;
                }
                final String mianyishijian = getTextById(R.id.etMianyishijian);
                if(TextUtils.isEmpty(mianyishijian)){
                    showToast(R.string.please_input_mianyi_shijian);
                    return;
                }
                final String lastMianyishijian = getTextById(R.id.tvLastMianyishijian);
                if(TextUtils.isEmpty(lastMianyishijian)){
                    showToast(R.string.please_input_last_mianyi_shijian);
                    return;
                }

                final String beizhu = getTextById(R.id.etBeizhu);
                if(TextUtils.isEmpty(beizhu)){
                    showToast(R.string.add_beizhu);
                    return;
                }

                final String fosterCompay="XXX有限公司";
                final String fosterCycle="1";  //寄养周期(1:短期   2:长期)
                final String fosterAge="28";
                final String fosterSex="1";
                HttpMethods.getInstance().uploadFile(getPetPictures(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if(responseBean!=null && !TextUtils.isEmpty(responseBean.getMsg())){
                            String userId=DataManager.getInstance().getMyUserInfo().getUserId();
                HttpMethods.getInstance().fosterPetSave(userId,name, phone, fosterSex, fosterAge, address,
                        fosterCompay, jyyuanyin, startTime, endTime, fosterCycle,
                        recSendWay, String.valueOf(mPetType), String.valueOf(mPetVariety), petAge, String.valueOf(mPetSex),
                        responseBean.getMsg(), weiyangYaoqiu, beizhu, mianyishijian, String.valueOf(mMianyiQingkuang), lastMianyishijian,
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
        }
    }
}
