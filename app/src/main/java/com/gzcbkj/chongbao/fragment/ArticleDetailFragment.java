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
import com.gzcbkj.chongbao.adapter.DynamicCommentAdapter;
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
    private DynamicCommentAdapter mAdapter;
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
        setImage(R.id.ivRight,R.drawable.more);
        setViewsOnClickListener(R.id.ivRight);
        ListView listView = fv(R.id.listView);
        listView.setAdapter(getAdapter());
        ArrayList<ResponseBean> list=new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new ResponseBean());
        }
        getAdapter().setDataList(list);
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
        if(!TextUtils.isEmpty(mArticleBean.getUserHead())){
            Utils.loadImage(R.drawable.touxiang,mArticleBean.getUserHead(),(ImageView)mTopView.findViewById(R.id.ivAvater));
        }else{
            ((ImageView)mTopView.findViewById(R.id.ivAvater)).setImageResource(R.drawable.touxiang);
        }
        ((TextView)mTopView.findViewById(R.id.tvName)).setText(mArticleBean.getUserName()==null?"":mArticleBean.getUserName());
        ((TextView)mTopView.findViewById(R.id.tvTime)).setText(mArticleBean.getCreateTime());
        ((ImageView)mTopView.findViewById(R.id.ivLike)).setImageResource(mArticleBean.getPraiseFlag()==0?R.drawable.like_grey:R.drawable.like);
        ((TextView)mTopView.findViewById(R.id.tvTitle)).setText(mArticleBean.getTitle());
        //<p>关于他在场上最合适的位置突然成为了外界讨论的焦点。就在此时，阿扎尔在主场迎战水晶宫的比赛中如脱胎换骨一般焕发了往日的风采，他在前场如鱼得水，盘活了蓝军的进攻体系，而唯一的变化便是他重新回到了左边锋的位置上。</p><p><img src="http://n.sinaimg.cn/sports/transform/w638h423/20180311/bl8J-fxpwyhw9073041.png"/></p><p>客观的说，切尔西在此前几场比赛中，接连与巴塞罗那、曼城这样善于传控的球队进行比拼，孔蒂在舍弃对球权的控制后安排小快灵的三叉戟在前场寻求反击，也是无可厚非的决定。</p>
        ((TextView)mTopView.findViewById(R.id.tvContent)).setText(Html.fromHtml(mArticleBean.getContent(),mImgGetter,null));
    }

    private DynamicCommentAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter=new DynamicCommentAdapter(getActivity());
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
        HttpMethods.getInstance().articleInfo(mArticleBean.getId(),new ProgressSubscriber(new SubscriberOnNextListener<ArticleBean>() {
            @Override
            public void onNext(ArticleBean bean) {
                if(getView()==null){
                    return;
                }
                refreshlayout.finishRefresh();
                initTopView();
            }
        },getActivity(),false,(BaseActivity)getActivity()));
    }

    Html.ImageGetter mImgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
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
