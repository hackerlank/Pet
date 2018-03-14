package com.gzcbkj.chongbao.http;

import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<ResponseBean> login(@Body HashMap<String, Object> map);


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
     * 查询自己空间主图像
     * @return
     */
    @GET("userInfo/querySpace")
    Observable<ResponseBean> querySpace();

    /**
     * 查询用户详细信息
     * @return
     */
    @GET("userInfo/queryUserInfo")
    Observable<ResponseBean> queryUserInfo();


    /**
     * 查询自己空间发表的说说
     * @return
     */
    @POST("appusersay/querySayList")
    Observable<ResponseBean> querySayList(@Body HashMap<String, Object> map);


    /**
     * 查询说说详情
     * @return
     */
    @GET("appusersay/querySayDetail")
    Observable<ResponseBean> querySayDetail(@Query("sayId") long sayId);


    /**
     * 用户发表文章
     * @return
     */
    @POST("apparticle/saveApparticle")
    Observable<ResponseBean> saveApparticle(@Body HashMap<String, Object> map);

    /**
     * 用户发表文章
     * @return
     */
    @GET("apparticle/info")
    Observable<ResponseBean> articleInfo(@Query("articleId") long articleId);

    /**
     * 点赞|分享|收藏文章
     * @return
     */
    @POST("apparticle/updateArticle")
    Observable<ResponseBean> updateArticle(@Body HashMap<String, Object> map);




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



    @Multipart
    @POST("file/uploadFile")
    Observable<ResponseBean> uploadFile(@Part ArrayList<MultipartBody.Part> part);


    /**
     * 查询带领养的宠物
     * @return
     */
    @POST("adoptpetapp/tobeAdoptList")
    Observable<ResponseBean> tobeAdoptList(@Body HashMap<String, Object> map);

    /**
     * 根据ID查询待领养宠物信息
     * @return
     */
    @POST("adoptpetapp/tobeAdoptInfo")
    Observable<ResponseBean> tobeAdoptInfo(@Body HashMap<String, Object> map);

    /**
     * 保存领养人信息
     * @return
     */
    @POST("adoptpetapp/adpotPetSave")
    Observable<ResponseBean> adpotPetSave(@Body HashMap<String, Object> map);


    /**
     * 查询宠物走失或拾得信息
     * @return
     */
    @POST("findorlostInfo/findorlostInfoList")
    Observable<ResponseBean> findorlostInfoList(@Body HashMap<String, Object> map);

    /**
     * 查询宠物类型和品种
     * @return
     */
    @GET("findorlostInfo/queryTypeInfoList")
    Observable<ResponseBean> queryTypeInfoList();



    /**
     * 根据ID查询宠物走失或拾得详细信息
     * @return
     */
    @POST("findorlostInfo/findorlostInfoById")
    Observable<ResponseBean> findorlostInfoById(@Body HashMap<String, Object> map);


    /**
     * 保存发布的走失或拾得信息
     * @return
     */
    @POST("findorlostInfo/findorlostInfoSave")
    Observable<ResponseBean> findorlostInfoSave(@Body HashMap<String, Object> map);


    /**
     * 查询宠物中心信息
     * @return
     */
    @POST("fosterPet/fosterPetList")
    Observable<ResponseBean> fosterPetList(@Body HashMap<String, Object> map);

    /**
     * 保存寄养信息
     * @return
     */
    @POST("fosterPet/fosterPetSave")
    Observable<ResponseBean> fosterPetSave(@Body HashMap<String, Object> map);

    /**
     * 查询自己的宠物信息
     * @return
     */
    @POST("adoptpetapp/findOwnPetList")
    Observable<ResponseBean> findOwnPetList(@Body HashMap<String, Object> map);

}
