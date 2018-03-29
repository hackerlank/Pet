package com.gzcbkj.chongbao.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.adapter.ArticleCommentAdapter;
import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.net.URL;
import java.util.ArrayList;


/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class ArticleDetailFragment extends BaseFragment implements OnRefreshListener {
    private ArticleCommentAdapter mAdapter;
    private ArticleBean mArticleBean;
    private View mTopView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath().build());
        mArticleBean=(ArticleBean) getArguments().getSerializable(Constants.KEY_BASE_BEAN);
        setText(R.id.tvTitle,R.string.choice_article);
        setViewVisible(R.id.ivRight);
        setViewGone(R.id.status_bar);
        setImage(R.id.ivRight,R.drawable.more);
        setViewsOnClickListener(R.id.ivRight,R.id.tvComment);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        mTopView=LayoutInflater.from(getActivity()).inflate(R.layout.article_detail_top_layout,null);
        listView.addHeaderView(mTopView);
        initTopView();
        SmartRefreshLayout smartRefreshLayout = fv(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.autoRefresh();
    }

    private void initTopView(){
  //      Utils.loadImage(R.drawable.default_1,mArticleBean.getMainPic(),(ImageView)mTopView.findViewById(R.id.iv));

        Glide.with(BaseApplication.getAppContext()).load(mArticleBean.getMainPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if(getView()!=null && resource!=null){
                        ImageView iv = mTopView.findViewById(R.id.iv);
                        ViewGroup.LayoutParams lp = iv.getLayoutParams();
                        lp.width = getDisplaymetrics().widthPixels;
                        lp.height = lp.width * resource.getHeight() / resource.getWidth();
                        ((ImageView) mTopView.findViewById(R.id.iv)).setImageBitmap(resource);
                }
            }
        });
        Utils.loadImages(R.drawable.touxiang,mArticleBean.getUserHead(),(ImageView)mTopView.findViewById(R.id.ivAvater));
        ((TextView)mTopView.findViewById(R.id.tvName)).setText(mArticleBean.getUserName()==null?"":mArticleBean.getUserName());
        ((TextView)mTopView.findViewById(R.id.tvTime)).setText(Utils.transformTime(getActivity(),mArticleBean.getCreateTime()));
        ((ImageView)mTopView.findViewById(R.id.ivLike)).setImageResource(mArticleBean.getPraiseFlag()==0?R.drawable.like_grey:R.drawable.like);
        ((TextView)mTopView.findViewById(R.id.tvTitle)).setText(mArticleBean.getTitle());
        ((TextView)mTopView.findViewById(R.id.tvContent)).setText(Html.fromHtml(Utils.replaceHtmlText(mArticleBean.getContent()),mImgGetter,null));

        mTopView.findViewById(R.id.ivLike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpMethods.getInstance().updateArticle(2, mArticleBean.getId(), new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean o) {
                        if(mArticleBean.getPraiseFlag()==0) {
                            mArticleBean.setPraiseFlag(1);
                        }else{
                            mArticleBean.setPraiseFlag(0);
                        }
                        ((ImageView)mTopView.findViewById(R.id.ivLike)).setImageResource(mArticleBean.getPraiseFlag()==0?R.drawable.like_grey:R.drawable.like);
                    }
                }, getActivity(), false, (BaseActivity) getActivity()));
            }
        });
    }

    private ArticleCommentAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new ArticleCommentAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.tvComment:
                String content=getTextById(R.id.etComment).trim();
                if(TextUtils.isEmpty(content)){
                    showToast(R.string.comment_cannot_null);
                    return;
                }
                String text=getTextById(R.id.etComment);
                hideKeyBoard();
                setText(R.id.etComment,"");
                HttpMethods.getInstance().articleCommentSave(text,mArticleBean.getId(),
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(bean!=null && !TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                getComment(1,30);
                            }
                        },getActivity(),false,(BaseActivity)getActivity()));
                break;
        }
    }

    private void getComment(int page,int limit){
        HttpMethods.getInstance().articlecommentList(page,limit,mArticleBean.getId(),
                new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                    @Override
                    public void onNext(ResponseBean bean) {
                        if(bean!=null){
                            getAdapter().setDataList(bean.getArticleCommentList());
                        }
                    }
                },getActivity(),false,(BaseActivity)getActivity()));
    }

    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        HttpMethods.getInstance().articleInfo(mArticleBean.getId(),new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
            @Override
            public void onNext(ResponseBean bean) {
                if(getView()==null){
                    return;
                }
                mArticleBean=bean.getArticle();
                refreshlayout.finishRefresh();
                initTopView();
            }
        },getActivity(),false,(BaseActivity)getActivity()));
        getComment(1,30);
    }

    Html.ImageGetter mImgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                ArrayList<String> list=Utils.getUrlList(source);
                url = new URL(list.get(0));
                drawable = Drawable.createFromStream(url.openStream(), "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            if(drawable==null){
                drawable=getResources().getDrawable(R.drawable.default_1);
            }
            int width=getDisplaymetrics().widthPixels-Utils.dip2px(getActivity(),30);
            int height=drawable.getIntrinsicHeight()*width/drawable.getIntrinsicWidth();
            drawable.setBounds(0, 0, width, height);
            return drawable;
        }
    };
}
