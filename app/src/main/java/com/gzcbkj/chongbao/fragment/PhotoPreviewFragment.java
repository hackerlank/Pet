package com.gzcbkj.chongbao.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.ShowPicView;


/**
 * Created by gigabud on 16-6-21.
 */
public class PhotoPreviewFragment extends BaseFragment {

    private int mCameraUseType;
    private Bitmap mScreenBmp, mBmp;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo_preview;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            goBack();
            return;
        }
        mCameraUseType = bundle.getInt(CameraFragment.USE_CAMERA_TYPE);
    }


    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvCancel, R.id.tvOk);

        mBmp = (Bitmap) DataManager.getInstance().getObject();
        if (mBmp == null) {
            goBack();
            return;
        }
        DataManager.getInstance().setObject(null);
        final ShowPicView showPicView = (ShowPicView) view.findViewById(R.id.ivShowPic);
        if (mCameraUseType == Constants.TYPE_CAMERA_FOR_AVATER) {
            view.findViewById(R.id.cutPhotoView).setVisibility(View.VISIBLE);
            showPicView.setImageBitmap(mBmp, true);
        } else {
            showPicView.setImageBitmap(mBmp);
        }
    }

    @Override
    public void updateUIText() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvCancel) {
            DataManager.getInstance().setObject(null);
            getActivity().finish();
        } else if (id == R.id.tvOk) {
            if (!Utils.isGrantPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ((BaseActivity) getActivity()).requestPermission(0,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                return;
            }
            if (mCameraUseType == Constants.TYPE_CAMERA_FOR_AVATER) {
                View rl = getView().findViewById(R.id.rl);
                mScreenBmp = Bitmap.createBitmap(rl.getWidth(), rl.getHeight(), Bitmap.Config.ARGB_8888);
                rl.draw(new Canvas(mScreenBmp));
                final ShowPicView showPicView = (ShowPicView) getView().findViewById(R.id.ivShowPic);
                showPicView.setImageBitmap(mScreenBmp, false);
                mBmp.recycle();
                mBmp = null;

                int x = 0;
                int y = mScreenBmp.getHeight() / 10;
                Bitmap newBmp = Bitmap.createBitmap(mScreenBmp, x, y, mScreenBmp.getWidth(), mScreenBmp.getWidth());
                DataManager.getInstance().setObjectType(Constants.OBJECT_TYPE_AVATER);
                DataManager.getInstance().setObject(Utils.saveJpeg(newBmp, getActivity()));
                newBmp.recycle();
                newBmp = null;
            }
            getActivity().finish();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mScreenBmp != null && !mScreenBmp.isRecycled()) {
            mScreenBmp.recycle();
        }
        mScreenBmp = null;
    }

}
