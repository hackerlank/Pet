package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangAdapter extends MyBaseAdapter<AdoptPetBean> {
    public PetLingyangAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.item_pet_lingyang, null);
            holder.tvPetName = fv(view, R.id.tvPetName);
            holder.ivPet = fv(view, R.id.ivPet);
            holder.ivPetSex = fv(view, R.id.ivPetSex);
            holder.tvPetType = fv(view, R.id.tvPetType);
            holder.tvContent = fv(view, R.id.tvContent);
            holder.tvPetAge = fv(view, R.id.tvPetAge);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AdoptPetBean bean = getItem(i);
        setText(holder.tvPetName, bean.getPetName());
        setText(holder.tvPetType, bean.getPetTypeName());
        setText(holder.tvContent, bean.getRemake());
        setText(holder.tvPetAge, bean.getPetAge() + mContext.getString(R.string.age));
        holder.ivPetSex.setImageResource("1".equals(bean.getPetSex())? R.drawable.male : R.drawable.female);
        Utils.loadImage(R.drawable.default_1, bean.getPetHeadUrl(), holder.ivPet);
        return view;
    }

    class ViewHolder {
        ImageView ivPet;
        ImageView ivPetSex;
        TextView tvPetName;
        TextView tvPetAge;
        TextView tvPetType;
        TextView tvContent;
    }
}
