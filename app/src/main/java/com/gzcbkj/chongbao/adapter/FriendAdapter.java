package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class FriendAdapter extends MyBaseAdapter<BaseBean> {
    public FriendAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_friend,null);
            holder.ivAvater= fv(view,R.id.ivAvater);
            holder.tvName= fv(view,R.id.tvName);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;

    }
}
