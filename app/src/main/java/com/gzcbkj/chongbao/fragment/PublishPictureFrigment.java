package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.MorePopupWindow;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishPictureFrigment extends BaseFragment {

    private ArrayList<File> mUploadPhotos;

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
                break;
        }
    }

    private void resetPhotosView() {
        LinearLayout llCarPhotos = fv(R.id.llPhotos);
        llCarPhotos.removeAllViews();
        int size;
        if (getUploadPhotos().size() == Constants.MAX_UPLOAD_PHOTO_NUM) {
            size = Constants.MAX_UPLOAD_PHOTO_NUM;
        } else {
            size = getUploadPhotos().size() + 1;
        }
        int row = (size % 3 == 0) ? size / 3 : size / 3 + 1;
        LinearLayout itemView;
        for (int i = 0; i < row; ++i) {
            itemView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_car_photo, null);
            llCarPhotos.addView(itemView);
            int index = i * 3;
            setPhoto((ViewGroup) itemView.getChildAt(0), index < getUploadPhotos().size() ? getUploadPhotos().get(index) : null, index, index == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(1), index + 1 < getUploadPhotos().size() ? getUploadPhotos().get(index + 1) : null, index + 1, index + 1 == size - 1);
            setPhoto((ViewGroup) itemView.getChildAt(2), index + 2 < getUploadPhotos().size() ? getUploadPhotos().get(index + 2) : null, index + 2, index + 2 == size - 1);
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
                    File file = getUploadPhotos().remove(pos);
                    if (file != null) {
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
//                        DataManager.getInstance().setObject(getUploadPhotos().size());
//                        gotoPager(SelectPhotoFragment.class, null, true);
                        showSelectPhotoWindow();
                    }
                });
            } else {
                itemView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private ArrayList<File> getUploadPhotos() {
        if (mUploadPhotos == null) {
            mUploadPhotos = new ArrayList<>();
        }
        return mUploadPhotos;
    }

    private void showSelectPhotoWindow() {
        MorePopupWindow morePopupWindow = new MorePopupWindow(getActivity(), new MorePopupWindow.MorePopupWindowClickListener() {
            @Override
            public void onFirstBtnClicked() {
                DataManager.getInstance().setObject(null);
                gotoPager(CameraFragment.class, null);
            }

            @Override
            public void onSecondBtnClicked() {
                DataManager.getInstance().setObject(null);
                gotoPager(AlbumFragment.class, null);
            }

            @Override
            public void onThirdBtnClicked() {

            }

            @Override
            public void onFourthBtnClicked() {

            }

            @Override
            public void onCancelBtnClicked() {

            }
        }, MorePopupWindow.MORE_POPUP_WINDOW_TYPE.TYPE_SELECT_ALBUM_OR_CAMERA);
        morePopupWindow.initView();
        morePopupWindow.showAtLocation(getView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
