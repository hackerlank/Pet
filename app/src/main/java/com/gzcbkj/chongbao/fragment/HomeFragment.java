package com.gzcbkj.chongbao.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.bean.BannerBean;
import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class HomeFragment extends BaseFragment implements OnRefreshListener {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated(View view) {
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        setViewsOnClickListener(R.id.ivAdvisory,R.id.ivSearch,R.id.tvChangeABatch);
        LinearLayout llInformation=view.findViewById(R.id.llInformation);
        int count=llInformation.getChildCount();
        View itemView;
        for(int i=0;i<count;++i){
            itemView=llInformation.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag=(int)view.getTag();
                    Bundle bundle=new Bundle();
                    bundle.putInt(Constants.KEY_BASE_BEAN,tag);
                    gotoPager(InformationFragment.class,bundle);
                }
            });
        }
        smartRefreshLayout.autoRefresh();
        //addArticles();
    }

    private void addBannerList(ConvenientBanner convenientBanner,ArrayList<BannerBean> list){
            convenientBanner.setCanLoop(list.size() > 1);
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
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
    }

    private void addChoiceArticles(ArrayList<ArticleBean> list){
        LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
        LinearLayout ll=fv(R.id.llChoiceArticle);
        int count=ll.getChildCount();
        for(int i=0;i<count;++i){
            ll.getChildAt(i).setVisibility(View.GONE);
        }
        if(list!=null) {
            count = list.size();
            View itemView;
            ArticleBean bean;
            for (int i = 0; i < count; ++i) {
                if (i < ll.getChildCount()) {
                    itemView = ll.getChildAt(i);
                    itemView.setVisibility(View.VISIBLE);
                } else {
                    itemView = layoutInflater.inflate(R.layout.item_choice_article, null);
                    ll.addView(itemView);
                }
                bean=list.get(i);
                Log.e("aaaaaa",bean.getMainPic()+"\n"+bean.getUserHead());
                Utils.loadImage(R.drawable.default_1,bean.getMainPic(),(ImageView)itemView.findViewById(R.id.ivBg));
                Utils.loadImage(R.drawable.touxiang,bean.getUserHead(),(ImageView)itemView.findViewById(R.id.ivAvater));
                setText((TextView)itemView.findViewById(R.id.tvContent),bean.getContent());
                setText((TextView)itemView.findViewById(R.id.tvName),bean.getUserName());
                setText((TextView)itemView.findViewById(R.id.tvTime),bean.getCreateTime());
            }
        }


    }

    private void addOtherArticle(ArticleListResponse response){
        LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
        LinearLayout ll=fv(R.id.llChoiceArticle);
        int count=ll.getChildCount();
        for(int i=0;i<count;++i){
            ll.getChildAt(i).setVisibility(View.GONE);
        }
        ArrayList<ArticleBean> list=response.getArticleList();
        if(list!=null) {
            count = list.size();
            View itemView;
            ArticleBean bean;
            for (int i = 0; i < count; ++i) {
                if (i < ll.getChildCount()) {
                    itemView = ll.getChildAt(i);
                    itemView.setVisibility(View.VISIBLE);
                } else {
                    itemView = layoutInflater.inflate(R.layout.item_other_article, null);
                    ll.addView(itemView);
                }
                bean=list.get(i);
                Utils.loadImage(R.drawable.default_1,bean.getMainPic(),(ImageView)fv(R.id.ivBg));
                Utils.loadImage(R.drawable.touxiang,bean.getUserHead(),(ImageView)fv(R.id.ivAvater));
                setText((TextView)fv(R.id.tvContent),bean.getContent());
                setText((TextView)fv(R.id.tvName),bean.getUserName());
                setText((TextView)fv(R.id.tvTime),bean.getCreateTime());
            }
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivAdvisory:
                gotoPager(RaisePetAdvisoryFragment.class,null);
                break;
            case R.id.ivSearch:
                gotoPager(SearchFragment.class,null);
                break;
            case R.id.tvChangeABatch:
                firstArticleList();
                break;
        }
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        getBannerList(refreshlayout);
        firstArticleList();
    }

    private void getBannerList(final RefreshLayout refreshlayout){
        HttpMethods.getInstance().bannerList(1,new ProgressSubscriber(new SubscriberOnNextListener<BannerListResponse>() {
            @Override
            public void onNext(BannerListResponse response) {
                if(getView()!=null){
                    refreshlayout.finishRefresh();
                    addBannerList((ConvenientBanner) fv(R.id.convenientBanner),response.getBannerList1());
                    addChoiceArticles(response.getArticleList());
                }
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }

    private void firstArticleList(){
        HttpMethods.getInstance().firstArticleList(1,3,new ProgressSubscriber(new SubscriberOnNextListener<ArticleListResponse>() {
            @Override
            public void onNext(ArticleListResponse response) {

            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }

    private class LocalImageHolderView implements Holder<BannerBean> {
        private ImageView ivImage;
        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.item_banner, null);
            ivImage = view.findViewById(R.id.ivImage);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean bean) {
            Utils.loadImage(R.drawable.default_1,bean.getImgUrl(),ivImage);
        }
    }
}
