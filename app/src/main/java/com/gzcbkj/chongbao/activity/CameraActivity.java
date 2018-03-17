package com.gzcbkj.chongbao.activity;

import android.os.Bundle;

import com.gzcbkj.chongbao.R;

/**
 * Created by gigabud on 18-3-2.
 */

public class CameraActivity extends BaseActivity {

    private boolean mIsOnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mIsOnCreate = true;
        findViewById(R.id.rootView).postDelayed(new Runnable() {
            @Override
            public void run() {
                goCameraPage();
            }
        }, 100);
    }

    public void onResume() {
        super.onResume();
        if (!mIsOnCreate) {
            finish();
        }
        mIsOnCreate = false;
    }
}
