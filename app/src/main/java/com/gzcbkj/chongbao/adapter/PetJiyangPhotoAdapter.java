package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.PetJiyangResponseBean;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gigabud on 17-7-5.
 */

public class PetJiyangPhotoAdapter extends RecyclerView.Adapter<PetJiyangPhotoAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mUrlList;

    public PetJiyangPhotoAdapter(Context context) {
        mContext = context;
    }


    public void setData(ArrayList<String> urlList) {
        mUrlList = urlList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jiyang_pet_photo, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mUrlList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUrlList == null ? 0 : mUrlList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
        }

        public void setData(String url) {
            Utils.loadImage(R.drawable.default_1, url, ivImage);
        }

    }
}
