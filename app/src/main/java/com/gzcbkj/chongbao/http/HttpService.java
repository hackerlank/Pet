package com.gzcbkj.chongbao.http;

import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import java.util.HashMap;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gigabud on 17-5-3.
 */

public interface HttpService {


    /**
     * 获取验证码
     * @param map
     * @return
     */
    @POST("user/queryValiCode")
    Observable<ResponseBean> queryValiCode(@Body HashMap<String, Object> map);

    /**
     *注册
     * @param map
     * @return
     */
    @POST("user/register")
    Observable<ResponseBean> register(@Body HashMap<String, Object> map);

    /**
     * 登录
     * @param map
     * @return
     */
    @POST("user/login")
    Observable<UserInfoBean> login(@Body HashMap<String, Object> map);


    /**
     *忘记密码
     * @param map
     * @return
     */
    @POST("user/forgetPassword")
    Observable<ResponseBean> forgetPassword(@Body HashMap<String, Object> map);

    /**
     *已登录修改密码
     * @param map
     * @return
     */
    @POST("user/modifyPassword")
    Observable<ResponseBean> modifyPassword(@Body HashMap<String, Object> map);

    /**
     *更新用户详细信息
     * @param map
     * @return
     */
    @POST("userInfo/updateUser")
    Observable<ResponseBean> updateUser(@Body HashMap<String, Object> map);

    /**
     * 查询用户详细信息
     * @return
     */
    @GET("userInfo/queryUserInfo")
    Observable<ResponseBean> queryUserInfo();


    /**
     * 用户发表文章
     * @return
     */
    @POST("apparticle/saveApparticle")
    Observable<ResponseBean> saveApparticle(@Body HashMap<String, Object> map);

//    /**
//     * 根据类型查询文章
//     * @return
//     */
//    @POST("apparticle/firstArticleList")
//    Observable<ArticleListResponse> firstArticleList(@Body HashMap<String, Object> map);
//
//    /**
//     * 收藏文章
//     * @return
//     */
//    @POST("apparticle/articleList")
//    Observable<ArticleListResponse> articleList(@Body HashMap<String, Object> map);




    /**
     * banner图和精选文章
     * @return
     */
    @GET("appbanner/bannerList")
    Observable<BannerListResponse> bannerList(@Query("position") int position);

    /**
     * 其它推文
     * @return
     */
    @POST("apparticle/firstArticleList")
    Observable<ArticleListResponse> firstArticleList(@Body HashMap<String, Object> map);

    /**
     * 资讯
     * @return
     */
    @POST("apparticle/articleList")
    Observable<ArticleListResponse> articleList(@Body HashMap<String, Object> map);






//    /**
//     * 19每日一拍
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("ordermeasure")
//    Observable<BasicResponse> orderMeasure(@Part ArrayList<MultipartBody.Part> parts);

}
