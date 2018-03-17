package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.PetVarietyBean;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class SelectPetTypeAdapter extends MyBaseAdapter<PetVarietyBean> {
    public SelectPetTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_pet_type,null);
            holder.tvPetType= fv(view,R.id.tvPetType);
            holder.line= fv(view,R.id.line);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        setText(holder.tvPetType,getItem(i).getPetVarietyName());
        holder.line.setVisibility(i== getCount()-1?View.GONE:View.VISIBLE);
        return view;
    }

    class ViewHolder{
        TextView tvPetType;
        View line;
    }
}
