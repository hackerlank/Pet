package com.gzcbkj.chongbao.http;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.gzcbkj.chongbao.manager.DataManager;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request;
                if (!TextUtils.isEmpty(DataManager.getInstance().getToken())) {
                    request = chain.request()
                            .newBuilder()
                            .addHeader("token", DataManager.getInstance().getToken())
                            .build();
                } else {
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
                        .setPrettyPrinting()
                        .disableHtmlEscaping()
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


    /**
     * @param mobile
     * @param type       register:注册 modify:忘记密码
     * @param subscriber
     */
    public void queryValiCode(String mobile, String type, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("type", type);
        Observable observable = mRetrofit.create(HttpService.class).queryValiCode(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param mobile
     * @param valiCode
     * @param nickname
     * @param password
     * @param province
     * @param city
     * @param subscriber
     */
    public void register(String mobile, String valiCode, String nickname, String password, String province, String city, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
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
     * @param mobile
     * @param password
     * @param subscriber
     */
    public void login(String mobile, String password, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        Observable observable = mRetrofit.create(HttpService.class).login(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param mobile
     * @param password
     * @param valiCode
     * @param subscriber
     */
    public void forgetPassword(String mobile, String password, String valiCode, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("valiCode", valiCode);
        Observable observable = mRetrofit.create(HttpService.class).forgetPassword(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param password
     * @param oldPassword
     * @param subscriber
     */
    public void modifyPassword(String password, String oldPassword, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("oldPassword", oldPassword);
        Observable observable = mRetrofit.create(HttpService.class).modifyPassword(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param nick
     * @param subscriber
     */
    public void updateUserName(String nick, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", nick);
        Observable observable = mRetrofit.create(HttpService.class).updateUser(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     */
    public void querySpace(ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).querySpace();
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     */
    public void queryUserInfo(ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).queryUserInfo();
        toSubscribe(observable, subscriber);
    }


    /**
     * @param position
     * @param subscriber
     */
    public void bannerList(int position, ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).bannerList(position);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param page
     * @param limit
     * @param category   1:表示查询自己
     * @param subscriber
     */
    public void querySayList(int page, int limit, int category, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("category", category);
        Observable observable = mRetrofit.create(HttpService.class).querySayList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param sayId
     * @param subscriber
     */
    public void querySayDetail(long sayId, ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).querySayDetail(sayId);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param page       页号
     * @param limit      行号
     * @param subscriber
     */
    public void firstArticleList(int page, int limit, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        Observable observable = mRetrofit.create(HttpService.class).firstArticleList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param page       页号
     * @param limit      行号
     * @param type       文章类型;宠物指南:guide|宠物公益:welfare|宠物百科:encyclopedias
     * @param subscriber
     */
    public void articleList(int page, int limit, String type, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("type", type);
        Observable observable = mRetrofit.create(HttpService.class).articleList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param type       文章类型;宠物指南:guide|宠物公益:welfare|宠物百科:encyclopedias
     * @param title
     * @param content
     * @param mainPic
     * @param subscriber
     */
    public void saveApparticle(String type, String title, String content, String mainPic, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("type", type);
        map.put("content", content);
        map.put("mainPic", mainPic);
        Observable observable = mRetrofit.create(HttpService.class).saveApparticle(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param type       //操作类型;2:点赞；3：收藏;1:分享
     * @param articleId
     * @param subscriber
     */
    public void updateArticle(int type, long articleId, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put("articleId", articleId);
        Observable observable = mRetrofit.create(HttpService.class).updateArticle(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param articleId
     * @param subscriber
     */
    public void articleInfo(long articleId, ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).articleInfo(articleId);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     */
    public void uploadFile(ProgressSubscriber subscriber) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("page",page);
//        map.put("limit",limit);
//        map.put("type",type);
        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        RequestBody requestBody;
        MultipartBody.Part part;
        File file = new File(Environment.getExternalStorageDirectory(), "1.jpg");
        requestBody = RequestBody.create(MediaType.parse("image"), file);
        part = MultipartBody.Part.
                createFormData("files", file.getName(), requestBody);
        parts.add(part);

        file = new File(Environment.getExternalStorageDirectory(), "2.jpg");
        requestBody = RequestBody.create(MediaType.parse("image"), file);
        part = MultipartBody.Part.
                createFormData("files", file.getName(), requestBody);
        parts.add(part);

        file = new File(Environment.getExternalStorageDirectory(), "3.jpg");
        requestBody = RequestBody.create(MediaType.parse("image"), file);
        part = MultipartBody.Part.
                createFormData("files", file.getName(), requestBody);
        parts.add(part);

        Observable observable = mRetrofit.create(HttpService.class).uploadFile(parts);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param page       页号
     * @param limit      行号
     * @param subscriber
     */
    public void tobeAdoptList(int page, int limit, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        Observable observable = mRetrofit.create(HttpService.class).tobeAdoptList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param id
     * @param subscriber
     */
    public void tobeAdoptInfo(long id, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        Observable observable = mRetrofit.create(HttpService.class).tobeAdoptInfo(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param adoptName
     * @param adoptAddress
     * @param adoptBeginTime
     * @param adoptEndTime
     * @param adoptPhone
     * @param adoptReason
     * @param adoptShuttle
     * @param userId
     * @param tobteAdpotPet
     * @param remake
     * @param subscriber
     */
    public void adpotPetSave(String adoptName, String adoptAddress, String adoptBeginTime,
                             String adoptEndTime, String adoptPhone, String adoptReason,
                             String adoptShuttle, String userId, long tobteAdpotPet,
                             String remake, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("adoptName", adoptName);
        map.put("adoptAddress", adoptAddress);
        map.put("adoptBeginTime", adoptBeginTime);
        map.put("adoptEndTime", adoptEndTime);
        map.put("adoptPhone", adoptPhone);
        map.put("adoptReason", adoptReason);
        map.put("adoptShuttle", adoptShuttle);
        map.put("userId", userId);
        map.put("tobteAdpotPet", tobteAdpotPet);
        map.put("remake", remake);
        Observable observable = mRetrofit.create(HttpService.class).adpotPetSave(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param type       1走失 2拾得
     * @param subscriber
     */
    public void findorlostInfoList(int page, int limit, String type, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("type", type);
        Observable observable = mRetrofit.create(HttpService.class).findorlostInfoList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     */
    public void queryTypeInfoList(ProgressSubscriber subscriber) {
        Observable observable = mRetrofit.create(HttpService.class).queryTypeInfoList();
        toSubscribe(observable, subscriber);
    }

    /**
     * @param id
     * @param subscriber
     */
    public void findorlostInfoById(long id, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        Observable observable = mRetrofit.create(HttpService.class).findorlostInfoById(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param findorlostType
     * @param findorlostTime
     * @param findorlostAddress
     * @param findorlostPetType
     * @param findorlostPetVariety
     * @param findorlostPetSex
     * @param findorlostPeopleName
     * @param findorlostPeoplePhone
     * @param findorlostRemake
     * @param findorlostLmg
     * @param findorlostLat
     * @param findorlostLng
     * @param subscriber
     */
    public void findorlostInfoSave(String findorlostType, String findorlostTime,
                                   String findorlostAddress, int findorlostPetType,
                                   int findorlostPetVariety, String findorlostPetSex,
                                   String findorlostPeopleName, String findorlostPeoplePhone,
                                   String findorlostRemake, String findorlostLmg, String findorlostLat,
                                   String findorlostLng, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("findorlostType", findorlostType);
        map.put("findorlostTime", findorlostTime);
        map.put("findorlostAddress", findorlostAddress);
        map.put("findorlostPetType", findorlostPetType);
        map.put("findorlostPetVariety", findorlostPetVariety);
        map.put("findorlostPetSex", findorlostPetSex);
        map.put("findorlostPeopleName", findorlostPeopleName);
        map.put("findorlostRemake", findorlostRemake);
        map.put("findorlostPeoplePhone", findorlostPeoplePhone);
        map.put("findorlostLmg", findorlostLmg);
        map.put("findorlostLat", findorlostLat);
        map.put("findorlostLng", findorlostLng);
        Observable observable = mRetrofit.create(HttpService.class).findorlostInfoSave(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param userId
     * @param subscriber
     */
    public void findOwnPetList(String userId, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        Observable observable = mRetrofit.create(HttpService.class).findOwnPetList(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param ownPetHeadurl
     * @param ownPetName
     * @param ownPetType
     * @param ownPetVariety
     * @param ownPetBirth
     * @param ownPetSex
     * @param ownPetWeight
     * @param ownPetSterilization
     * @param ownPetInterest
     * @param ownUserId
     * @param subscriber
     */
    public void ownPetSave(String ownPetHeadurl, String ownPetName, int ownPetType, int ownPetVariety, String ownPetBirth,
                           String ownPetSex, String ownPetWeight, String ownPetSterilization,
                           String ownPetInterest, String ownUserId,
                           ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ownUserId", ownUserId);
        map.put("ownPetHeadurl", ownPetHeadurl);
        map.put("ownPetName", ownPetName);
        map.put("ownPetType", ownPetType);
        map.put("ownPetVariety", ownPetVariety);
        map.put("ownPetBirth", ownPetBirth);
        map.put("ownPetSex", ownPetSex);
        map.put("ownPetWeight", ownPetWeight);
        map.put("ownPetSterilization", ownPetSterilization);
        map.put("ownPetInterest", ownPetInterest);
        Observable observable = mRetrofit.create(HttpService.class).ownPetSave(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param page
     * @param limit
     * @param subscriber
     */
    public void fosterPetList(int page, int limit, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        Observable observable = mRetrofit.create(HttpService.class).fosterPetList(map);

        toSubscribe(observable, subscriber);
    }

    /**
     * @param userId
     * @param fosterName
     * @param fosterPhone
     * @param fosterSex
     * @param fosterAge
     * @param fosterAddress
     * @param fosterCompay
     * @param fosterReason
     * @param fosterBeginTime
     * @param fosterEndTime
     * @param fosterCycle
     * @param fosterShuttle
     * @param fosterPetType
     * @param fosterPetVariety
     * @param fosterPetAge
     * @param fosterPetSex
     * @param fosterPetPic
     * @param feedRequire
     * @param remake
     * @param immuneTime
     * @param immuneCondition
     * @param immuneNewlyday
     * @param subscriber
     */
    public void fosterPetSave(String userId, String fosterName, String fosterPhone,
                              String fosterSex, String fosterAge, String fosterAddress,
                              String fosterCompay, String fosterReason,
                              String fosterBeginTime, String fosterEndTime,
                              String fosterCycle, String fosterShuttle, String fosterPetType,
                              String fosterPetVariety, String fosterPetAge, String fosterPetSex,
                              String fosterPetPic, String feedRequire, String remake,
                              String immuneTime, String immuneCondition, String immuneNewlyday,
                              ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("fosterName", fosterName);
        map.put("fosterPhone", fosterPhone);
        map.put("fosterSex", fosterSex);
        map.put("fosterAge", fosterAge);
        map.put("fosterAddress", fosterAddress);
        map.put("fosterCompay", fosterCompay);
        map.put("fosterReason", fosterReason);
        map.put("fosterBeginTime", fosterBeginTime);
        map.put("fosterEndTime", fosterEndTime);
        map.put("fosterCycle", fosterCycle);
        map.put("fosterShuttle", fosterShuttle);
        map.put("fosterPetType", fosterPetType);
        map.put("fosterPetVariety", fosterPetVariety);
        map.put("fosterPetAge", fosterPetAge);
        map.put("fosterPetSex", fosterPetSex);
        map.put("fosterPetPic", fosterPetPic);
        map.put("feedRequire", feedRequire);
        map.put("remake", remake);
        map.put("immuneTime", immuneTime);
        map.put("immuneCondition", immuneCondition);
        map.put("immuneNewlyday", immuneNewlyday);
        Observable observable = mRetrofit.create(HttpService.class).fosterPetSave(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param userId
     * @param page
     * @param limit
     * @param subscriber
     */
    public void fosterAndAdoptPetInfoList(String userId, int page, int limit, ProgressSubscriber subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("page", page);
        map.put("limit", limit);
        Observable observable = mRetrofit.create(HttpService.class).fosterAndAdoptPetInfoList(map);

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
