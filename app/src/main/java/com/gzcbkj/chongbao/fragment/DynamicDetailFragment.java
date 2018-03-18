package com.gzcbkj.chongbao.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.DynamicCommentAdapter;
import com.gzcbkj.chongbao.adapter.LikeDynamicAdapter;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayBean;
import com.gzcbkj.chongbao.bean.SayDetailResponse;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.DynamicDetailPhotosView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class DynamicDetailFragment extends BaseFragment implements OnRefreshListener {
    private DynamicCommentAdapter mAdapter;
    private LikeDynamicAdapter mLikeDynamicAdapter;
    private SayBean mSayBean;
    private ArrayList<SayDetailResponse.PraiseUser> mSayPraiseList;
    private View mTopView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.detail);
        mSayBean=(SayBean) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
        mTopView= LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_detail_top_layout,null);
        listView.addHeaderView(mTopView);
        initTopView();
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    private void initTopView(){
        RecyclerView recyclerView=mTopView.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setAdapter(getLikeDynamicAdapter());
        getLikeDynamicAdapter().setDataList(mSayPraiseList);
        Utils.loadImages(R.drawable.touxiang,mSayBean.getUserHead(),(ImageView)mTopView.findViewById(R.id.ivAvater));
        ((TextView)mTopView.findViewById(R.id.tvName)).setText(mSayBean.getUserName());
        ((TextView)mTopView.findViewById(R.id.tvContent)).setText(mSayBean.getContent());
        ((TextView)mTopView.findViewById(R.id.tvTime)).setText(mSayBean.getCreateTime());
        ((DynamicDetailPhotosView)mTopView.findViewById(R.id.dynamicDetailPhotosView)).setImageList(mSayBean.getSayImgList());
    }

    private DynamicCommentAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new DynamicCommentAdapter(getActivity());
        return mAdapter;
    }

    private LikeDynamicAdapter getLikeDynamicAdapter(){
        if(mLikeDynamicAdapter==null)
            mLikeDynamicAdapter=new LikeDynamicAdapter(getActivity());
        return mLikeDynamicAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().querySayDetail(mSayBean.getId(),new ProgressSubscriber(new SubscriberOnNextListener<SayDetailResponse>() {
            @Override
            public void onNext(SayDetailResponse bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
                mSayBean=bean.getUserSayEntity();
                mSayPraiseList=bean.getSayPraiseList();
                initTopView();
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }
}
