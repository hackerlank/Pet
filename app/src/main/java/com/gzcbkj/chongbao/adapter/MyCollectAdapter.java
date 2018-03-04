package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.BaseBean;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MyCollectAdapter extends MyBaseAdapter<BaseBean> {
    public MyCollectAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_my_collection,null);
            holder.rlCollect1=fv(view,R.id.rlCollect1);
            holder.rlCollect2=fv(view,R.id.rlCollect2);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        if(i%2==0){
            holder.rlCollect1.setVisibility(View.VISIBLE);
            holder.rlCollect2.setVisibility(View.GONE);
        }else{
            holder.rlCollect1.setVisibility(View.GONE);
            holder.rlCollect2.setVisibility(View.VISIBLE);
        }
        return view;
    }

    class ViewHolder{
        View rlCollect1;
        View rlCollect2;
    }
}
