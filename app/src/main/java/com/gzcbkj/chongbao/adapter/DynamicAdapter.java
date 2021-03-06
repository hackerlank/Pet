package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.PublishBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicPhotosView;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicAdapter extends MyBaseAdapter<PublishBean> {
    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 1+super.getCount();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mLayoutInflater.inflate(R.layout.item_my_dynamic,null);
            holder.tvTime1= fv(view,R.id.tvTime1);
            holder.tvTime2= fv(view,R.id.tvTime2);
            holder.tvContent= fv(view,R.id.tvContent);
            holder.tvPhoneNum= fv(view,R.id.tvPhoneNum);
            holder.dynamicPhotosView= fv(view,R.id.dynamicPhotosView);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        if(i==0){
            holder.tvTime1.setText(mContext.getString(R.string.today));
            holder.tvTime2.setText("");
            holder.tvContent.setText("");
            holder.tvPhoneNum.setText("");
            holder.dynamicPhotosView.setPhotoNum(i,null);
        }else{
            PublishBean bean=getItem(i-1);
            Calendar calendar=Calendar.getInstance();
            try {
                calendar.setTime(Utils.stringToDate(bean.getCreateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            calendar=Calendar.getInstance();
            if(year==calendar.get(Calendar.YEAR)
                    && month==calendar.get(Calendar.MONTH)
                    && day==calendar.get(Calendar.DAY_OF_MONTH)){
                holder.tvTime1.setText(mContext.getString(R.string.today));
                holder.tvTime2.setText("");
            }else{
                holder.tvTime1.setText(Utils.getNewText(day));
                holder.tvTime2.setText(Utils.getNewText(month+1)+mContext.getString(R.string.month));
            }
            if(bean.getUserSay()!=null) {
                SayBean userSay=bean.getUserSay();
                setText(holder.tvContent, userSay.getContent());
                holder.dynamicPhotosView.setPhotoNum(i, userSay.getSayImgList());
                if (userSay.getSayImgList() == null || userSay.getSayImgList().isEmpty()) {
                    holder.tvPhoneNum.setText("");
                } else {
                    holder.tvPhoneNum.setText(mContext.getString(R.string.total_phone, String.valueOf(userSay.getSayImgList().size())));
                }
            }else if(bean.getArticle()!=null){
                ArticleBean article=bean.getArticle();
                setText(holder.tvContent, article.getTitle());
                holder.dynamicPhotosView.setPhoto(article.getMainPic());
                holder.tvPhoneNum.setText("");
            }
        }

        return view;
    }

    class ViewHolder{
        TextView tvTime1;
        TextView tvTime2;
        TextView tvContent;
        TextView tvPhoneNum;
        DynamicPhotosView dynamicPhotosView;

    }
}
