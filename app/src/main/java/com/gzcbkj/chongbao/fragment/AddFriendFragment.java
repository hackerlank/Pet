package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class AddFriendFragment extends BaseFragment {

    private ArrayList<UserInfoBean> mQueryFriends;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_friend;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.add_new_friend);
        setViewsOnClickListener(R.id.ivUnlike,R.id.ivLike);
    }

    @Override
    public void updateUIText() {

    }

    public void onResume(){
        super.onResume();
        if(mQueryFriends==null || mQueryFriends.isEmpty()){
            HttpMethods.getInstance().queryFriends(new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                @Override
                public void onNext(ResponseBean bean) {

                }
            },getActivity(),(BaseActivity)getActivity()));
        }
    }

    @Override
    public void onClick(View view) {

    }
}
