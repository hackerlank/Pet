package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicPhotosView;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicAdapter extends MyBaseAdapter<SayBean> {
    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 1+super.getCount();
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
           // holder.dynamicPhotosView.setPhotoNum();
            holder.dynamicPhotosView.setPhotoNum(i,null);
        }else{
            SayBean bean=getItem(i-1);
            Calendar calendar=Calendar.getInstance();
            try {
                calendar.setTime(Utils.stringToDate(bean.getCreateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.tvTime1.setText(Utils.getNewText(calendar.get(Calendar.DAY_OF_MONTH)));
            int month=calendar.get(Calendar.MONTH)+1;
            holder.tvTime2.setText(Utils.getNewText(month)+mContext.getString(R.string.month));
            holder.tvContent.setText(bean.getContent()==null?"":bean.getContent());
            holder.dynamicPhotosView.setPhotoNum(i,bean.getSayImgList());
        }

        return view;
    }

    class ViewHolder{
        TextView tvTime1;
        TextView tvTime2;
        TextView tvContent;
        DynamicPhotosView dynamicPhotosView;

    }
}
