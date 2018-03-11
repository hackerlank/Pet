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
                String content1="关于他在场上最合适的位置突然成为了外界讨论的焦点。就在此时，阿扎尔在主场迎战水晶宫的比赛中如脱胎换骨一般焕发了往日的风采，他在前场如鱼得水，盘活了蓝军的进攻体系，而唯一的变化便是他重新回到了左边锋的位置上。";
                if(TextUtils.isEmpty(content1)){
                    showToast(R.string.please_input_title);
                    return;
                }

                String image="<img src=\"http://n.sinaimg.cn/sports/transform/w638h423/20180311/bl8J-fxpwyhw9073041.png\"/>";

                String content2="客观的说，切尔西在此前几场比赛中，接连与巴塞罗那、曼城这样善于传控的球队进行比拼，孔蒂在舍弃对球权的控制后安排小快灵的三叉戟在前场寻求反击，也是无可厚非的决定。";

                String content="<p>"+content1+"</p>"+"<p>"+image+"</p>"+"<p>"+content2+"</p>";
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
