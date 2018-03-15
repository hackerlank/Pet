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

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetNoticeAdapter extends MyBaseAdapter<PetFindorlostInfo> {
    public PetNoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_pet_notice,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.ivSex=fv(view,R.id.ivSex);
            holder.tvDate=fv(view,R.id.tvDate);
            holder.tvPetType=fv(view,R.id.tvPetType);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvLocation=fv(view,R.id.tvLocation);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        PetFindorlostInfo bean=getItem(i);
        setText(holder.tvName,bean.getFindorlostPeopleName());
        setText(holder.tvDate,bean.getFindorlostTime());
        setText(holder.tvPetType,bean.getPetVarietyName());
        setText(holder.tvContent,bean.getFindorlostRemake());
        setText(holder.tvLocation,bean.getFindorlostAddress());
        holder.ivSex.setImageResource("1".equals(bean.getFindorlostPetSex()) ? R.drawable.male : R.drawable.female);
        Utils.loadImage(R.drawable.default_1, bean.getFindorlostLmg(), holder.ivAvater);
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        ImageView ivSex;
        TextView tvName;
        TextView tvDate;
        TextView tvPetType;
        TextView tvContent;
        TextView tvLocation;
    }
}
