package com.pet.http;

import com.pet.bean.BasicResponse;
import com.pet.bean.UserInfoBean;

import java.util.HashMap;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by gigabud on 17-5-3.
 */

public interface HttpService {



    /**
     * 01 获取用户信息
     * @param map
     * @return
     */
    @POST("getUserInfo")
    Observable<BasicResponse<UserInfoBean>> getUserInfo(@Body HashMap<String, Object> map);




//    /**
//     * 19每日一拍
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("ordermeasure")
//    Observable<BasicResponse> orderMeasure(@Part ArrayList<MultipartBody.Part> parts);

    //    /**
//     * 20获取每日一拍列表
//     * @param map
//     * @return
//     */
//    @POST("ordermeasure")
//    Observable<BasicResponse> orderMeasure(@Body HashMap<String, Object> map);
}
