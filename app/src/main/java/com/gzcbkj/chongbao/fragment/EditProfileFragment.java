package com.gzcbkj.chongbao.fragment;

import android.os.Bundle;
import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class EditProfileFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.personal_prdfile);
        setViewsOnClickListener(R.id.rlAvater,R.id.rlNickname);
    }

    @Override
    public void updateUIText() {
        UserInfoBean bean= DataManager.getInstance().getMyUserInfo();
        if(bean!=null){
            setText(R.id.tvNick,bean.getUsername());
        }else{
            setText(R.id.tvNick,"");
        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.rlAvater:
                break;
            case R.id.rlNickname:
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.KEY_BASE_BEAN,Constants.EDIT_USER_NICK);
                bundle.putString(Constants.KEY_BASE_BEAN_2,getTextById(R.id.tvNick));
                gotoPager(EditNicknameFragment.class,bundle);
                break;
        }
    }
}
