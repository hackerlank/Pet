package com.gzcbkj.chongbao.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class OrderListFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.order);
        initViewPager();
        setViewsOnClickListener(R.id.llTab1,R.id.llTab2,R.id.llTab3,R.id.llTab4);
    }

    private void initViewPager(){
        ViewPager viewPager=fv(R.id.viewPager);
        final ArrayList<OrderFragment> fragments=new ArrayList<>();
        for(int i=0;i<4;++i){
            OrderFragment orderFragment=new OrderFragment();
            orderFragment.setOrderTye(i);
            fragments.add(orderFragment);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
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

    private void initTab(int pos){
        ViewGroup viewGroup;
        for(int i=0;i<4;++i){
            viewGroup=fv(getResources().getIdentifier("llTab"+(i+1),"id",getActivity().getPackageName()));
            ((TextView) viewGroup.getChildAt(0)).setTextColor(getResources().getColor(i==pos?R.color.color_33_33_33:R.color.color_99_99_99));
            viewGroup.getChildAt(1).setVisibility(i==pos?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivRight:
                break;
            case R.id.llTab1:
                ViewPager viewPager=fv(R.id.viewPager);
                viewPager.setCurrentItem(0);
                break;
            case R.id.llTab2:
                viewPager=fv(R.id.viewPager);
                viewPager.setCurrentItem(1);
                break;
            case R.id.llTab3:
                viewPager=fv(R.id.viewPager);
                viewPager.setCurrentItem(2);
                break;
            case R.id.llTab4:
                viewPager=fv(R.id.viewPager);
                viewPager.setCurrentItem(3);
                break;
        }
    }
}
