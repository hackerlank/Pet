package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class MoneyChargeFragment extends BaseFragment {

    private int mCurrentPay=0;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_charge;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.account);
        mCurrentPay=0;
        setViewsOnClickListener(R.id.tvNextStep,R.id.llPay,R.id.llWechatPay,R.id.llAliPay);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvNextStep:
                setViewGone(R.id.llSelectPay);
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.KEY_BASE_BEAN,0);
                gotoPager(SubmitSucFragment.class,bundle);
                break;
            case R.id.llPay:
                setViewVisible(R.id.llSelectPay);
                if(mCurrentPay==0){
                    fv(R.id.llAliPay).setBackgroundColor(getResources().getColor(R.color.background_color));
                    fv(R.id.llWechatPay).setBackgroundColor(Color.WHITE);
                }else{
                    fv(R.id.llWechatPay).setBackgroundColor(getResources().getColor(R.color.background_color));
                    fv(R.id.llAliPay).setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.llAliPay:
                mCurrentPay=0;
                resetPayLayout();
                break;
            case R.id.llWechatPay:
                mCurrentPay=1;
                resetPayLayout();
                break;

        }
    }

    private void resetPayLayout(){
        setViewGone(R.id.llSelectPay);
        setImage(R.id.ivPay,mCurrentPay==0?R.drawable.alipay:R.drawable.wechat_pay);
        setText(R.id.tvPay,mCurrentPay==0?R.string.ali_pay:R.string.wechat_pay);
        setImage(R.id.ivArrow,R.drawable.jiantou2);
    }
}
