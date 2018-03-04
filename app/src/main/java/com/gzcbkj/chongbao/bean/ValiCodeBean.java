package com.gzcbkj.chongbao.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huangzhifeng on 2018/3/4.
 */

public class ValiCodeBean extends BaseBean {

    public String verCode;

    @SerializedName("Msg")
    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
