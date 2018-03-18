package com.gzcbkj.chongbao.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
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

public class PetShideGaoshiFragment extends BaseFragment {

    private int mFindorlostType=-1;
    private int mFindorlostVariety=-1;
    private String mFindorlostPetSex="1";
    private ArrayList<File> mPetPictures;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_shide_gaoshi;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.shide_pet_gaoshi);
        setViewsOnClickListener(R.id.tvSubmit,R.id.tvShideTime,R.id.tvShidePetType,R.id.tvShidePetType2,R.id.tvMale,
                R.id.tvFemale,R.id.tvNotKnow);
        resetPhotosView();
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

        ((BaseActivity) getActivity()).showSelectTimeView(year, month, day, 0,System.currentTimeMillis(),new DatePicker.OnDateChangedListener() {
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
                DataManager.getInstance().setObject(null);
                resetPhotosView();
            }else if(objectType== Constants.OBJECT_TYPE_PET_TYPE){
                String text=(String)DataManager.getInstance().getObject();
                String[] strs=text.split("_");
                mFindorlostType=Integer.parseInt(strs[2]);
                mFindorlostVariety=Integer.parseInt(strs[3]);
                setText(R.id.tvShidePetType,strs[0]);
                setText(R.id.tvShidePetType2,strs[1]);
                DataManager.getInstance().setObject(null);
            }
        }
    }

    protected void resetPhotosView() {
        LinearLayout llCarPhotos = fv(R.id.llPhotos);
        llCarPhotos.removeAllViews();
        int size;
        if (getPetPictures().size() == Constants.MAX_UPLOAD_PHOTO_NUM) {
            size = Constants.MAX_UPLOAD_PHOTO_NUM;
        } else {
            size = getPetPictures().size() + 1;
        }
        int row = (size % 3 == 0) ? size / 3 : size / 3 + 1;
        LinearLayout itemView;
        for (int i = 0; i < row; ++i) {
            itemView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_add_pet_photo, null);
            llCarPhotos.addView(itemView);
            int index = i * 3;
            setPhoto((ViewGroup) itemView.getChildAt(0), index < getPetPictures().size() ? getPetPictures().get(index) : null, index, index == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(1), index + 1 < getPetPictures().size() ? getPetPictures().get(index + 1) : null, index + 1, index + 1 == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(2), index + 2 < getPetPictures().size() ? getPetPictures().get(index + 2) : null, index + 2, index + 2 == size - 1);
        }
        if(getPetPictures().size()>1){
            final ScrollView scrollView=fv(R.id.scrollView);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    private void setPhoto(ViewGroup itemView, File photo, int index, boolean isLast) {
        ImageView ivPhoto = (ImageView) itemView.getChildAt(0);
        ImageView ivClose = (ImageView) itemView.getChildAt(1);
        View addPhoto = itemView.getChildAt(2);
        if (photo != null) {
            addPhoto.setVisibility(View.GONE);
            Utils.loadImage(R.drawable.default_1, Uri.fromFile(photo), ivPhoto);
            ivClose.setVisibility(View.VISIBLE);
            ivPhoto.setTag(R.id.tag, index);
            ivClose.setTag(R.id.tag, index);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag(R.id.tag);
                    File file = getPetPictures().remove(pos);
                    if (file!=null && file.exists()) {
                        file.delete();
                    }
                    resetPhotosView();
                }
            });
        } else {
            if (isLast) {
                ivPhoto.setVisibility(View.GONE);
                addPhoto.setVisibility(View.VISIBLE);
                ivClose.setVisibility(View.GONE);
                addPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BaseActivity) getActivity()).showSelectPhotoWindow();
                    }
                });
            } else {
                itemView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void resetSexBtns(){
        TextView tvMale=fv(R.id.tvMale);
        TextView tvFemale=fv(R.id.tvFemale);
        if(mFindorlostPetSex.equals("1")){
            tvMale.setTextColor(Color.WHITE);
            tvFemale.setTextColor(getResources().getColor(R.color.color_33_33_33));
            tvMale.setBackgroundResource(R.drawable.bg_verify_friend);
            tvFemale.setBackgroundResource(0);
        }else{
            tvFemale.setTextColor(Color.WHITE);
            tvMale.setTextColor(getResources().getColor(R.color.color_33_33_33));
            tvFemale.setBackgroundResource(R.drawable.bg_verify_friend);
            tvMale.setBackgroundResource(0);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvShideTime:
                showSelectTime((TextView) view);
                break;
            case R.id.tvShidePetType:
            case R.id.tvShidePetType2:
                gotoPager(PetSelectTypeFragment.class,null);
                break;
            case R.id.tvMale:
                mFindorlostPetSex="1";
                resetSexBtns();
                break;
            case R.id.tvFemale:
                mFindorlostPetSex="2";
                resetSexBtns();
                break;
            case R.id.tvNotKnow:
                break;
            case R.id.tvSubmit:
                final String findorlostTime = getTextById(R.id.tvShideTime);
                if(TextUtils.isEmpty(findorlostTime)){
                    showToast(R.string.please_select_time);
                    return;
                }
                final String findorlostAddress = getTextById(R.id.etShideAddress);
                if(TextUtils.isEmpty(findorlostAddress)){
                    showToast(R.string.please_input_address);
                    return;
                }

                if(mFindorlostType==-1){
                    showToast(R.string.please_select_pet_type);
                    return;
                }

                if(mFindorlostVariety==-1){
                    showToast(R.string.please_select_pet_type_2);
                    return;
                }

                final String findorlostPeopleName = getTextById(R.id.etShidePersonname);
                if(TextUtils.isEmpty(findorlostPeopleName)){
                    showToast(R.string.please_input_name);
                    return;
                }
                final String findorlostPeoplePhone = getTextById(R.id.etContactWay);
                if(TextUtils.isEmpty(findorlostPeopleName)){
                    showToast(R.string.please_input_contact_way);
                    return;
                }
                final String findorlostRemake = getTextById(R.id.etPetMiaoshu);
                if(TextUtils.isEmpty(findorlostPeopleName)){
                    showToast(R.string.add_pet_miaoshu);
                    return;
                }
                if(getPetPictures().isEmpty()){
                    showToast(R.string.please_add_pet_photos);
                    return;
                }
                final String findorlostLat="120.78";
                final String findorlostLng="120.45";
                HttpMethods.getInstance().uploadFile(getPetPictures(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if(responseBean!=null && !TextUtils.isEmpty(responseBean.getMsg())){
                            HttpMethods.getInstance().findorlostInfoSave(getType(), findorlostTime, findorlostAddress, mFindorlostType,
                                    mFindorlostVariety, mFindorlostPetSex, findorlostPeopleName, findorlostPeoplePhone,
                                    findorlostRemake, responseBean.getMsg(), findorlostLat,findorlostLng,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
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

    protected String getType() {
        return "2";
    }
}
