package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.CollectionBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicDetailPhotosView;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class MyCollectAdapter extends MyBaseAdapter<CollectionBean> {
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
            holder.ivAvater=fv(view,R.id.ivAvater);
            holder.tvName=fv(view,R.id.tvName);
            holder.tvContent=fv(view,R.id.tvContent);
            holder.dynamicDetailPhotosView=fv(view,R.id.dynamicDetailPhotosView);
            holder.tvTime=fv(view,R.id.tvTime);
            holder.tvLikeNum=fv(view,R.id.tvLikeNum);
            holder.tvCommentNum=fv(view,R.id.tvCommentNum);

            holder.rlCollect2=fv(view,R.id.rlCollect2);
            holder.ivAvater2=fv(view,R.id.ivAvater2);
            holder.tvName2=fv(view,R.id.tvName2);
            holder.tvContent2=fv(view,R.id.tvContent2);
            holder.ivPic=fv(view,R.id.ivPic);
            holder.tvTime2=fv(view,R.id.tvTime2);
            holder.tvLikeNum2=fv(view,R.id.tvLikeNum2);
            holder.tvCommentNum2=fv(view,R.id.tvCommentNum2);
            holder.tvTitle=fv(view,R.id.tvTitle);
            holder.tvCollectNum2=fv(view,R.id.tvCollectNum2);
            holder.tvShareNum2=fv(view,R.id.tvShareNum2);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        CollectionBean bean=getItem(i);

        if(bean.getArticle()==null){
            SayBean sayBean=bean.getUserSay();
            holder.rlCollect1.setVisibility(View.VISIBLE);
            holder.rlCollect2.setVisibility(View.GONE);
            Utils.loadImages(R.drawable.touxiang,sayBean.getUserHead(),holder.ivAvater);
            setText(holder.tvName,sayBean.getUserName());
            setText(holder.tvContent,sayBean.getContent());
            holder.dynamicDetailPhotosView.setImageList(sayBean.getSayImgList());
            setText(holder.tvTime,Utils.transformTime(mContext,bean.getCreateTime()));
            setText(holder.tvLikeNum,String.valueOf(sayBean.getPraiseNum()));
            setText(holder.tvCommentNum,String.valueOf(sayBean.getCommentNum()));
        }else{
            ArticleBean articleBean=bean.getArticle();
            holder.rlCollect1.setVisibility(View.GONE);
            holder.rlCollect2.setVisibility(View.VISIBLE);
            Utils.loadImages(R.drawable.default_1, articleBean.getMainPic(), holder.ivPic);
            Utils.loadImages(R.drawable.touxiang, articleBean.getUserHead(), holder.ivAvater2);
            setText(holder.tvTitle,articleBean.getTitle());
            holder.tvContent2.setText(Html.fromHtml(Utils.replaceHtmlImage(Utils.replaceHtmlText(articleBean.getContent()))));
            setText(holder.tvName2,articleBean.getUserName());
            setText(holder.tvTime2,Utils.transformTime(mContext,bean.getCreateTime()));
            setText(holder.tvLikeNum2,String.valueOf(articleBean.getPraiseNum()));
            setText(holder.tvCommentNum2,String.valueOf(articleBean.getCommentNum()));
            setText(holder.tvShareNum2,String.valueOf(articleBean.getShareNum()));
            setText(holder.tvCollectNum2,String.valueOf(articleBean.getCollectionNum()));
            holder.tvCollectNum2.setTag(bean);
            holder.tvCollectNum2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CollectionBean collectionBean=(CollectionBean) view.getTag();
                    HttpMethods.getInstance().deleteCollection(collectionBean.getId(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                        @Override
                        public void onNext(ResponseBean bean1) {
                            removeData(collectionBean);
                        }
                    },mContext,(BaseActivity) mContext));
                }
            });
        }
        return view;
    }

    class ViewHolder{
        View rlCollect1;
        ImageView ivAvater;
        TextView tvName;
        TextView tvContent;
        DynamicDetailPhotosView dynamicDetailPhotosView;
        TextView tvTime;
        TextView tvLikeNum;
        TextView tvCommentNum;
        View rlCollect2;
        ImageView ivAvater2;
        TextView tvName2;
        ImageView ivPic;
        TextView tvTime2;
        TextView tvTitle;
        TextView tvContent2;
        TextView tvShareNum2;
        TextView tvCollectNum2;
        TextView tvLikeNum2;
        TextView tvCommentNum2;
    }
}
