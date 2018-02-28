package com.pet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pet.BaseApplication;
import com.pet.R;
import com.pet.fragment.BaseFragment;
import com.pet.fragment.MyDialogFragment;
import com.pet.http.OnHttpErrorListener;
import com.pet.utils.NetUtil;
import com.pet.utils.Preferences;
import com.pet.utils.Utils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import acplibrary.ACProgressBaseDialog;
import acplibrary.ACProgressConstant;
import acplibrary.ACProgressFlower;

/**
 * 应用首界面
 */
public abstract class BaseActivity extends AppCompatActivity implements OnHttpErrorListener {

    private DisplayMetrics mDisplaymetrics;

    private boolean mIsFullScreen;

    private HashMap<Integer, MyDialogFragment> mErrorDialogs;

    private static boolean isNotComeFromBG;  //改为静态的，防止多个Activity会调用背景到前景的方法


    private static ArrayList<String> mActivityNameList;  //当mActivityNameList size为0时表示到了后台

    private ACProgressBaseDialog mDlgLoading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               /*
         * 这里判断是否从splashActivity过来，是的话当作从后台到前台处理
		 */
        isNotComeFromBG = !isComeFromSplash();
    }

    public void setTitle(String title) {
        setTopBar(title, 0);
    }


    public void setTopBar(String title, int rightRes) {
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
//        if (rightRes > 0) {
//            ImageView ivRight = findViewById(R.id.ivRight);
//            ivRight.setVisibility(View.VISIBLE);
//            ivRight.setImageResource(rightRes);
//        }
    }

    public void showLoadingDialog() {
        showLoadingDialog("", null, true);
    }

    /**
     * 显示Loading 页面， listener可为空
     *
     * @param strTitle
     * @param listener
     * @param isCancelByUser:用户是否可点击屏幕，或者Back键关掉对话框
     */
    public void showLoadingDialog(String strTitle, final DialogInterface.OnCancelListener listener, boolean isCancelByUser) {
        if (mDlgLoading == null) {
            mDlgLoading = new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)  // loading花瓣颜色
                    .text(strTitle)
                    .fadeColor(Color.DKGRAY).build(); // loading花瓣颜色
        }

        if (listener != null) {
            mDlgLoading.setOnCancelListener(listener);
        }

        if (isCancelByUser) {
            mDlgLoading.setCanceledOnTouchOutside(true);
            mDlgLoading.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return false;
                }
            });
        } else {
            mDlgLoading.setCanceledOnTouchOutside(false);
            //防止用户点击Back键，关掉此对话框
            mDlgLoading.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                        return true;
                    return false;
                }
            });
        }

        mDlgLoading.setMessage(strTitle);
        mDlgLoading.show();
    }


    /**
     * 关闭loading的页面
     */
    public void hideLoadingDialog() {
        if (mDlgLoading != null) {
            mDlgLoading.dismiss();
        }
    }

    public void requestPermission(int permissionReqCode, String... permissions) {
        ArrayList<String> uncheckPermissions = null;
        for (String permission : permissions) {
            if (!Utils.isGrantPermission(this, permission)) {
                //进行权限请求
                if (uncheckPermissions == null) {
                    uncheckPermissions = new ArrayList<>();
                }
                uncheckPermissions.add(permission);
            }
        }
        if (uncheckPermissions != null && !uncheckPermissions.isEmpty()) {
            String[] array = new String[uncheckPermissions.size()];
            ActivityCompat.requestPermissions(this, uncheckPermissions.toArray(array), permissionReqCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

    }

    /**
     * 判断是否从splashActivity过来
     *
     * @return true将被当作从后台到前台处理
     */
    protected boolean isComeFromSplash() {
        return getIntent().getBooleanExtra("key_come_from_splash", false);
    }


    public DisplayMetrics getDisplaymetrics() {
        if (mDisplaymetrics == null) {
            mDisplaymetrics = new DisplayMetrics();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplaymetrics);
        }
        return mDisplaymetrics;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mActivityNameList == null) {
            mActivityNameList = new ArrayList<>();
        }

        mActivityNameList.add(getClass().getName());

        if (!isNotComeFromBG) {// 这里表示是从后台到前台
            onFromBackground();
            // 重置
            isNotComeFromBG = true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(0,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        if (mActivityNameList != null) {
            mActivityNameList.remove(getClass().getName());
        }
        if (isBackground()) {
            isNotComeFromBG = false;
            onToBackground();
        } else {
            isNotComeFromBG = true;
        }
    }


    /**
     * 用于在onStop后判断应用是否已经退到后台
     *
     * @return
     */
    private boolean isBackground() {
        return mActivityNameList == null || mActivityNameList.isEmpty();
    }


    public void showOneBtnDialog(final String title, final String msg, final String btnText) {
        final MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.setLayout(R.layout.layout_one_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                if (TextUtils.isEmpty(title)) {
                    view.findViewById(R.id.tv1).setVisibility(View.GONE);
                } else {
                    ((TextView) view.findViewById(R.id.tv1)).setText(title);
                }
                ((TextView) view.findViewById(R.id.tv2)).setText(msg);
                ((TextView) view.findViewById(R.id.btn2)).setText(btnText);
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {

            }
        });
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    public void errorCodeDo(final int errorCode, final String message) {
        if (TextUtils.isEmpty(message)) {
            if (mErrorDialogs == null) {
                mErrorDialogs = new HashMap<>();
            }
            MyDialogFragment errorDialog = mErrorDialogs.get(errorCode);
            if (errorDialog == null) {
                errorDialog = new MyDialogFragment();
                errorDialog.setLayout(R.layout.layout_one_btn_dialog);
                mErrorDialogs.put(errorCode, errorDialog);
                errorDialog.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        view.findViewById(R.id.tv1).setVisibility(View.GONE);
                        ((TextView) view.findViewById(R.id.tv2)).setText(message);
                        ((TextView) view.findViewById(R.id.btn2)).setText("Ok");
                        mErrorDialogs.get(errorCode).setDialogViewsOnClickListener(view, R.id.btn2);
                    }

                    @Override
                    public void onViewClick(int viewId) {

                    }
                });
                errorDialog.show(getSupportFragmentManager(), "MyDialogFragment");

                errorDialog.setOnDismiss(new MyDialogFragment.IDismissListener() {
                    @Override
                    public void onDismiss() {
                        mErrorDialogs.remove(errorCode);
                    }
                });
            }
        }
    }


    /**
     * 页面跳转，如果返回true,则基类已经处理，否则没有处理
     *
     * @param pagerClass
     * @param bundle
     * @return
     */
    public boolean gotoPager(Class<?> pagerClass, Bundle bundle) {

        if (Activity.class.isAssignableFrom(pagerClass)) {
            Intent intent = new Intent(this, pagerClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
            return true;
        } else {
            String name = pagerClass.getName();
            Intent intent = new Intent(this, EmptyActivity.class);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.putExtra("FRAGMENT_NAME", name);
            startActivity(intent);
            return true;
        }
    }


    /**
     * 根据name获取fragment
     *
     * @param name
     * @return
     */
    public BaseFragment getFragmentByName(String name) {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                .findFragmentByTag(name);
        if (fragment == null) {
            fragment = (BaseFragment) Fragment.instantiate(this, name);
        }
        return fragment;
    }

    /**
     * 返回，如果stack中还有Fragment的话，则返回stack中的fragment，否则 finish当前的Activity
     */
    public void goBack() {

        getSupportFragmentManager().executePendingTransactions();
        int nSize = getSupportFragmentManager().getBackStackEntryCount();
        if (nSize > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    public BaseFragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {
            return null;
        }
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment instanceof BaseFragment && fragment.isVisible())
                return (BaseFragment) fragment;
        }
        return BaseApplication.getCurFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getVisibleFragment().onReturnResult(requestCode, resultCode, data);
    }

    protected void onToBackground() {
        Preferences.getInstacne().setIsRunning(false);
        Preferences.getInstacne().setLastCloseAppTime();
    }

    protected void onFromBackground() {
        Preferences.getInstacne().setIsRunning(true);
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConnectError(Throwable e) {
        stopHttpLoad();
        if (!NetUtil.isConnected(this)) {
            //     errorCodeDo("pub_err_nointernet", e.toString());
        } else if (e instanceof UnknownHostException
                || e instanceof JSONException
                || e instanceof retrofit2.adapter.rxjava.HttpException) {
            //          errorCodeDo("GB2411057", e.toString());
        } else if (e instanceof SocketTimeoutException
                || e instanceof ConnectException
                || e instanceof TimeoutException) {
            //        errorCodeDo("GB2411059", e.toString());
        } else {
            //      errorCodeDo("", e.toString());
        }
    }


    @Override
    public void onServerError(int errorCode, String errorMsg) {
        stopHttpLoad();
        errorCodeDo(errorCode, errorMsg);
    }

    public void onBackClick(View view) {
        goBack();
    }

    public void setScreenFull(boolean isFull) {
        if (mIsFullScreen == isFull) {
            return;
        }

        if (isFull) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);
            mIsFullScreen = true;
        } else {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            mIsFullScreen = false;
        }

    }

    protected void stopHttpLoad() {
        getVisibleFragment().stopLoad();
    }

}
