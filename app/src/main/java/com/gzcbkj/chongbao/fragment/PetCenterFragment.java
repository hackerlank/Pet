package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.utils.Utils;

import java.util.ArrayList;

import okhttp3.internal.Util;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetCenterFragment extends BaseFragment {

    public static final int PET_LINGYANG_INDEX = 0;
    public static final int PET_SHIDE_INDEX = 1;
    public static final int PET_ZOUSHI_INDEX = 2;
    public static final int PET_JIYANG_INDEX = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_center;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.pet_center);
        setViewInvisible(R.id.ivLeft);
        initViewPager();
        setViewsOnClickListener(R.id.llTab1, R.id.llTab2, R.id.llTab3, R.id.llTab4,R.id.tvRight);
    }

    private void initViewPager() {
        ViewPager viewPager = fv(R.id.viewPager);
        final ArrayList<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            fragments.add(new PetCenterItemFragment().setItemIndex(i));
        }
        fragments.add(new PetJiyangFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
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
        TextView tvRight=fv(R.id.tvRight);
        for (int i = 0; i < 4; ++i) {
            viewGroup = fv(getResources().getIdentifier("llTab" + (i + 1), "id", getActivity().getPackageName()));
            ((TextView) viewGroup.getChildAt(0)).setTextColor(getResources().getColor(i == pos ? R.color.color_33_33_33 : R.color.color_99_99_99));
            viewGroup.getChildAt(1).setVisibility(i == pos ? View.VISIBLE : View.GONE);
        }
        if(pos==1 || pos==2){
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(getString(R.string.publish));
            tvRight.setBackgroundResource(R.drawable.bg_lingyang_status);
            int padding1=Utils.dip2px(getActivity(),15);
            int padding2=Utils.dip2px(getActivity(),5);
            tvRight.setPadding(padding1,padding2,padding1,padding2);
            tvRight.setGravity(Gravity.CENTER);
            tvRight.setTextColor(Color.WHITE);
        }else{
            tvRight.setVisibility(View.GONE);
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
            case R.id.llTab3:
                ((ViewPager)fv(R.id.viewPager)).setCurrentItem(2);
                break;
            case R.id.llTab4:
                ((ViewPager)fv(R.id.viewPager)).setCurrentItem(3);
                break;
            case R.id.tvRight:
                gotoPager(((ViewPager)fv(R.id.viewPager)).getCurrentItem()==1?PetShideGaoshiFragment.class:PetDiushiGaoshiFragment.class,null);
                break;
        }
    }
}
