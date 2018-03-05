package com.gzcbkj.chongbao.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gzcbkj.chongbao.activity.BaseActivity;
import com.gzcbkj.chongbao.bean.ResponseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 */
public class ProgressSubscriber extends Subscriber<ResponseBean> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private OnHttpErrorListener mErrorListener;
    private boolean isShowDialog = true;
    private Context context;
    private int mRetryTime = 0;
    private Observable mObservable;

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, OnHttpErrorListener errorListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.mErrorListener = errorListener;
    }


    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShowDialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowDialog = isShowDialog;
    }

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShowDialog, OnHttpErrorListener errorListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.mErrorListener = errorListener;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS_DIALOG:
                    ((BaseActivity) context).showLoadingDialog();
                    break;
                case DISMISS_PROGRESS_DIALOG:
                    ((BaseActivity) context).hideLoadingDialog();
                    break;
            }
        }
    };



    public void setObservable(Observable observable) {
        mObservable = observable;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if (isShowDialog) {
            mHandler.obtainMessage(SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if (isShowDialog) {
            mHandler.obtainMessage(DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    public void setRetry(int retryTime) {
        mRetryTime = retryTime;
    }


    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Log.e("aaaaaaaa", "errorCode: " + e.toString());
        try {
            if (isShowDialog) {
                mHandler.obtainMessage(DISMISS_PROGRESS_DIALOG).sendToTarget();
            }
            if (e instanceof UnknownHostException) {
                if (mErrorListener != null) {
                    mErrorListener.onConnectError(e);
                }
                return;
            }
            if (mRetryTime++ < 1 && mObservable != null && isConnected(context)) {  //当超时时重新链接服务器
                ProgressSubscriber subscriber = new ProgressSubscriber(mSubscriberOnNextListener, context, isShowDialog, mErrorListener);
                subscriber.setRetry(mRetryTime);
                HttpMethods.getInstance().toSubscribe(mObservable, subscriber);
                return;
            }
        } catch (Exception ex) {

        }
        if (mErrorListener != null) {
            mErrorListener.onConnectError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param responseObj 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(ResponseBean responseObj) {
        if (responseObj != null) {
            if (responseObj.isSuccess()) {
                if (mSubscriberOnNextListener != null) {
                    mSubscriberOnNextListener.onNext(responseObj);
                }
            } else {
                //处理errorCode
                try {
                    if (mErrorListener != null) {
                        mErrorListener.onServerError(responseObj.getCode(), responseObj.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }
    }


    private ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        stop();
    }

    public void stop() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
        if (isShowDialog) {
            mHandler.obtainMessage(DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public void showProgressDialog() {
        if (isShowDialog) {
            mHandler.obtainMessage(SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    public void dismissProgressDialog() {
        if (isShowDialog) {
            mHandler.obtainMessage(DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

}