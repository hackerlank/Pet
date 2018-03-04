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

public class DynamicPhotosView extends LinearLayout {

    public DynamicPhotosView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_dynamic_photo,this);
    }

    public void setPhotoNum(int photoNum){
        if(photoNum==0 || photoNum==1){
            findViewById(R.id.ll1).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine1).setVisibility(View.GONE);
            findViewById(R.id.iv1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv2).setVisibility(View.GONE);
            findViewById(R.id.hLine).setVisibility(View.GONE);
            findViewById(R.id.ll2).setVisibility(View.GONE);
        }else if(photoNum==2){
            findViewById(R.id.ll1).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv2).setVisibility(View.VISIBLE);
            findViewById(R.id.hLine).setVisibility(View.GONE);
            findViewById(R.id.ll2).setVisibility(View.GONE);
        }else if(photoNum==3){
            findViewById(R.id.ll1).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv2).setVisibility(View.VISIBLE);
            findViewById(R.id.hLine).setVisibility(View.VISIBLE);
            findViewById(R.id.ll2).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine2).setVisibility(View.VISIBLE);
            findViewById(R.id.iv3).setVisibility(View.VISIBLE);
            findViewById(R.id.iv4).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.ll1).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv1).setVisibility(View.VISIBLE);
            findViewById(R.id.iv2).setVisibility(View.VISIBLE);
            findViewById(R.id.hLine).setVisibility(View.VISIBLE);
            findViewById(R.id.ll2).setVisibility(View.VISIBLE);
            findViewById(R.id.vLine2).setVisibility(View.VISIBLE);
            findViewById(R.id.iv3).setVisibility(View.VISIBLE);
            findViewById(R.id.iv4).setVisibility(View.VISIBLE);
        }
    }
}
