package com.gzcbkj.chongbao.fragment;

import android.view.View;
import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetFriendGroupFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_friend_group;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.pet_friend);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
