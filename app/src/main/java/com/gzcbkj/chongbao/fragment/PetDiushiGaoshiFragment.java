package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetDiushiGaoshiFragment extends PetShideGaoshiFragment {


    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.diushi_ai_chong);
        setViewsOnClickListener(R.id.tvSubmit);
        setText(R.id.tv1,R.string.diushi_time);
        setText(R.id.tv2,R.string.diushi_address);
    }

    protected String getType() {
        return "1";
    }
}
