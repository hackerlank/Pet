package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class MoneyAccountFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_account;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.account);
        setViewsOnClickListener(R.id.tvCharge);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvCharge:
                gotoPager(MoneyChargeFragment.class,null);
                break;
        }
    }
}
