package com.gzcbkj.chongbao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.ArticleAdapter;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.bean.BannerBean;
import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.http.HttpMethods;
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

public class HomeFragment extends BaseFragment implements OnRefreshListener {
    private ArticleAdapter mAdapter;
    private  View mTopView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.ivAdvisory, R.id.ivSearch);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        mTopView=LayoutInflater.from(getActivity()).inflate(R.layout.layout_top_home,null);
        listView.addHeaderView(mTopView);
        initTopLayout();
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    private void initTopLayout(){
        mTopView.findViewById(R.id.tvChangeABatch).setOnClickListener(this);
        LinearLayout llInformation = mTopView.findViewById(R.id.llInformation);
        int count = llInformation.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llInformation.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.KEY_BASE_BEAN, tag);
                    gotoPager(InformationFragment.class, bundle);
                }
            });
        }
        BannerListResponse response=(BannerListResponse)DataManager.getInstance().getDate(BannerListResponse.class.getName(),BannerListResponse.class);
        if(response!=null) {
            addBannerList((ConvenientBanner) mTopView.findViewById(R.id.convenientBanner1), (PagerDotView) mTopView.findViewById(R.id.pagerDotView1), response.getBannerList1());
            addBannerList((ConvenientBanner) mTopView.findViewById(R.id.convenientBanner2), (PagerDotView) mTopView.findViewById(R.id.pagerDotView2), response.getBannerList2());
            addChoiceArticles(response.getArticleList());
        }

        ArticleListResponse response1=(ArticleListResponse)DataManager.getInstance().getDate(ArticleListResponse.class.getName(),ArticleListResponse.class);
        if(response1!=null) {
            getAdapter().setDataList(response1.getArticleList());
        }
        if(response==null || response1==null){
            ((SmartRefreshLayout) fv(R.id.smartLayout)).autoRefresh();
        }
    }

    private ArticleAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new ArticleAdapter(getActivity());
        return mAdapter;
    }

    private void addBannerList(ConvenientBanner convenientBanner, final PagerDotView pagerDotView,
                               ArrayList<BannerBean> list) {
        if (list.size() > 1) {
            convenientBanner.startTurning(3000);
            convenientBanner.setCanLoop(true);
            pagerDotView.setVisibility(View.VISIBLE);
            pagerDotView.setTotalPage(list.size());
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
    }

    private void addChoiceArticles(ArrayList<ArticleBean> list) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        LinearLayout ll = mTopView.findViewById(R.id.llChoiceArticle);
        int count = ll.getChildCount();
        for (int i = 0; i < count; ++i) {
            ll.getChildAt(i).setVisibility(View.GONE);
        }
        if (list != null) {
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
                bean = list.get(i);
                Utils.loadImage(R.drawable.default_1, bean.getMainPic(), (ImageView) itemView.findViewById(R.id.ivBg));
                Utils.loadImage(R.drawable.touxiang, bean.getUserHead(), (ImageView) itemView.findViewById(R.id.ivAvater));
                ((TextView) itemView.findViewById(R.id.tvContent)).setText(Html.fromHtml(Utils.replaceHtmlText(bean.getContent())));
                setText((TextView) itemView.findViewById(R.id.tvName), bean.getUserName());
                setText((TextView) itemView.findViewById(R.id.tvTime), bean.getCreateTime());
            }
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivAdvisory:
                gotoPager(RaisePetAdvisoryFragment.class, null);
                break;
            case R.id.ivSearch:
                gotoPager(SearchFragment.class, null);
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

    private void getBannerList(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().bannerList(1, new ProgressSubscriber(new SubscriberOnNextListener<BannerListResponse>() {
            @Override
            public void onNext(BannerListResponse response) {
                DataManager.getInstance().saveData(BannerListResponse.class.getName(),response);
                if (getView() != null) {
                    refreshlayout.finishRefresh();
                    addBannerList((ConvenientBanner) mTopView.findViewById(R.id.convenientBanner1),
                            (PagerDotView) mTopView.findViewById(R.id.pagerDotView1), response.getBannerList1());
                    addBannerList((ConvenientBanner) mTopView.findViewById(R.id.convenientBanner2),
                            (PagerDotView) mTopView.findViewById(R.id.pagerDotView2), response.getBannerList2());
                    addChoiceArticles(response.getArticleList());
                }
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }

    private void firstArticleList() {
        HttpMethods.getInstance().firstArticleList(1, 10, new ProgressSubscriber(new SubscriberOnNextListener<ArticleListResponse>() {
            @Override
            public void onNext(ArticleListResponse response) {
                DataManager.getInstance().saveData(ArticleListResponse.class.getName(),response);
                if(getView()!=null) {
                    getAdapter().setDataList(response.getArticleList());
                }
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
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
            Utils.loadImage(R.drawable.default_1, bean.getImgUrl(), ivImage);
        }
    }
}
