package com.gzcbkj.chongbao.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * Created by huangzhifeng on 2018/3/1.
 */

public class SuggestionFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_suggestion;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.suggest);
        setViewsOnClickListener(R.id.tvSubmit);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvSubmit:
                String text=getTextById(R.id.etSuggestion);
                if(TextUtils.isEmpty(text)){
                    showToast(R.string.please_input_your_suggestion);
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.KEY_BASE_BEAN,1);
                gotoPager(SubmitSucFragment.class,bundle);
                break;
        }
    }
}
