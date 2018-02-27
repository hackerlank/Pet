package com.hxf.live.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hxf.live.activity.BaseActivity;
import com.hxf.live.manager.DataManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.getInstance().getWeChatApi(this).handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        DataManager.getInstance().getWeChatApi(this).handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i(TAG, "onResp OK");

                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                    DataManager.getInstance().setObject(code);
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i(TAG, "onResp ERR_USER_CANCEL ");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i(TAG, "onResp ERR_AUTH_DENIED");
                //发送被拒绝
                break;
            default:
                Log.i(TAG, "onResp default errCode " + resp.errCode);
                //发送返回
                break;
        }
        finish();
    }

}