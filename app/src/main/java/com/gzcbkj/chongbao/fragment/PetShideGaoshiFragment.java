package com.gzcbkj.chongbao.fragment;

import android.view.View;

import com.gzcbkj.chongbao.R;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetShideGaoshiFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_shide_gaoshi;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.shide_pet_gaoshi);
        setViewsOnClickListener(R.id.tvSubmit);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvSubmit:

                break;
        }
    }
}
