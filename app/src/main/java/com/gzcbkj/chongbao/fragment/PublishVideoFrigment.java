package com.gzcbkj.chongbao.fragment;

import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.http.HttpMethods;
import com.gzcbkj.chongbao.http.ProgressSubscriber;
import com.gzcbkj.chongbao.http.SubscriberOnNextListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.manager.IDataChangeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangzhifeng on 2018/3/2.
 */

public class PublishVideoFrigment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_video;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.publish_video);
        TextView tvRight = fv(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        setText(tvRight, getString(R.string.publish));
        tvRight.setBackgroundResource(R.drawable.publish_bg);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setTextColor(Color.WHITE);
        setViewsOnClickListener(R.id.tvRight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvRight:
//                File file = new File(Environment.getExternalStorageDirectory(), "video1.mp4");
//                ArrayList<File> list = new ArrayList<>();
//                list.add(file);
//                HttpMethods.getInstance().uploadFile(list, new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
//                    @Override
//                    public void onNext(ResponseBean bean) {
//                        if (bean != null) {
//                            Log.e("aaaaaaaa", "msg: " + bean.getMsg());
//                        }
//                    }
//                }, getActivity(), (BaseActivity) getActivity()));
                final ArrayList<HashMap<String, String>> list = new ArrayList<>();

                HashMap<String, String> sayImg = new HashMap<>();
                sayImg.put("imgUrl", "http://120.79.189.98:8080/img/20180329/1522293708043621030.mp4");
                list.add(sayImg);

                String content = getTextById(R.id.etAddDes);
                HttpMethods.getInstance().saveSayDetail(content, list, "1", 0.0, 0.0,
                        new ProgressSubscriber(new SubscriberOnNextListener<ResponseBean>() {
                            @Override
                            public void onNext(ResponseBean bean) {
                                ArrayList<IDataChangeListener> listeners = DataManager.getInstance().getDataChangeListener();
                                for (IDataChangeListener listener : listeners) {
                                    if (listener instanceof MyDynamicFragment) {
                                        listener.needRefrsh();
                                        break;
                                    }
                                }
                                if (getView() == null) {
                                    return;
                                }
                                if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                    showToast(bean.getMsg());
                                }
                                goBack();
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }
}
