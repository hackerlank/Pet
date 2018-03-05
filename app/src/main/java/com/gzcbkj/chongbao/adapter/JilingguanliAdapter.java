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

public class JilingguanliAdapter extends MyBaseAdapter<ResponseBean> {
    public JilingguanliAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_jiling_guanli,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.ivSex=fv(view,R.id.ivSex);
            holder.tvDate=fv(view,R.id.tvDate);
            holder.tvPetType=fv(view,R.id.tvPetType);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvLocation=fv(view,R.id.tvLocation);
            holder.tvExamineState=fv(view,R.id.tvExamineState);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        ImageView ivSex;
        TextView tvName;
        TextView tvExamineState;
        TextView tvDate;
        TextView tvPetType;
        TextView tvContent;
        TextView tvLocation;
    }
}
