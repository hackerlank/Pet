package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicDetailPhotosView;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetGroupFriendAdapter extends MyBaseAdapter<SayBean> {
    public PetGroupFriendAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_pet_group_friend,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvLikePerson=fv(view,R.id.tvComment);
            holder.tvComment=fv(view,R.id.tvComment);
            holder.tvTime=fv(view,R.id.tvTime);
            holder.dynamicDetailPhotosView=fv(view,R.id.dynamicDetailPhotosView);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        SayBean bean=getItem(i);
        holder.dynamicDetailPhotosView.setImageList(bean.getSayImgList());
        Utils.setSubText(holder.tvComment,"一把勺子  拍的很好看。","一把勺子",
                mContext.getResources().getColor(R.color.color_33_33_33),
                mContext.getResources().getColor(R.color.color_00_55_87));
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        TextView tvComment;
        TextView tvLikePerson;
        DynamicDetailPhotosView dynamicDetailPhotosView;
    }
}
