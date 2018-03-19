package com.gzcbkj.chongbao.utils;

/**
 * Created by huangzhifeng on 2018/1/6.
 */

public class Constants {

    public static final String WECHAT_APP_ID = "wx3a0e36ac2be1ad28";
    public static final String WECHAT_APP_SECRET = "2a3cb91e3104ea0dd031b5786b4bde27";
    public static final String WECHAT_PARTNER_ID = "1496067702";
    /**
     * 微信公众平台商户模块和商户约定的密钥
     */
    public static final String WECHAT_PARTNER_KEY = "23k4sfskfuwaonfaklsfuaw3kfklasfs";
    /**
     * 微信统一下单接口
     */
    public static final String WECHAT_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 回调接口
     */
    public static final String WECHAT_NOTIFY_URL = "http://member.daqunli.cn:9090/billingws/ipn/wechat";

    public static final String KEY_BASE_BEAN = "key_base_bean";
    public static final String KEY_BASE_BEAN_2 = "key_base_bean_2";

    public static final String ARTICLE_TYPE[] = new String[]{"guide", "welfare", "encyclopedias", "hospital"};

    public static final String MAIN_PICS[] = new String[]{"http://n.sinaimg.cn/sports/transform/w650h460/20180306/MRcm-fxipenn6273765.jpg",
            "http://n.sinaimg.cn/sports/transform/w640h427/20180306/P12y-fxipenn5101209.jpg",
            "http://n.sinaimg.cn/sports/transform/w650h420/20180305/mfgL-fxipenm9222701.jpg"};


    public static final int MAX_UPLOAD_PHOTO_NUM = 9;


    public static final int TYPE_CAMERA_FOR_AVATER = 1;
    public static final int TYPE_CAMERA_FOR_WALL = 2;
    public static final int OBJECT_TYPE_AVATER=1;
    public static final int OBJECT_TYPE_NICKNAME=2;
    public static final int OBJECT_TYPE_PET_TYPE=3;
    public static final int OBJECT_TYPE_PET_WEIGHT=4;

    public static final int EDIT_USER_NICK=1;
    public static final int EDIT_PET_NICK=2;

}
