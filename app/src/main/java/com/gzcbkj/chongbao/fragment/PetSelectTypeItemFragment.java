package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.adapter.SelectPetTypeAdapter;
import com.gzcbkj.chongbao.bean.PetVarietyBean;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class PetSelectTypeItemFragment extends BaseFragment {
    private SelectPetTypeAdapter mAdapter;
    private String mPetName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_pet_type_item;
    }

    public void setPetName(String petName){
        mPetName=petName;
    }

    public void setData(ArrayList<PetVarietyBean> list) {
        getAdapter().setDataList(list);
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PetVarietyBean bean=getAdapter().getItem(i);
                DataManager.getInstance().setObjectType(Constants.OBJECT_TYPE_PET_TYPE);
                DataManager.getInstance().setObject(mPetName+"_"+bean.getPetVarietyName()+"_"+bean.getPetType()+"_"+bean.getId());
                goBack();
            }
        });
    }

    private SelectPetTypeAdapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new SelectPetTypeAdapter(getActivity());
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }
}
