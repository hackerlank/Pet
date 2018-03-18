package com.gzcbkj.chongbao.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishArticleFrigment extends BaseFragment {

    private String mMainPicUrl;
    private File mMainPicFile;
    private String mArticleType;

    private int mSelectPicFor=0;//1为主封面，2为内容
    private int mSelectContentPicIndex;

    private ArrayList<ArticleContent> mArticleContentList;

    private ArrayList<ArticleContent> getArticleContentList(){
        if(mArticleContentList==null){
            mArticleContentList=new ArrayList<>();
        }
        return mArticleContentList;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_article;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.publish_article);
        TextView tvRight=fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        setText(tvRight,getString(R.string.publish));
        tvRight.setBackgroundResource(R.drawable.publish_bg);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setTextColor(Color.WHITE);
        tvRight.setOnClickListener(this);
        setViewGone(R.id.status_bar);
        setViewsOnClickListener(R.id.rlSelectType,R.id.llArticlePic,R.id.ivArticlePic,R.id.ivClose);
        addContentItem();
    }

    private void addContentItem(){
        LinearLayout llContent=fv(R.id.llContent);
        View itemView= LayoutInflater.from(getActivity()).inflate(R.layout.item_add_article_content,null);
        llContent.addView(itemView);
        int pos=llContent.getChildCount()-1;
        ImageView ivAddPic=itemView.findViewById(R.id.ivAddPic);
        ivAddPic.setTag(R.id.tag,pos);
        ivAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag(R.id.tag);
                mSelectContentPicIndex=pos;
                mSelectPicFor=2;
                ((BaseActivity) getActivity()).showSelectPhotoWindow();
            }
        });
        ImageView ivAddContent=itemView.findViewById(R.id.ivAddContent);
        ivAddContent.setTag(R.id.tag,pos);
        ivAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContentItem();
            }
        });
        ArticleContent content=new ArticleContent();
        getArticleContentList().add(content);

        final ScrollView scrollView=fv(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (DataManager.getInstance().getObject() != null) {
            if (DataManager.getInstance().getObject() instanceof Bitmap) {
                String fileStr = Utils.saveJpeg((Bitmap) DataManager.getInstance().getObject(), getActivity());
                if(mSelectPicFor==1){
                    final ArrayList<File> list=new ArrayList<>();
                    list.add(new File(fileStr));
                    HttpMethods.getInstance().uploadFile(list,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                        @Override
                        public void onNext(ResponseBean bean) {
                            if(bean!=null && !TextUtils.isEmpty(bean.getMsg())){
                                mMainPicFile=list.get(0);
                                mMainPicUrl=bean.getMsg();
                                setViewVisible(R.id.ivClose,R.id.ivArticlePic);
                                Utils.loadImage(R.drawable.default_1, Uri.fromFile(mMainPicFile),(ImageView)fv(R.id.ivArticlePic));
                            }
                        }
                    },getActivity(),(BaseActivity)getActivity()));
                }else if(mSelectPicFor==2){
                    final ArrayList<File> list=new ArrayList<>();
                    list.add(new File(fileStr));
                    HttpMethods.getInstance().uploadFile(list,new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                        @Override
                        public void onNext(ResponseBean bean) {
                            if(bean!=null && !TextUtils.isEmpty(bean.getMsg())){
                                ArticleContent contentBean=getArticleContentList().get(mSelectContentPicIndex);
                                if(contentBean.imgUrls==null){
                                    contentBean.imgUrls=new ArrayList<>();
                                }
                                if(contentBean.picFiles==null){
                                    contentBean.picFiles=new ArrayList<>();
                                }
                                contentBean.picFiles.add(list.get(0));
                                contentBean.imgUrls.add(bean.getMsg());
                                addContentPicture(contentBean);
                            }
                        }
                    },getActivity(),(BaseActivity)getActivity()));
                }
                DataManager.getInstance().setObject(null);
            }
        }
    }

    private void addContentPicture(ArticleContent contentBean){
        LinearLayout llContent=fv(R.id.llContent);
        View itemView=llContent.getChildAt(mSelectContentPicIndex);
        LinearLayout llPhotos=itemView.findViewById(R.id.llPhotos);
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_add_article_content_photo,null);
        llPhotos.addView(view);
        ImageView ivPhoto=view.findViewById(R.id.ivPhoto);
        ImageView ivClose=view.findViewById(R.id.ivClose);
        int pos=contentBean.picFiles.size()-1;
        Utils.loadImage(R.drawable.default_1,Uri.fromFile(contentBean.picFiles.get(pos)),ivPhoto);
        ivClose.setTag(contentBean);
        ivClose.setTag(R.id.tag,pos);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleContent bean=(ArticleContent) view.getTag();
                int pos=(int) view.getTag(R.id.tag);
                bean.imgUrls.remove(pos);
                bean.picFiles.remove(pos);
                View parent=(View)view.getParent();
                ((ViewGroup) parent.getParent()).removeView(parent);
            }
        });
        final ScrollView scrollView=fv(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivClose:
                setViewGone(R.id.ivClose,R.id.ivArticlePic);
                if(mMainPicFile!=null){
                    mMainPicFile.delete();
                }
                mMainPicFile=null;
                mMainPicUrl="";
                break;
            case R.id.rlSelectType:
                final MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.setLayout(R.layout.layout_select_dialog);
                dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        dialogFragment.setDialogViewsOnClickListener(view,R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4);
                        ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.guid));
                        ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.encyclopedia));
                        ((TextView) view.findViewById(R.id.tv3)).setText(getString(R.string.public_welfare));
                        ((TextView) view.findViewById(R.id.tv4)).setText(getString(R.string.hospital));
                        view.findViewById(R.id.line3).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.line4).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tv4).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onViewClick(int viewId) {
                        switch (viewId){
                            case R.id.tv1:
                                mArticleType=Constants.ARTICLE_TYPE[0];
                                setText(R.id.tvArticleType,R.string.guid);
                                break;
                            case R.id.tv2:
                                mArticleType=Constants.ARTICLE_TYPE[1];
                                setText(R.id.tvArticleType,R.string.encyclopedia);
                                break;
                            case R.id.tv3:
                                mArticleType=Constants.ARTICLE_TYPE[2];
                                setText(R.id.tvArticleType,R.string.public_welfare);
                                break;
                            case R.id.tv4:
                                mArticleType=Constants.ARTICLE_TYPE[3];
                                setText(R.id.tvArticleType,R.string.hospital);
                                break;
                        }
                    }
                });
                dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
                break;
            case R.id.llArticlePic:
            case R.id.ivArticlePic:
                mSelectPicFor=1;
                ((BaseActivity) getActivity()).showSelectPhotoWindow();
                break;
            case R.id.tvRight:
                if(TextUtils.isEmpty(mArticleType)){
                    showToast(R.string.please_select_article_type);
                    return;
                }
                if(TextUtils.isEmpty(mMainPicUrl)){
                    showToast(R.string.please_select_mian_pic);
                    return;
                }
                String title=getTextById(R.id.etTitle);
                if(TextUtils.isEmpty(title)){
                    showToast(R.string.please_input_title);
                    return;
                }
                String content="";
                int size=getArticleContentList().size();
                ArticleContent bean;
                LinearLayout llContent=fv(R.id.llContent);
                View itemView;
                String itemContent;
                for(int i=0;i<size;++i){
                    bean=getArticleContentList().get(i);
                    itemView=llContent.getChildAt(i);
                    itemContent=((EditText) itemView.findViewById(R.id.etAddContent)).getText().toString();
                    if(!TextUtils.isEmpty(itemContent)){
                        content+="<p>"+itemContent+"</p>";
                    }
                    if(bean.imgUrls!=null && !bean.imgUrls.isEmpty()){
                        for(String url:bean.imgUrls){
                            if(TextUtils.isEmpty(url)){
                                continue;
                            }
                            ArrayList<String> urls=Utils.getUrlList(url);
                            content+="<p><img src=\""+urls.get(0)+"\"/></p>";
                        }
                    }
                }
                if(TextUtils.isEmpty(content)){
                    showToast(R.string.please_input_content);
                    return;
                }
                HttpMethods.getInstance().saveApparticle(mArticleType,title,content,mMainPicUrl,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                if(!TextUtils.isEmpty(bean.getMsg())){
                                    showToast(bean.getMsg());
                                }
                                goBack();
                            }
                        },getActivity(),(BaseActivity) getActivity()));
                break;
        }
    }

    class ArticleContent{
        public ArrayList<String> imgUrls;
        public ArrayList<File> picFiles;
    }
}
