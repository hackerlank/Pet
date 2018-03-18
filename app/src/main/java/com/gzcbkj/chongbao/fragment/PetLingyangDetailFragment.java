package com.gzcbkj.chongbao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.AdoptPetBean;
import com.gzcbkj.chongbao.bean.BannerBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.PagerDotView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetLingyangDetailFragment extends BaseFragment implements OnRefreshListener {

    private AdoptPetBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_lingyang_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.lingyang_detail);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.autoRefresh();
        setViewsOnClickListener(R.id.tvLingyang);
        mBean = (AdoptPetBean) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
        if(mBean==null){
            goBack();
        }
    }

    @Override
    public void updateUIText() {
        if(mBean==null){
            return;
        }
        ConvenientBanner convenientBanner=fv(R.id.convenientBanner);
        final PagerDotView pagerDotView=fv(R.id.pagerDotView);
        ArrayList<String> list=new ArrayList<>();
        String[] urls=mBean.getPetHeadUrl().split(",");
        if (urls!=null && urls.length > 1) {
            for(String url:urls){
                list.add(url);
            }
        }else{
            list.add(mBean.getPetHeadUrl());
        }
        if (list.size() > 1) {
            convenientBanner.startTurning(3000);
            convenientBanner.setCanLoop(true);
            pagerDotView.setVisibility(View.VISIBLE);
            pagerDotView.setTotalPage(urls.length);
        } else {
            convenientBanner.stopTurning();
            convenientBanner.setCanLoop(false);
            pagerDotView.setVisibility(View.GONE);
        }
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, list)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
        convenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pagerDotView.setCurrentPageIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setText(R.id.tvPetName,mBean.getPetName());
        setImage(R.id.ivPetSex,"1".equals(mBean.getPetSex())?R.drawable.male:R.drawable.female);
        setText(R.id.tvPetType,mBean.getPetVarietyNmae());
        setText(R.id.tvPetName2,mBean.getPetAge());
        setText(R.id.tvPetSex,getString("1".equals(mBean.getPetSex())?R.string.male:R.string.female));
        setText(R.id.tvPetAge,mBean.getPetAge()+getString(R.string.age));
        if(1==mBean.getPetStatus()){
            setText(R.id.tvPetStatus, R.string.waiting_lingtang);
   //         setViewVisible(R.id.tvLingyang);
        }else{
            setText(R.id.tvPetStatus, R.string.had_lingyang);
 //           setViewGone(R.id.tvLingyang);
        }
        setText(R.id.tvPetLingyangRequire,mBean.getPetRequirement());
        setText(R.id.tvPetJieshao,mBean.getRemake());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvLingyang:
                if(!DataManager.getInstance().isLogin()){
                    gotoPager(LoginFragment.class,null);
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constants.KEY_BASE_BEAN,mBean);
                gotoPager(PetLingyangziliaoFragment.class, bundle);
                break;
        }
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().tobeAdoptInfo(mBean.getId(), new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
            }
        }, getActivity(), false, (BaseActivity)getActivity()));
    }

    private class LocalImageHolderView implements Holder<String> {
        private ImageView ivImage;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.item_pet_image, null);
            ivImage = view.findViewById(R.id.ivImage);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String petUrl) {
            Utils.loadImages(R.drawable.default_1, petUrl, ivImage);
        }
    }
}
