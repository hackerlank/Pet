package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class ArticleAdapter extends MyBaseAdapter<ArticleBean> {
    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_other_article,null);
            holder.ivPic=fv(view,R.id.ivPic);
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.tvTitle=fv(view,R.id.tvTitle);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.tvName=fv(view,R.id.tvName);
            holder.tvTime=fv(view,R.id.tvTime);
            holder.ivShare=fv(view,R.id.ivShare);
            holder.ivCollect=fv(view,R.id.ivCollect);
            holder.ivLike=fv(view,R.id.ivLike);
            holder.ivComment=fv(view,R.id.ivComment);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        ArticleBean bean = getItem(i);
        Utils.loadImage(R.drawable.default_1, bean.getMainPic(), holder.ivPic);
        Utils.loadImage(R.drawable.touxiang, bean.getUserHead(), holder.ivAvater);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvContent.setText(Html.fromHtml(Utils.replaceHtmlText(bean.getContent())));
        holder.tvName.setText(bean.getUserName());
        holder.tvTime.setText(bean.getCreateTime());
        holder.ivShare.setImageResource(bean.getShareFlag()==1?R.drawable.share:R.drawable.share_grey);
        holder.ivCollect.setImageResource(bean.getShareFlag()==1?R.drawable.collect:R.drawable.collect_grey);
        holder.ivLike.setImageResource(bean.getShareFlag()==1?R.drawable.like:R.drawable.like_grey);
        return view;
    }

    class ViewHolder{
        ImageView ivPic;
        ImageView ivAvater;
        TextView tvTitle;
        TextView tvContent;
        TextView tvName;
        TextView tvTime;
        ImageView ivShare;
        ImageView ivCollect;
        ImageView ivLike;
        ImageView ivComment;
    }
}
