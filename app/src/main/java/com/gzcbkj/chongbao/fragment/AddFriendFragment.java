package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class AddFriendFragment extends BaseFragment {
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

    @Override
    public void onClick(View view) {

    }
}
