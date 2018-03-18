package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gigabud on 17-7-5.
 */

public class AddPetJiyangPhotoAdapter extends RecyclerView.Adapter<AddPetJiyangPhotoAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<File> mFileList;

    public AddPetJiyangPhotoAdapter(Context context) {
        mContext = context;
    }


    public void setData(ArrayList<File> fileList) {
        mFileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jiyang_pet_photo, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if(mFileList==null){
            return 1;
        }
        int size=mFileList.size();
        if(size== Constants.MAX_UPLOAD_PHOTO_NUM){
            return Constants.MAX_UPLOAD_PHOTO_NUM;
        }else{
            return size+1;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;
        ImageView ivClose;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
            ivClose=itemView.findViewById(R.id.ivClose);
        }

        public void setData(int position) {
            if(position==getItemCount()-1 && mFileList.size()<Constants.MAX_UPLOAD_PHOTO_NUM){
                ivImage.setImageResource(R.drawable.add_pet_btn);
                ivClose.setVisibility(View.GONE);
            }else{
                Utils.loadImage(R.drawable.default_1, Uri.fromFile(mFileList.get(position)), ivImage);
                ivClose.setVisibility(View.VISIBLE);
                ivClose.setTag(R.id.tag,position);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos=(int) view.getTag(R.id.tag);
                        mFileList.remove(pos);
                        notifyDataSetChanged();
                    }
                });
            }
            ivImage.setTag(R.id.tag,position);
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=(int) view.getTag(R.id.tag);
                    if(pos==getItemCount()-1 && mFileList.size()<Constants.MAX_UPLOAD_PHOTO_NUM){
                        ((BaseActivity) mContext).showSelectPhotoWindow();
                    }
                }
            });
        }
    }
}
