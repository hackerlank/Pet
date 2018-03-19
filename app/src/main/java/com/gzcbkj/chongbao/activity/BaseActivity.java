package com.gzcbkj.chongbao.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.fragment.AddPetFragment;
import com.gzcbkj.chongbao.fragment.AlbumFragment;
import com.gzcbkj.chongbao.fragment.BaseFragment;
import com.gzcbkj.chongbao.fragment.CameraFragment;
import com.gzcbkj.chongbao.fragment.EditProfileFragment;
import com.gzcbkj.chongbao.fragment.MeFragment;
import com.gzcbkj.chongbao.fragment.MyDialogFragment;
import com.gzcbkj.chongbao.fragment.PhotoPreviewFragment;
import com.gzcbkj.chongbao.http.OnHttpErrorListener;
import com.gzcbkj.chongbao.manager.DataManager;
import com.gzcbkj.chongbao.utils.BitmapUtil;
import com.gzcbkj.chongbao.utils.Constants;
import com.gzcbkj.chongbao.utils.NetUtil;
import com.gzcbkj.chongbao.utils.Preferences;
import com.gzcbkj.chongbao.utils.Utils;
import com.gzcbkj.chongbao.widgets.MorePopupWindow;

import org.json.JSONException;

import java.io.File;
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

    private static boolean isNotComeFromBG;  //改为静态的，防止多个Activity会调用背景到前景的方法


    private static ArrayList<String> mActivityNameList;  //当mActivityNameList size为0时表示到了后台

    private ACProgressBaseDialog mDlgLoading;

    private static final int CAMERA_REQUEST_CODE = 10001;
    private static final int ALBUM_REQUEST_CODE = 10002;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               /*
         * 这里判断是否从splashActivity过来，是的话当作从后台到前台处理
		 */
        isNotComeFromBG = !isComeFromSplash();
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
        if (!TextUtils.isEmpty(message)) {
            final MyDialogFragment errorDialog = new MyDialogFragment();
            errorDialog.setLayout(R.layout.layout_one_btn_dialog);
            errorDialog.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                @Override
                public void initView(View view) {
                    view.findViewById(R.id.tv1).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.tv2)).setText(message);
                    ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.ok));
                    errorDialog.setDialogViewsOnClickListener(view, R.id.btn2);
                }

                @Override
                public void onViewClick(int viewId) {

                }
            });
            errorDialog.show(getSupportFragmentManager(), "MyDialogFragment");

            errorDialog.setOnDismiss(new MyDialogFragment.IDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
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
            return BaseApplication.getCurFragment();
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                super.onActivityResult(requestCode, resultCode, data);
                try {
                    String filePath = Utils.getSaveFilePath(BaseActivity.this, "output.jpg");
                    Bitmap bmp = BitmapUtil.getBitmapFromFile(filePath, getDisplaymetrics().widthPixels, getDisplaymetrics().heightPixels);
                    if (bmp == null) {
                        return;
                    }
                    DataManager.getInstance().setObject(bmp);
                    BaseFragment fragment = getVisibleFragment();
                    if (fragment != null && (fragment instanceof AddPetFragment
                            || fragment instanceof EditProfileFragment
                            || fragment instanceof MeFragment)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CameraFragment.USE_CAMERA_TYPE,
                                fragment instanceof MeFragment ? Constants.TYPE_CAMERA_FOR_WALL : Constants.TYPE_CAMERA_FOR_AVATER);
                        gotoPager(PhotoPreviewFragment.class, bundle);
                    }
                } catch (Exception e) {

                }
            } else if (requestCode == ALBUM_REQUEST_CODE) {
                try {
                    String filePath;
                    int sdkVersion = Build.VERSION.SDK_INT;
                    if (sdkVersion >= 19) {
                        filePath = getRealPathFromUriAboveApi19(data.getData());
                    } else {
                        filePath = getRealPathFromUriBelowAPI19(data.getData());
                    }
                    Bitmap bmp = BitmapUtil.getBitmapFromFile(filePath, getDisplaymetrics().widthPixels, getDisplaymetrics().heightPixels);
                    if (bmp == null) {
                        return;
                    }
                    DataManager.getInstance().setObject(bmp);
                    BaseFragment fragment = getVisibleFragment();
                    if (fragment != null && (fragment instanceof AddPetFragment
                            || fragment instanceof EditProfileFragment
                            || fragment instanceof MeFragment)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CameraFragment.USE_CAMERA_TYPE,
                                fragment instanceof MeFragment ? Constants.TYPE_CAMERA_FOR_WALL : Constants.TYPE_CAMERA_FOR_AVATER);
                        gotoPager(PhotoPreviewFragment.class, bundle);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param uri 图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private String getRealPathFromUriBelowAPI19(Uri uri) {
        return getDataColumn(uri, null, null);
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param uri 图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private String getRealPathFromUriAboveApi19(Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
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

    public void connectError(Throwable e) {
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

    public void serverError(int errorCode, String errorMsg) {
        stopHttpLoad();
        errorCodeDo(errorCode, errorMsg);
    }

    public void onBackClick(View view) {
        goBack();
    }

    public void goCameraPage() {
        if (this instanceof CameraActivity) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Utils.getSaveFilePath(BaseActivity.this, "output.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            gotoPager(CameraActivity.class, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (permissions != null && permissions.length > 0 && permissions[0].equals(Manifest.permission.CAMERA)) {
                    if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        goCameraPage();
                        return;
                    }
                }
                break;
            }
        }
    }

    protected void stopHttpLoad() {
        getVisibleFragment().stopLoad();
    }


    public void showSelectTimeView(int year, int month, int day, long minDate, long maxDate, DatePicker.OnDateChangedListener listener) {
        View llSelectTime = findViewById(R.id.llSelectTime);
        llSelectTime.setVisibility(View.VISIBLE);
        llSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });
        DatePicker datePicker = findViewById(R.id.datePicker);
        if (minDate > 0) {
            datePicker.setMinDate(minDate);
        }
        if (maxDate > 0) {
            datePicker.setMaxDate(maxDate);
        }
        datePicker.init(year, month, day, listener);
    }

    public void showSelectPhotoWindow() {
        MorePopupWindow morePopupWindow = new MorePopupWindow(this, new MorePopupWindow.MorePopupWindowClickListener() {
            @Override
            public void onFirstBtnClicked() {
                if (!Utils.isGrantPermission(BaseActivity.this,
                        Manifest.permission.CAMERA)) {
                    requestPermission(0, Manifest.permission.CAMERA);
                } else {
                    goCameraPage();
                }
            }

            @Override
            public void onSecondBtnClicked() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, ALBUM_REQUEST_CODE);
            }

            @Override
            public void onThirdBtnClicked() {

            }

            @Override
            public void onFourthBtnClicked() {

            }

            @Override
            public void onCancelBtnClicked() {

            }
        }, MorePopupWindow.MORE_POPUP_WINDOW_TYPE.TYPE_SELECT_ALBUM_OR_CAMERA);
        morePopupWindow.initView();
        morePopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
