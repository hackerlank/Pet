package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MessageCenterAdapter extends MyBaseAdapter<ResponseBean> {
    public MessageCenterAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_message_center,null);
            holder.tvMsgType=fv(view,R.id.tvMsgType);
            holder.tvDate=fv(view,R.id.tvDate);
            holder.tvMsgContent=fv(view,R.id.tvMsgContent);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder{
        TextView tvMsgType;
        TextView tvDate;
        TextView tvMsgContent;
    }
}
