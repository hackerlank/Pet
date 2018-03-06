package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishArticleFrigment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_article;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.publish_article);
        TextView tvRight=fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        setText(tvRight,getString(R.string.publish));
        tvRight.setBackgroundResource(R.drawable.publish_bg);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setTextColor(Color.WHITE);
        tvRight.setOnClickListener(this);
    }

    @Override
    public void updateUIText() {

    }

    private int index=0;


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvRight:
                String title=getTextById(R.id.etTitle);
                if(TextUtils.isEmpty(title)){
                    showToast(R.string.please_input_title);
                    return;
                }
                String content=getTextById(R.id.etAddDes);
                if(TextUtils.isEmpty(content)){
                    showToast(R.string.please_input_title);
                    return;
                }
                HttpMethods.getInstance().saveApparticle(Constants.ARTICLE_TYPE[index%3],title,content,Constants.MAIN_PICS[index%3],
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(!TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                            }
                        },getActivity(),(BaseActivity) getActivity()));
                ++index;
                break;
        }
    }
}
