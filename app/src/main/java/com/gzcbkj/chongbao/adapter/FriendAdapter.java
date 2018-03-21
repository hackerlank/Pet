package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class FriendAdapter extends MyBaseAdapter<ResponseBean> {
    private int mCurrentIndex;
    public FriendAdapter(Context context) {
        super(context);
    }

    public void setCurrentIndex(int index){
        mCurrentIndex=index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_friend,null);
            holder.ivAvater= fv(view,R.id.ivAvater);
            holder.tvName= fv(view,R.id.tvName);
            holder.line= fv(view,R.id.line);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        holder.line.setVisibility(i==getCount()-1?View.INVISIBLE:View.VISIBLE);
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        View line;
    }
}
