package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.RelationBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class FriendAdapter extends MyBaseAdapter<RelationBean> {
    private int mCurrentIndex;
    private String mMyUserId;
    public FriendAdapter(Context context) {
        super(context);
        mMyUserId= DataManager.getInstance().getMyUserInfo().getUserId();
    }

    public void setCurrentIndex(int index){
        mCurrentIndex=index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_friend,null);
            holder.ivAvater= fv(view,R.id.ivAvater);
            holder.tvName= fv(view,R.id.tvName);
            holder.line= fv(view,R.id.line);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        holder.line.setVisibility(i==getCount()-1?View.INVISIBLE:View.VISIBLE);
        RelationBean bean=getItem(i);
        if(mMyUserId.equals(bean.getUserId())){
            setText(holder.tvName,bean.getUsername2());
            Utils.loadImages(R.drawable.touxiang,bean.getHeadPic2(),holder.ivAvater);
        }else{
            setText(holder.tvName,bean.getUsername());
            Utils.loadImages(R.drawable.touxiang,bean.getHeadPic1(),holder.ivAvater);
        }
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvName;
        View line;
    }
}
