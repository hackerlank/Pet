package com.gzcbkj.chongbao.manager;

import android.content.Context;
import android.text.TextUtils;

import com.gzcbkj.chongbao.bean.UserInfoBean;
import com.google.gson.Gson;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.Preferences;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.ArrayList;

/**
 * Created by gigabud on 17-5-9.
 */

public class DataManager implements IDataManager {
    private static final String TAG = "DataManager";
    private static DataManager mDataManager;

    private IWXAPI mWeChatApi;

    private Object mObject;

    private UserInfoBean mMyUserInfo;


    private static final String MY_USER_INFO = "my_user_info";


    private ArrayList<IDataChangeListener> mListeners;

    public static IDataManager getInstance() {
        if (mDataManager == null) {
            synchronized (TAG) {
                if (mDataManager == null) {
                    mDataManager = new DataManager();
                }
            }
        }
        return mDataManager;
    }

    private DataManager() {

    }

    @Override
    public synchronized void saveMyUserInfo(UserInfoBean myUserInfo) {
        Preferences.getInstacne().setValues(MY_USER_INFO, myUserInfo == null ? "" : new Gson().toJson(myUserInfo));
        mMyUserInfo = myUserInfo;
    }

    @Override
    public UserInfoBean getMyUserInfo() {
        if (mMyUserInfo != null) {
            return mMyUserInfo;
        }
        String dataStr = Preferences.getInstacne().getValues(MY_USER_INFO, "");
        if (!TextUtils.isEmpty(dataStr)) {
            mMyUserInfo = new Gson().fromJson(dataStr, UserInfoBean.class);
        }
        return mMyUserInfo;
    }

    public String getToken(){
        if(getMyUserInfo()==null){
            return "";
        }
        return getMyUserInfo().getToken();
    }

    public boolean isLogin(){
        return getMyUserInfo()!=null;
    }


    @Override
    public IWXAPI getWeChatApi(Context context) {
        if (mWeChatApi == null) {
            synchronized (TAG) {
                if (mWeChatApi == null) {
                    mWeChatApi = WXAPIFactory.createWXAPI(context, null);
                    mWeChatApi.registerApp(Constants.WECHAT_APP_ID);
                }
            }
        }
        return mWeChatApi;
    }


    @Override
    public void clearMyUserInfo() {
        if (mListeners != null) {
            mListeners.clear();
        }
        mMyUserInfo = null;
        Preferences.getInstacne().setValues(MY_USER_INFO, "");
    }

    @Override
    public void addDataChangeListener(IDataChangeListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    @Override
    public void removeDataChangeListener(IDataChangeListener listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
    }

    @Override
    public ArrayList<IDataChangeListener> getDataChangeListener() {
        return mListeners;
    }

    @Override
    public void setObject(Object object) {
        mObject = object;
    }

    @Override
    public Object getObject() {
        return mObject;
    }

}
