package com.gzcbkj.chongbao.fragment;

import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.PetVarietyBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetSelectTypeFragment extends BaseFragment  implements OnRefreshListener {

    private ArrayList<PetSelectTypeItemFragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_pet_type;
    }




    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.shide_pet_type);
        initViewPager();
        setViewsOnClickListener(R.id.llTab1, R.id.llTab2);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
    }

    private void initViewPager() {
        ViewPager viewPager = fv(R.id.viewPager);
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; ++i) {
            mFragments.add(new PetSelectTypeItemFragment());
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        initTab(0);
    }

    private void initTab(int pos) {
        ViewGroup viewGroup;
        for (int i = 0; i < 2; ++i) {
            viewGroup = fv(getResources().getIdentifier("llTab" + (i + 1), "id", getActivity().getPackageName()));
            ((TextView) viewGroup.getChildAt(0)).setTextColor(getResources().getColor(i == pos ? R.color.color_33_33_33 : R.color.color_99_99_99));
            viewGroup.getChildAt(1).setVisibility(i == pos ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void updateUIText() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llTab1:
                ((ViewPager)fv(R.id.viewPager)).setCurrentItem(0);
                break;
            case R.id.llTab2:
                ((ViewPager)fv(R.id.viewPager)).setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        ProgressSubscriber subscriber = new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                if (bean == null) {
                    return;
                }
                setText(R.id.tv1,bean.getPetTypeList().get(0).getTypeName());
                setText(R.id.tv2,bean.getPetTypeList().get(1).getTypeName());
                ArrayList<PetVarietyBean> list1=new ArrayList<>();
                ArrayList<PetVarietyBean> list2=new ArrayList<>();
                for(PetVarietyBean petVarietyBean:bean.getPetVarietyList()){
                    if(petVarietyBean.getPetType()==bean.getPetTypeList().get(0).getId()){
                        list1.add(petVarietyBean);
                    }else if(petVarietyBean.getPetType()==bean.getPetTypeList().get(1).getId()){
                        list2.add(petVarietyBean);
                    }
                }
                mFragments.get(0).setPetName(bean.getPetTypeList().get(0).getTypeName());
                mFragments.get(1).setPetName(bean.getPetTypeList().get(1).getTypeName());
                mFragments.get(0).setData(list1);
                mFragments.get(1).setData(list2);
            }
        }, getActivity(), false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).connectError(e);
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                if (getView() == null) {
                    return;
                }
                refreshlayout.finishRefresh();
                ((BaseActivity) getActivity()).serverError(errorCode, errorMsg);
            }
        });
        HttpMethods.getInstance().queryTypeInfoList(subscriber);
    }
}
