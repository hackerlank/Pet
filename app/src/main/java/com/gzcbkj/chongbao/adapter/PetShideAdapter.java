package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.PetFindorlostInfo;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.utils.Utils;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetShideAdapter extends MyBaseAdapter<PetFindorlostInfo> {
    public PetShideAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_pet_shide,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.ivSex=fv(view,R.id.ivSex);
            holder.tvTime=fv(view,R.id.tvTime);
            holder.tvPetType=fv(view,R.id.tvPetType);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvLocation=fv(view,R.id.tvLocation);
            holder.tvDistance=fv(view,R.id.tvDistance);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        PetFindorlostInfo bean=getItem(i);
        setText(holder.tvName,bean.getFindorlostPeopleName());
        setText(holder.tvTime,bean.getFindorlostTime());
        setText(holder.tvPetType,bean.getPetVarietyName());
        setText(holder.tvContent,bean.getFindorlostRemake());
        setText(holder.tvLocation,bean.getFindorlostAddress());
        holder.ivSex.setImageResource("1".equals(bean.getFindorlostPetSex()) ? R.drawable.male : R.drawable.female);
        Utils.loadImages(R.drawable.touxiang, bean.getFindorlostLmg(), holder.ivAvater);
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        ImageView ivSex;
        TextView tvName;
        TextView tvDistance;
        TextView tvTime;
        TextView tvPetType;
        TextView tvContent;
        TextView tvLocation;
    }
}
