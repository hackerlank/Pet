package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.PetFosterBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class JilingguanliAdapter extends MyBaseAdapter<BaseBean> {
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
        BaseBean bean=getItem(i);
        if(bean instanceof AdoptPetBean){
            AdoptPetBean adoptPetBean=(AdoptPetBean) bean;
            setText(holder.tvName,adoptPetBean.getPetName());
            setText(holder.tvPetType,adoptPetBean.getPetVarietyName());
            Utils.loadImage(R.drawable.default_1,adoptPetBean.getPetHeadUrl(),holder.ivAvater);
            holder.ivSex.setImageResource("1".equals(adoptPetBean.getPetSex())? R.drawable.male : R.drawable.female);
            setText(holder.tvDate,adoptPetBean.getCreatetime());
            setText(holder.tvContent,adoptPetBean.getRemake());
            setText(holder.tvLocation,"");
            setText(holder.tvExamineState,mContext.getString(1==adoptPetBean.getPetStatus()?R.string.waiting_lingtang:R.string.had_lingyang));
        }else{
            PetFosterBean petFosterBean=(PetFosterBean) bean;
            setText(holder.tvName,petFosterBean.getFosterName());
            setText(holder.tvPetType,petFosterBean.getPetVarietyName());
            Utils.loadImage(R.drawable.default_1,petFosterBean.getFosterPetPic(),holder.ivAvater);
            holder.ivSex.setImageResource("1".equals(petFosterBean.getFosterPetSex())? R.drawable.male : R.drawable.female);
            setText(holder.tvDate,petFosterBean.getCreateTime());
            setText(holder.tvContent,petFosterBean.getRemake());
            setText(holder.tvLocation,petFosterBean.getFosterAddress());
            if(petFosterBean.getFosterStatus()==1){
                setText(holder.tvExamineState,mContext.getString(R.string.not_jiyang));
            }else if(petFosterBean.getFosterStatus()==2){
                setText(holder.tvExamineState,mContext.getString(R.string.jiyang_zhong));
            }else{
                setText(holder.tvExamineState,mContext.getString(R.string.jiyang_jishu));
            }
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
