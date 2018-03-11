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

public class DynamicDetailPhotosView extends LinearLayout {

    public DynamicDetailPhotosView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_photo,this);
    }

    public void setImageList(ArrayList<SayBean.SayImg> list){

        if(list==null || list.isEmpty()){
            setVisibility(View.GONE);
        }else{
            int size=list.size();
            if(size==1){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv1_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine1_1).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.ll2).setVisibility(View.GONE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==2){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.ll2).setVisibility(View.GONE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==3){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.GONE);
                findViewById(R.id.ll2).setVisibility(View.GONE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==4){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv2_1));
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView) findViewById(R.id.iv2_2));
                findViewById(R.id.iv2_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==5){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView) findViewById(R.id.iv2_1));
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(4).getImgUrl(),(ImageView) findViewById(R.id.iv2_2));
                findViewById(R.id.iv2_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==6){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_3).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.GONE);
                findViewById(R.id.ll3).setVisibility(View.GONE);
            }else if(size==7){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView) findViewById(R.id.iv2_1));
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(4).getImgUrl(),(ImageView) findViewById(R.id.iv2_2));
                findViewById(R.id.iv2_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(5).getImgUrl(),(ImageView) findViewById(R.id.iv2_3));
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.VISIBLE);
                findViewById(R.id.ll3).setVisibility(View.VISIBLE);
                findViewById(R.id.iv3_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(6).getImgUrl(),(ImageView) findViewById(R.id.iv3_1));
                findViewById(R.id.iv3_2).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv3_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine3_1).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine3_2).setVisibility(View.INVISIBLE);
            }else if(size==8){
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView) findViewById(R.id.iv2_1));
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(4).getImgUrl(),(ImageView) findViewById(R.id.iv2_2));
                findViewById(R.id.iv2_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(5).getImgUrl(),(ImageView) findViewById(R.id.iv2_3));
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.VISIBLE);
                findViewById(R.id.ll3).setVisibility(View.VISIBLE);
                findViewById(R.id.iv3_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(6).getImgUrl(),(ImageView) findViewById(R.id.iv3_1));
                findViewById(R.id.iv3_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(7).getImgUrl(),(ImageView) findViewById(R.id.iv3_2));
                findViewById(R.id.iv3_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.vLine3_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine3_2).setVisibility(View.INVISIBLE);
            }else{
                findViewById(R.id.ll1).setVisibility(View.VISIBLE);
                findViewById(R.id.iv1_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(0).getImgUrl(),(ImageView) findViewById(R.id.iv1_1));
                findViewById(R.id.iv1_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(1).getImgUrl(),(ImageView) findViewById(R.id.iv1_2));
                findViewById(R.id.iv1_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(2).getImgUrl(),(ImageView) findViewById(R.id.iv1_3));
                findViewById(R.id.vLine1_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine1_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine1).setVisibility(View.VISIBLE);
                findViewById(R.id.ll2).setVisibility(View.VISIBLE);
                findViewById(R.id.iv2_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(3).getImgUrl(),(ImageView) findViewById(R.id.iv2_1));
                findViewById(R.id.iv2_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(4).getImgUrl(),(ImageView) findViewById(R.id.iv2_2));
                findViewById(R.id.iv2_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(5).getImgUrl(),(ImageView) findViewById(R.id.iv2_3));
                findViewById(R.id.vLine2_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine2_2).setVisibility(View.VISIBLE);
                findViewById(R.id.hLine2).setVisibility(View.VISIBLE);
                findViewById(R.id.ll3).setVisibility(View.VISIBLE);
                findViewById(R.id.iv3_1).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(6).getImgUrl(),(ImageView) findViewById(R.id.iv3_1));
                findViewById(R.id.iv3_2).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(7).getImgUrl(),(ImageView) findViewById(R.id.iv3_2));
                findViewById(R.id.iv3_3).setVisibility(View.VISIBLE);
                Utils.loadImage(R.drawable.default_1,list.get(8).getImgUrl(),(ImageView) findViewById(R.id.iv3_3));
                findViewById(R.id.vLine3_1).setVisibility(View.VISIBLE);
                findViewById(R.id.vLine3_2).setVisibility(View.VISIBLE);
            }
        }

    }
}
