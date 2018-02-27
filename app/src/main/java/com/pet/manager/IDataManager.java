package com.pet.manager;

import android.content.Context;

import com.pet.bean.UserInfoBean;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;

/**
 * Created by gigabud on 17-12-14.
 */

public interface IDataManager {

    public void clearMyUserInfo();

    public void addDataChangeListener(IDataChangeListener listener);

    public void removeDataChangeListener(IDataChangeListener listener);

    public ArrayList<IDataChangeListener> getDataChangeListener();

    public void setObject(Object object);

    public Object getObject();

    public IWXAPI getWeChatApi(Context context);

    public void saveMyUserInfo(UserInfoBean myUserInfo);

    public UserInfoBean getMyUserInfo();
}
