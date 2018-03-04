package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class SubmitSucFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_submit_suc;
    }

    @Override
    protected void onViewCreated(View view) {
        if(getArguments().getInt(Constants.KEY_BASE_BEAN,0)==0) {
            setText(R.id.tvTitle, R.string.pay_suc);
            setImage(R.id.ivSuc,R.drawable.paysuccesful);
        }else{
            setText(R.id.tvTitle, R.string.submit_suc);
            setImage(R.id.ivSuc,R.drawable.tijiaochenggong);
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
