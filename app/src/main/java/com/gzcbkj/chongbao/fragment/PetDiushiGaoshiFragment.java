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
        setText(R.id.tv1,R.string.diushi_time);
        setText(R.id.tv2,R.string.diushi_address);
        setViewsOnClickListener(R.id.tvSubmit,R.id.tvShideTime,R.id.tvShidePetType,R.id.tvShidePetType2,R.id.tvMale,
                R.id.tvFemale,R.id.tvNotKnow);
        resetPhotosView();
    }

    protected String getType() {
        return "1";
    }
}
