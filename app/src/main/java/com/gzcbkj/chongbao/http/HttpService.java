package com.gzcbkj.chongbao.http;

import com.gzcbkj.chongbao.bean.BaseBean;
import com.gzcbkj.chongbao.bean.BasicResponse;
import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.gzcbkj.chongbao.bean.ValiCodeBean;

import java.util.HashMap;
import retrofit2.http.Body;
import retrofit2.http.POST;
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
    Observable<BaseBean> queryValiCode(@Body HashMap<String, Object> map);

    /**
     *注册
     * @param map
     * @return
     */
    @POST("user/register")
    Observable<BaseBean> register(@Body HashMap<String, Object> map);

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
    Observable<BaseBean> forgetPassword(@Body HashMap<String, Object> map);

    /**
     *已登录修改密码
     * @param map
     * @return
     */
    @POST("user/modifyPassword")
    Observable<BaseBean> modifyPassword(@Body HashMap<String, Object> map);

    /**
     *更新用户详细信息
     * @param map
     * @return
     */
    @POST("userInfo/updateUser")
    Observable<BaseBean> updateUser(@Body HashMap<String, Object> map);






//    /**
//     * 19每日一拍
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("ordermeasure")
//    Observable<BasicResponse> orderMeasure(@Part ArrayList<MultipartBody.Part> parts);

}
