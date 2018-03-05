package com.gzcbkj.chongbao.http;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.BasicResponse;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.Utils;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                 Request request;
                 if(!TextUtils.isEmpty(DataManager.getInstance().getToken())){
                     request = chain.request()
                             .newBuilder()
                             .addHeader("token", DataManager.getInstance().getToken())
                             .build();
                 }else{
                     request = chain.request()
                             .newBuilder()
                             .build();
                 }
                return chain.proceed(request);
            }
        };
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
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
        return "http://120.79.189.98:8080/chongbao/api/";
    }

    private HashMap<String, Object> getDataMap() {
        HashMap<String, Object> dataMap = new HashMap<>();
        if(!TextUtils.isEmpty(DataManager.getInstance().getToken())) {
            //dataMap.put("token", DataManager.getInstance().getToken());
        }

        return dataMap;
    }


    /**
     *
     * @param mobile
     * @param type  register:注册 modify:忘记密码
     * @param subscriber
     */
    public void queryValiCode(String mobile,String type, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("mobile", mobile);
        map.put("type", type);
        Observable observable = mRetrofit.create(HttpService.class).queryValiCode(map);
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

    /**
     *
     * @param mobile
     * @param valiCode
     * @param nickname
     * @param password
     * @param province
     * @param city
     * @param subscriber
     */
    public void register(String mobile,String valiCode,String nickname,String password,String province,String city,ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("mobile", mobile);
        map.put("valiCode", valiCode);
        map.put("username", nickname);
        map.put("password", password);
        map.put("province", province);
        map.put("city", city);
        Observable observable = mRetrofit.create(HttpService.class).register(map);
        toSubscribe(observable, subscriber);
    }

    /**
     *
     * @param mobile
     * @param password
     * @param subscriber
     */
    public void login(String mobile,String password,ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("mobile", mobile);
        map.put("password", password);
        Observable observable = mRetrofit.create(HttpService.class).login(map);
        toSubscribe(observable, subscriber);
    }

    /**
     *
     * @param mobile
     * @param password
     * @param valiCode
     * @param subscriber
     */
    public void forgetPassword(String mobile,String password,String valiCode,ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("valiCode", valiCode);
        Observable observable = mRetrofit.create(HttpService.class).forgetPassword(map);
        toSubscribe(observable, subscriber);
    }

    /**
     *
     * @param password
     * @param subscriber
     */
    public void modifyPassword(String password,ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("password", password);
        Observable observable = mRetrofit.create(HttpService.class).modifyPassword(map);
        toSubscribe(observable, subscriber);
    }

    /**
     *
     * @param nick
     * @param subscriber
     */
    public void updateUserName(String nick,ProgressSubscriber subscriber) {
        HashMap<String, Object> map = getDataMap();
        map.put("username", nick);
        Observable observable = mRetrofit.create(HttpService.class).updateUser(map);
        toSubscribe(observable, subscriber);
    }

    /**
     *
     * @param subscriber
     */
    public void queryUserInfo(ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).queryUserInfo();
        toSubscribe(observable, subscriber);
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
