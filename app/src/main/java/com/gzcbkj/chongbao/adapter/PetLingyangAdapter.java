package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangAdapter extends MyBaseAdapter<ResponseBean> {
    public PetLingyangAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_pet_lingyang,null);
            holder.tvPetName=fv(view,R.id.tvPetName);
            holder.ivPet=fv(view,R.id.ivPet);
            holder.ivPetSex=fv(view,R.id.ivPetSex);
            holder.tvPetType=fv(view,R.id.tvPetType);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvPetAge=fv(view,R.id.tvPetAge);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        ImageView ivPet;
        ImageView ivPetSex;
        TextView tvPetName;
        TextView tvPetAge;
        TextView tvPetType;
        TextView tvContent;
    }
}
