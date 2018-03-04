package com.gzcbkj.chongbao.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicDetailPhotosView extends LinearLayout {

    public DynamicDetailPhotosView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_photo,this);
    }

    public void setPhotoNum(int photoNum){

    }
}
