package com.gzcbkj.chongbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicPhotosView;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicCommentAdapter extends MyBaseAdapter<BaseBean> {
    public DynamicCommentAdapter(Context context) {
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
        Utils.setSubText(holder.tvComment,"一把勺子  拍的很好看。","一把勺子",
                mContext.getResources().getColor(R.color.color_33_33_33),
                mContext.getResources().getColor(R.color.color_00_55_87));
        return view;
    }

    class ViewHolder{
        ImageView ivAvater;
        TextView tvComment;

    }
}
