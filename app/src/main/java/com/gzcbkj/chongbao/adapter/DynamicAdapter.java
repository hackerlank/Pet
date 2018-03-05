package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.widgets.DynamicPhotosView;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicAdapter extends MyBaseAdapter<ResponseBean> {
    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_my_dynamic,null);
            holder.tvTime1= fv(view,R.id.tvTime1);
            holder.tvTime2= fv(view,R.id.tvTime2);
            holder.tvContent= fv(view,R.id.tvContent);
            holder.dynamicPhotosView= fv(view,R.id.dynamicPhotosView);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        if(i==0){
            holder.tvTime1.setText(mContext.getString(R.string.today));
            holder.tvTime2.setText("");
            holder.tvContent.setText("");
        }else{
            holder.tvTime1.setText("09");
            holder.tvTime2.setText("1月");
            holder.tvContent.setText("卡哇伊！");
        }
        holder.dynamicPhotosView.setPhotoNum(i);
        return view;
    }

    class ViewHolder{
        TextView tvTime1;
        TextView tvTime2;
        TextView tvContent;
        DynamicPhotosView dynamicPhotosView;

    }
}
