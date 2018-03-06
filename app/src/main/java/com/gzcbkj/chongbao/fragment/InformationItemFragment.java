package com.gzcbkj.chongbao.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.ArticleAdapter;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class InformationItemFragment extends BaseFragment implements OnRefreshListener {

    private String mArticleType;

    private ArticleAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.item_friend_other;
    }

    @Override
    protected void onViewCreated(View view) {
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        if(getAdapter().getCount()==0) {
            smartRefreshLayout.autoRefresh();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public InformationItemFragment setArticleType(String articleType){
        mArticleType=articleType;
        return this;
    }

    private ArticleAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new ArticleAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().articleList(1, 10,mArticleType,
        new ProgressSubscriber(new SubscriberOnNextListener<ArticleListResponse>() {
            @Override
            public void onNext(ArticleListResponse response) {
                refreshlayout.finishRefresh();
                DataManager.getInstance().saveData(ArticleListResponse.class.getName(), response);
                if (getView() != null) {
                    getAdapter().setDataList(response.getArticleList());
                }
            }
        }, getActivity(), false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                refreshlayout.finishRefresh();
           //     ((BaseActivity) getActivity()).onConnectError(e);
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                refreshlayout.finishRefresh();
           //     ((BaseActivity) getActivity()).onServerError(errorCode,errorMsg);
            }
        }));
    }
}
