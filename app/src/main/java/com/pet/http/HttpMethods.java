package com.pet.http;

import android.util.Log;

import com.pet.BaseApplication;
import com.pet.bean.BasicResponse;
import com.pet.utils.Utils;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gigabud on 17-5-3.
 */

public class HttpMethods {

    private static final String TAG = "HttpMethods";


    private Retrofit mRetrofit;


    private static final int DEFAULT_TIMEOUT = 10;

    private static HttpMethods INSTANCE;


    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
    }

    public static HttpMethods getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpMethods();
                }
            }
        }
        return INSTANCE;
    }

    public String getBaseUrl() {
        return "http://v15y626799.iask.in/zfb/app/";
    }

    private HashMap<String, Object> getDataMap(Object data) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("appver", Utils.getVersionName(BaseApplication.getAppContext()));
        dataMap.put("devicetype", "android");
        dataMap.put("rows", 10);
        dataMap.put("page", 1);
        dataMap.put("sorttype", "");
        if (data != null) {
            dataMap.put("data", data);
        }
        return dataMap;
    }




    /**
     * 获取用户信息
     *
     * @param userid     用户id
     * @param subscriber
     */
    public void getUserInfo(String userid, ProgressSubscriber<BasicResponse> subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        Observable observable = mRetrofit.create(HttpService.class).getUserInfo(map);
        toSubscribe(observable, subscriber);

        //        if (!TextUtils.isEmpty(filePath)) {
//            File file = new File(filePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image"), file);
//            MultipartBody.Part part = MultipartBody.Part.
//                    createFormData("image", file.getName(), requestBody);
//
//            ArrayList<MultipartBody.Part> parts = new ArrayList<>();
//            parts.add(part);
    }


    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        if (s instanceof ProgressSubscriber) {
            ((ProgressSubscriber) s).setObservable(o);
        }
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
