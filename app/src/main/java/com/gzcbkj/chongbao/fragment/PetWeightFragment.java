package com.gzcbkj.chongbao.fragment;

import android.text.TextUtils;
import android.view.View;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PetWeightFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_weight;
    }

    @Override
    protected void onViewCreated(View view) {
        String weight=getArguments().getString(Constants.KEY_BASE_BEAN,"");
        setText(R.id.tvTitle, R.string.pet_weight_2);
        setViewVisible(R.id.tvRight);
        setText(R.id.tvRight, R.string.save);
        setViewsOnClickListener(R.id.tvRight);
        setText(R.id.etWeight,weight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvRight:
                final String weight=getTextById(R.id.etWeight);
                if(TextUtils.isEmpty(weight)){
                    showToast(R.string.your_pet_weight);
                    return;
                }
                    DataManager.getInstance().setObjectType(Constants.OBJECT_TYPE_PET_WEIGHT);
                    DataManager.getInstance().setObject(weight);
                hideKeyBoard();
                goBack();
                break;
        }
    }
}
