package com.gzcbkj.chongbao.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.fragment.ComplaintFragment;
import com.gzcbkj.chongbao.fragment.EditNicknameFragment;
import com.gzcbkj.chongbao.fragment.EditPasswordFragment;
import com.gzcbkj.chongbao.fragment.EditProfileFragment;
import com.gzcbkj.chongbao.fragment.LoginFragment;
import com.gzcbkj.chongbao.fragment.MessageFragment;
import com.gzcbkj.chongbao.fragment.PublishArticleFrigment;
import com.gzcbkj.chongbao.fragment.PublishFrigment;
import com.gzcbkj.chongbao.fragment.PublishPictureFrigment;
import com.gzcbkj.chongbao.fragment.PublishVideoFrigment;
import com.gzcbkj.chongbao.fragment.RaisePetAdvisoryFragment;
import com.gzcbkj.chongbao.fragment.SearchFragment;
import com.gzcbkj.chongbao.fragment.SettingFragment;
import com.gzcbkj.chongbao.fragment.UnfamiliarUserProfileFragment;
import com.gzcbkj.chongbao.fragment.UserProfileFragment;
import com.gzcbkj.chongbao.fragment.VerifyFriendFragment;
import com.gzcbkj.chongbao.fragment.VerifyFriendProfileFragment;

import java.util.ArrayList;

/**
 * Created by gigabud on 17-12-14.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    private static final String[] APP_NEED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        } else {
            afterRequestPermission();
        }
    }

    public void requestPermission() {
        ArrayList<String> uncheckPermissions = null;
        for (String permission : APP_NEED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                if (uncheckPermissions == null) {
                    uncheckPermissions = new ArrayList<>();
                }
                uncheckPermissions.add(permission);
            }
        }
        if (uncheckPermissions != null && !uncheckPermissions.isEmpty()) {
            String[] array = new String[uncheckPermissions.size()];
            ActivityCompat.requestPermissions(this, uncheckPermissions.toArray(array), EXTERNAL_STORAGE_REQ_CODE);
        } else {
            afterRequestPermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                afterRequestPermission();
            }
        }
    }

    private void afterRequestPermission() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        Intent intent = new Intent(SplashActivity.this, EmptyActivity.class);
                        intent.putExtra("FRAGMENT_NAME", EditPasswordFragment.class.getName());
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }

}
