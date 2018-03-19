package com.gzcbkj.chongbao.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.manager.IDataChangeListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishPictureFrigment extends BaseFragment{

    private ArrayList<UploadPhoto> mUploadPhotos;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_picture;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.publish_picture);
        TextView tvRight = fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        setText(tvRight, getString(R.string.publish));
        tvRight.setBackgroundResource(R.drawable.publish_bg);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setTextColor(Color.WHITE);
        tvRight.setOnClickListener(this);
        resetPhotosView();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvRight:
                if(getUploadPhotos().isEmpty()){
                    showToast(R.string.please_select_photos);
                    return;
                }
                final ArrayList<HashMap<String,String>> list=new ArrayList<>();
                for(UploadPhoto photo:mUploadPhotos){
                    HashMap<String,String> sayImg=new HashMap<>();
                    sayImg.put("imgUrl",photo.mUrl);
                    list.add(sayImg);
                }
                String content=getTextById(R.id.etAddDes);
                HttpMethods.getInstance().saveSayDetail(content,list,"1",0.0,0.0,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                ArrayList<IDataChangeListener> listeners=DataManager.getInstance().getDataChangeListener();
                                for(IDataChangeListener listener:listeners){
                                    if(listener instanceof MyDynamicFragment){
                                        listener.needRefrsh();
                                        break;
                                    }
                                }
                                if(getView()==null){
                                    return;
                                }
                                if(bean!=null && !TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                getUploadPhotos().clear();
                                goBack();
                            }
                        },getActivity(),(BaseActivity) getActivity()));
                break;
        }
    }

    private void resetPhotosView() {
        LinearLayout llPhotos = fv(R.id.llPhotos);
        int childCount = llPhotos.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            llPhotos.getChildAt(i).setVisibility(View.GONE);
        }
        int size;
        if (getUploadPhotos().size() == Constants.MAX_UPLOAD_PHOTO_NUM) {
            size = Constants.MAX_UPLOAD_PHOTO_NUM;
        } else {
            size = getUploadPhotos().size() + 1;
        }
        int photoSize=getUploadPhotos().size();
        int row = (size % 3 == 0) ? size / 3 : size / 3 + 1;
        LinearLayout itemView;
        for (int i = 0; i < row; ++i) {
            if (i < llPhotos.getChildCount()) {
                itemView = (LinearLayout) llPhotos.getChildAt(i);
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_add_say_photo, null);
                llPhotos.addView(itemView);
            }
            int index = i * 3;
            setPhoto((ViewGroup) itemView.getChildAt(0), index < photoSize ? getUploadPhotos().get(index) : null, index, index == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(1), index + 1 < photoSize ? getUploadPhotos().get(index + 1) : null, index + 1, index + 1 == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(2), index + 2 < photoSize ? getUploadPhotos().get(index + 2) : null, index + 2, index + 2 == size - 1);
        }
    }

    private void setPhoto(ViewGroup itemView, UploadPhoto photo, int index, boolean isLast) {
        ImageView ivPhoto = (ImageView) itemView.getChildAt(0);
        ImageView ivClose = (ImageView) itemView.getChildAt(1);
        View addPhoto = itemView.getChildAt(2);
        if (photo != null) {
            itemView.setVisibility(View.VISIBLE);
            addPhoto.setVisibility(View.GONE);
            ivPhoto.setVisibility(View.VISIBLE);
            Utils.loadImage(R.drawable.default_1, Uri.fromFile(photo.mFile), ivPhoto);
            ivClose.setVisibility(View.VISIBLE);
            ivPhoto.setTag(R.id.tag, index);
            ivClose.setTag(R.id.tag, index);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag(R.id.tag);
                    UploadPhoto uploadPhoto = getUploadPhotos().remove(pos);
                    if (uploadPhoto != null && uploadPhoto.mFile!=null) {
                        uploadPhoto.mFile.delete();
                    }
                    resetPhotosView();
                }
            });
        } else {
            if (isLast) {
                itemView.setVisibility(View.VISIBLE);
                ivPhoto.setVisibility(View.GONE);
                addPhoto.setVisibility(View.VISIBLE);
                ivClose.setVisibility(View.GONE);
                addPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getUploadPhotos().size()<Constants.MAX_UPLOAD_PHOTO_NUM) {
                            ((BaseActivity) getActivity()).showSelectPhotoWindow();
                        }
                    }
                });
            } else {
                itemView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private ArrayList<UploadPhoto> getUploadPhotos() {
        if (mUploadPhotos == null) {
            mUploadPhotos = new ArrayList<>();
        }
        return mUploadPhotos;
    }

    public void onResume() {
        super.onResume();
        if (DataManager.getInstance().getObject() != null) {
            if (DataManager.getInstance().getObject() instanceof Bitmap) {
                final File file = new File(Utils.saveJpeg((Bitmap) DataManager.getInstance().getObject(), getActivity()));
                final ArrayList<File> list = new ArrayList<>();
                list.add(file);
                HttpMethods.getInstance().uploadFile(list, new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
                        if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                            UploadPhoto photo=new UploadPhoto();
                            photo.mFile=file;
                            photo.mUrl=Utils.getUrlList(bean.getMsg()).get(0);
                            getUploadPhotos().add(photo);
                            resetPhotosView();
                        }
                    }
                }, getActivity(), (BaseActivity) getActivity()));
                DataManager.getInstance().setObject(null);
            }
        }
    }

    //    private void showSelectPhotoWindow() {
//        MorePopupWindow morePopupWindow = new MorePopupWindow(getActivity(), new MorePopupWindow.MorePopupWindowClickListener() {
//            @Override
//            public void onFirstBtnClicked() {
//                DataManager.getInstance().setObject(null);
//                gotoPager(CameraFragment.class, null);
//            }
//
//            @Override
//            public void onSecondBtnClicked() {
//                DataManager.getInstance().setObject(null);
//                gotoPager(AlbumFragment.class, null);
//            }
//
//            @Override
//            public void onThirdBtnClicked() {
//
//            }
//
//            @Override
//            public void onFourthBtnClicked() {
//
//            }
//
//            @Override
//            public void onCancelBtnClicked() {
//
//            }
//        }, MorePopupWindow.MORE_POPUP_WINDOW_TYPE.TYPE_SELECT_ALBUM_OR_CAMERA);
//        morePopupWindow.initView();
//        morePopupWindow.showAtLocation(getView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//    }
    class UploadPhoto {
        public File mFile;
        public String mUrl;
    }
}
