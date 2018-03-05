package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;

import java.util.Random;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class OrderAdapter extends MyBaseAdapter<ResponseBean> {

    private int mOrderType;

    public void setOrderTye(int orderType){
        mOrderType=orderType;
    }

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_order,null);
            holder.tvState=fv(view,R.id.tvState);
            holder.iv=fv(view,R.id.iv);
            holder.tvName=fv(view,R.id.tvName);
            holder.tvQuantity=fv(view,R.id.tvQuantity);
            holder.tvPrice=fv(view,R.id.tvPrice);
            holder.tvOperatorOrder=fv(view,R.id.tvOperatorOrder);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        if(mOrderType==0){
            holder.tvState.setText(mContext.getString(R.string.waiting_pay));
            holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_255_255_255));
            holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_waiting_pay);
            holder.tvOperatorOrder.setText(mContext.getString(R.string.pay_now));
        }else if(mOrderType==1){
            holder.tvState.setText(mContext.getString(R.string.waiting_goods));
            holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_255_255_255));
            holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_waiting_pay);
            holder.tvOperatorOrder.setText(mContext.getString(R.string.sure_get_goods));
        }else if(mOrderType==2){
            holder.tvState.setText(mContext.getString(R.string.order_finished));
            holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_66_66_66));
            holder.tvOperatorOrder.setText(mContext.getString(R.string.delete_order));
            holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_order_finish);
        }else{
            int index=new Random().nextInt()%3;
            if(index==0){
                holder.tvState.setText(mContext.getString(R.string.waiting_pay));
                holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_255_255_255));
                holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_waiting_pay);
                holder.tvOperatorOrder.setText(mContext.getString(R.string.pay_now));
            }else if(index==1){
                holder.tvState.setText(mContext.getString(R.string.waiting_goods));
                holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_255_255_255));
                holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_waiting_pay);
                holder.tvOperatorOrder.setText(mContext.getString(R.string.sure_get_goods));
            }else if(index==2){
                holder.tvState.setText(mContext.getString(R.string.order_finished));
                holder.tvOperatorOrder.setTextColor(mContext.getResources().getColor(R.color.color_66_66_66));
                holder.tvOperatorOrder.setText(mContext.getString(R.string.delete_order));
                holder.tvOperatorOrder.setBackgroundResource(R.drawable.bg_order_finish);
            }
        }
        return view;
    }

    class ViewHolder{
        ImageView iv;
        TextView tvState;
        TextView tvName;
        TextView tvQuantity;
        TextView tvPrice;
        TextView tvOperatorOrder;
    }
}
