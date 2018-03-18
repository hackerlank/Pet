package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayDetailResponse;
import com.gzcbkj.chongbao.utils.Utils;

import java.util.ArrayList;

/**
 * Created by gigabud on 17-7-5.
 */

public class LikeDynamicAdapter extends RecyclerView.Adapter<LikeDynamicAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SayDetailResponse.PraiseUser> mDataList;

    public LikeDynamicAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(ArrayList<SayDetailResponse.PraiseUser> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_like_avater, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivAvater;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivAvater = itemView.findViewById(R.id.ivAvater);;
        }

        public void setData(SayDetailResponse.PraiseUser user) {
            Utils.loadImages(R.drawable.touxiang,user.getUserHead(),ivAvater);
        }

    }
}
