package com.gzcbkj.chongbao.http;

import com.gzcbkj.chongbao.bean.ArticleBean;
import com.gzcbkj.chongbao.bean.BannerListResponse;
import com.gzcbkj.chongbao.bean.ArticleListResponse;
import com.gzcbkj.chongbao.bean.PetJiyangResponseBean;
import com.gzcbkj.chongbao.bean.ResponseBean;
import com.gzcbkj.chongbao.bean.SayDetailResponse;
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
     * 发表说说
     * @return
     */
    @POST("appusersay/saveSayDetail")
    Observable<ResponseBean> saveSayDetail(@Body HashMap<String, Object> map);


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
    Observable<SayDetailResponse> querySayDetail(@Query("sayId") long sayId);

    /**
     * 评论说说
     * @return
     */
    @POST("appusersaycomment/saveSayComment")
    Observable<ResponseBean> saveSayComment(@Body HashMap<String, Object> map);


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
     * 查询文章评论
     * @return
     */
    @POST("apparticlecomment/articlecommentList")
    Observable<ResponseBean> articlecommentList(@Body HashMap<String, Object> map);

    /**
     * 评论文章
     * @return
     */
    @POST("apparticlecomment/save")
    Observable<ResponseBean> articleCommentSave(@Body HashMap<String, Object> map);

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
    Observable<PetJiyangResponseBean> fosterPetList(@Body HashMap<String, Object> map);

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

    /**
     * 保存个人宠物信息系
     * @return
     */
    @POST("adoptpetapp/ownPetSave")
    Observable<ResponseBean> ownPetSave(@Body HashMap<String, Object> map);

    /**
     * 查询寄领信息
     * @return
     */
    @POST("fosterPet/fosterAndAdoptPetInfoList")
    Observable<ResponseBean> fosterAndAdoptPetInfoList(@Body HashMap<String, Object> map);


    /**
     * 查询收藏列表
     * @return
     */
    @POST("appusercollection/queryCollenction")
    Observable<ResponseBean> queryCollenction(@Body HashMap<String, Object> map);

    /**
     * 取消收藏
     * @return
     */
    @POST("appusercollection/deleteCollection")
    Observable<ResponseBean> deleteCollection(@Query("colectionId") long collectionId);

    /**
     * 查询最新申请记录数量
     * @return
     */
    @GET("appuserapply/queryApplyNum")
    Observable<ResponseBean> queryApplyNum();

    /**
     * 查询申请记录列表
     * @return
     */
    @POST("appuserapply/applyList")
    Observable<ResponseBean> applyList(@Body HashMap<String, Object> map);


    /**
     * 查询可添加好友
     * @return
     */
    @GET("appuserapply/queryFriends")
    Observable<ResponseBean> queryFriends();


    /**
     * 申请添加好友
     * @return
     */
    @POST("appuserapply/userApply")
    Observable<ResponseBean> userApply(@Body HashMap<String, Object> map);

    /**
     * 用户同意或拒绝好友申请
     * @return
     */
    @POST("appuserapply/updateApply")
    Observable<ResponseBean> updateApply(@Body HashMap<String, Object> map);


    /**
     * 查询用户关系列表
     * @return
     */
    @POST("appuserRelation/relationlist")
    Observable<ResponseBean> relationlist(@Body HashMap<String, Object> map);

    /**
     * 保存查看过的用户
     * @return
     */
    @POST("appuserapply/saveResult")
    Observable<ResponseBean> saveResult(@Body HashMap<String, Object> map);
}


