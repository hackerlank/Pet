package com.gzcbkj.chongbao.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.utils.Utils;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicPhotosView extends LinearLayout {

    public DynamicPhotosView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_dynamic_photo,this);
    }

    public void setPhoto(String phone){
        findViewById(R.id.iv).setVisibility(View.GONE);
        findViewById(R.id.ll1).setVisibility(View.VISIBLE);
        findViewById(R.id.hLine1).setVisibility(View.GONE);
        findViewById(R.id.iv1).setVisibility(View.VISIBLE);
        Utils.loadImage(R.drawable.default_1,phone,(ImageView)findViewById(R.id.iv1));
        findViewById(R.id.iv3).setVisibility(View.GONE);
        findViewById(R.id.vLine).setVisibility(View.GONE);
        findViewById(R.id.ll2).setVisibility(View.GONE);
    }


    public void setPhotoNum(int index,ArrayList<SayBean.SayImg> list){
        if(index==0){
            findViewById(R.id.ll1).setVisibility(View.GONE);
            findViewById(R.id.vLine).setVisibility(View.GONE);
            findViewById(R.id.ll2).setVisibility(View.GONE);
            ImageView iv=findViewById(R.id.iv);
            iv.setVisibility(View.VISIBLE);
            iv.setBackgroundColor(getResources().getColor(R.color.color_55_55_55));
            iv.setImageResource(R.drawable.publish_camera);
        }else{
            int photoNum=(list==null?0:list.size());
            findViewById(R.id.iv).setVisibility(View.GONE);
            if(photoNum==0){
                findViewById(R.id.ll1).setVisibility(View.GONE);
                findViewById(R.id.vLine).setVisibility(View.GONE);
                findViewById(R.id.ll2).setVisibility(View.GONE);
            }else if(photoNum==1){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.iv1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView)findViewById(R.id.iv1));
                findViewById(R.id.iv3).setVisibility(View.GONE);
                findViewById(R.id.vLine).setVisibility(View.GONE);
                findViewById(R.id.ll2).setVisibility(View.GONE);
            }else if(photoNum==2){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.iv1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView)findViewById(R.id.iv1));
                findViewById(R.id.iv3).setVisibility(View.GONE);
                findViewById(R.id.vLine).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.iv2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView)findViewById(R.id.iv2));
                findViewById(R.id.iv4).setVisibility(View.GONE);
            }else if(photoNum==3){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.iv1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView)findViewById(R.id.iv1));
                findViewById(R.id.iv3).setVisibility(View.GONE);
                findViewById(R.id.vLine).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView)findViewById(R.id.iv2));
                findViewById(R.id.iv4).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView)findViewById(R.id.iv4));
            }else{
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView)findViewById(R.id.iv1));
                findViewById(R.id.iv3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView)findViewById(R.id.iv3));
                findViewById(R.id.vLine).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView)findViewById(R.id.iv2));
                findViewById(R.id.iv4).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView)findViewById(R.id.iv4));
            }
        }
    }
}
