package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.PetJiyangResponseBean;
import com.gzcbkj.chongbao.utils.Utils;
import java.util.ArrayList;

/**
 * Created by gigabud on 17-7-5.
 */

public class PetJiyangHJAdapter extends RecyclerView.Adapter<PetJiyangHJAdapter.ViewHolder> {
    private Context mContext;
    private PetJiyangResponseBean.PetBase mPetBase;
    private ArrayList<String> urlList;

    public PetJiyangHJAdapter(Context context) {
        mContext = context;
    }


    public void setData(PetJiyangResponseBean.PetBase petBase) {
        mPetBase = petBase;
        urlList = Utils.getUrlList(petBase.getPetBasePic());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jiyang_hj, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(urlList.get(position));
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
            tv = itemView.findViewById(R.id.tv);
        }

        public void setData(String url) {
            Utils.loadImage(R.drawable.default_1, url, ivImage);
            tv.setText(mPetBase.getPetBaseName());
        }

    }
}
