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

public class VerifyFriendAdapter extends MyBaseAdapter<ResponseBean> {
    public VerifyFriendAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_verify_friend,null);
            holder.tvName=fv(view,R.id.tvName);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvOperator=fv(view,R.id.tvOperator);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        if(i%2==0){
            holder.tvOperator.setBackgroundResource(R.drawable.bg_verify_friend);
            holder.tvOperator.setTextColor(mContext.getResources().getColor(R.color.color_255_255_255));
            holder.tvOperator.setText(mContext.getString(R.string.pass));
        }else{
            holder.tvOperator.setBackgroundResource(0);
            holder.tvOperator.setTextColor(mContext.getResources().getColor(R.color.color_ff_73_73));
            holder.tvOperator.setText(mContext.getString(R.string.added));
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        TextView tvContent;
        TextView tvOperator;
    }
}
