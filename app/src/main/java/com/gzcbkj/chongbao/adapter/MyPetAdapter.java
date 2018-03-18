package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.MyPetBean;
import com.gzcbkj.chongbao.utils.Utils;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MyPetAdapter extends MyBaseAdapter<BaseBean> {
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
            holder.tvPetAge=fv(view,R.id.tvPetAge);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        BaseBean bean=getItem(i);
        ArrayList<String> urlList;
        if(bean instanceof MyPetBean){
            MyPetBean petBean=(MyPetBean) bean;
            setText(holder.tvName,petBean.getOwnPetName());
            setText(holder.tvPetType,petBean.getPetVarietyName());
            setText(holder.tvPetAge,petBean.getPetAge()+mContext.getString(R.string.age));
            Utils.loadImages(R.drawable.touxiang, petBean.getOwnPetHeadurl(), holder.ivAvater);
        }else{
            AdoptPetBean petBean=(AdoptPetBean) bean;
            setText(holder.tvName,petBean.getPetName());
            setText(holder.tvPetType,petBean.getPetVarietyName());
            setText(holder.tvPetAge,petBean.getPetAge()+mContext.getString(R.string.age));
            Utils.loadImages(R.drawable.touxiang, petBean.getPetHeadUrl(), holder.ivAvater);
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        TextView tvPetType;
        TextView tvPetAge;
    }
}
