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

public class MyPetAdapter extends MyBaseAdapter<ResponseBean> {
    public MyPetAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_my_pet,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.tvPetType=fv(view,R.id.tvPetType);
            holder.tvPetTime=fv(view,R.id.tvPetTime);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        TextView tvPetType;
        TextView tvPetTime;
    }
}
