package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ArticleCommentBean;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class ArticleCommentAdapter extends MyBaseAdapter<ArticleCommentBean> {
    public ArticleCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_dynamic_comment,null);
            holder.ivAvater= fv(view,R.id.ivAvater);
            holder.tvComment= fv(view,R.id.tvComment);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        ArticleCommentBean bean=getItem(i);
        Utils.setSubText(holder.tvComment,bean.getUsername()+"  "+bean.getContent(),bean.getUsername(),
                mContext.getResources().getColor(R.color.color_33_33_33),
                mContext.getResources().getColor(R.color.color_00_55_87));
        Utils.loadImages(R.drawable.touxiang,bean.getHeadPic(),holder.ivAvater);
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvComment;
    }
}
