package com.pet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected ArrayList<T> mDataList;
    protected LayoutInflater mLayoutInflater;
    protected T mSelectData;

    public MyBaseAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDataList(ArrayList<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void setSelectData(T data){
        mSelectData=data;
        notifyDataSetChanged();
    }

    public void addDatas(ArrayList<T> dataList) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        if (mDataList != null) {
            mDataList.remove(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(int position) {
        if (mDataList != null) {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void addData(T data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public ArrayList<T> getDatas() {
        return mDataList;
    }

    public void clearData() {
        if (mDataList != null) {
            mDataList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected <VT extends View> VT fv(View parent, int id) {
        return (VT) parent.findViewById(id);
    }

}
