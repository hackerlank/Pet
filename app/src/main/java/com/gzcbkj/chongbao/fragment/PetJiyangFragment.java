package com.gzcbkj.chongbao.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.PetJiyangHJAdapter;
import com.gzcbkj.chongbao.adapter.PetJiyangPhotoAdapter;
import com.gzcbkj.chongbao.bean.BannerBean;
import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.bean.PetJiyangResponseBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.PagerDotView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/2/27.
 */

public class PetJiyangFragment extends BaseFragment implements OnRefreshListener {

    private PetJiyangHJAdapter mAdapter1;
    private PetJiyangPhotoAdapter mAdapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pet_jiyang;
    }

    @Override
    protected void onViewCreated(View view) {
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        setViewsOnClickListener(R.id.tvAplly);
        addBannerList((ArrayList<BannerBean>) DataManager.getInstance().getDate("petjiyang_bannerlist", new TypeToken<ArrayList<BannerBean>>() {
        }.getType()));
        initPetJiyangView((PetJiyangResponseBean) DataManager.getInstance().getDate("PetJiyangResponse", PetJiyangResponseBean.class));
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvAplly:
                break;
        }
    }

    private void initPetJiyangView(PetJiyangResponseBean bean) {
        if (bean == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        ArrayList<PetJiyangResponseBean.BaseRoom> roomList = bean.getBaseRoomList();
        if (roomList != null) {
            LinearLayout llRoomJs = fv(R.id.llRoomJs);
            llRoomJs.removeAllViews();
            View itemView;
            for (PetJiyangResponseBean.BaseRoom room : roomList) {
                itemView = inflater.inflate(R.layout.item_jiyang_service, null);
                llRoomJs.addView(itemView);
                ((TextView) itemView.findViewById(R.id.tv1)).setText(room.getBaseRoomName());
                ((TextView) itemView.findViewById(R.id.tv2)).setText(getString(R.string.xx_price, room.getBaseRoomPrice()));
            }
        }

        ArrayList<PetJiyangResponseBean.BaseService> serviceList = bean.getBaseServiceList();
        if (serviceList != null) {
            LinearLayout llQitaFw = fv(R.id.llQitaFw);
            llQitaFw.removeAllViews();
            View itemView;
            for (PetJiyangResponseBean.BaseService service : serviceList) {
                itemView = inflater.inflate(R.layout.item_jiyang_service, null);
                llQitaFw.addView(itemView);
                ((TextView) itemView.findViewById(R.id.tv1)).setText(service.getBaseServiceName());
                ((TextView) itemView.findViewById(R.id.tv2)).setText(getString(R.string.xx_price, service.getBaseServicePrice()));
            }
        }

        ArrayList<PetJiyangResponseBean.PetBase> petBaseList = bean.getPetBaseList();
        if (petBaseList != null && !petBaseList.isEmpty()) {
            PetJiyangResponseBean.PetBase petBase = petBaseList.get(0);
            RecyclerView recyclerView1 = fv(R.id.recyclerView1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView1.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            recyclerView1.setAdapter(getAdapter1());
            getAdapter1().setData(petBase);

            RecyclerView recyclerView2 = fv(R.id.recyclerView2);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView2.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            recyclerView2.setAdapter(getAdapter2());
            getAdapter2().setData(Utils.getUrlList(petBase.getAdoptPetPic()));
            setText(R.id.tvJidims, petBase.getRemake());
        }

    }

    private PetJiyangHJAdapter getAdapter1() {
        if (mAdapter1 == null)
            mAdapter1 = new PetJiyangHJAdapter(getActivity());
        return mAdapter1;
    }

    private PetJiyangPhotoAdapter getAdapter2() {
        if (mAdapter2 == null)
            mAdapter2 = new PetJiyangPhotoAdapter(getActivity());
        return mAdapter2;
    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().fosterPetList(1, 30, new ProgressSubscriber(new SubscriberOnNextListener<PetJiyangResponseBean>() {
            @Override
            public void onNext(PetJiyangResponseBean bean) {
                if (getView() == null || bean == null) {
                    return;
                }
                DataManager.getInstance().saveData("PetJiyangResponse", bean);
                initPetJiyangView(bean);
                refreshlayout.finishRefresh();
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
        }));
        HttpMethods.getInstance().bannerList(4, new ProgressSubscriber(new SubscriberOnNextListener<BannerListResponse>() {
            @Override
            public void onNext(BannerListResponse bean) {
                if (bean != null) {
                    DataManager.getInstance().saveData("petjiyang_bannerlist", bean.getBannerList1());
                    addBannerList(bean.getBannerList1());
                }
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }

    private void addBannerList(ArrayList<BannerBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        ConvenientBanner convenientBanner = fv(R.id.convenientBanner);
        final PagerDotView pagerDotView = fv(R.id.pagerDotView);
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
