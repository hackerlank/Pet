package com.gzcbkj.chongbao.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.PetFindorlostInfo;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.PagerDotView;
import com.gzcbkj.chongbao.widgets.RoundRectImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetDetailFragment extends BaseFragment implements OnRefreshListener {

    private PetFindorlostInfo mBean;
    private int mFindOrLostType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_shide_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.pet_detail);
        mBean = (PetFindorlostInfo) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
        mFindOrLostType=getArguments().getInt(Constants.KEY_BASE_BEAN_2,2);
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void updateUIText() {
        if (mBean == null) {
            return;
        }
        if(mFindOrLostType==1){
            setText(R.id.tvTitle1,R.string.zoushi_time);
            setText(R.id.tvTitle2,R.string.zoushi_address);
        }else{
            setText(R.id.tvTitle1,R.string.shide_time);
            setText(R.id.tvTitle2,R.string.shide_address);
        }
        ConvenientBanner convenientBanner = fv(R.id.convenientBanner);
        final PagerDotView pagerDotView = fv(R.id.pagerDotView);
        ArrayList<String> list = new ArrayList<>();
        String[] urls = mBean.getFindorlostLmg().split(",");
        if (urls != null && urls.length > 1) {
            for (String url : urls) {
                if(url.startsWith("http")) {
                    list.add(url);
                }else if(list.size()>0){
                    String lastUrl=list.get(list.size()-1)+","+url;
                    list.set(list.size()-1,lastUrl);
                }
            }
        } else {
            list.add(mBean.getFindorlostLmg());
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
        setText(R.id.tvTime, mBean.getFindorlostTime());
        setImage(R.id.ivPetSex, "1".equals(mBean.getFindorlostPetSex()) ? R.drawable.male : R.drawable.female);
        setText(R.id.tvAddress, mBean.getFindorlostAddress());
        setText(R.id.tvPetType, mBean.getPetVarietyName());
        setText(R.id.tvPetMiaoshu, mBean.getFindorlostRemake());
        setText(R.id.tvContactPerson, mBean.getFindorlostPeopleName());
        setText(R.id.tvContactPhone, mBean.getFindorlostPeoplePhone());
    }

    @Override
    public void onClick(View view) {

    }

    private class LocalImageHolderView implements Holder<String> {
        private RoundRectImageView ivImage;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.item_pet_image, null);
            ivImage = view.findViewById(R.id.ivImage);
            ivImage.setOnlyTopRound(false);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String petUrl) {
            Utils.loadImages(R.drawable.default_1, petUrl, ivImage);
        }
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().findorlostInfoById(mBean.getId(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
                mBean=bean.getFindorlostInfo();
                updateUIText();
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }
}
