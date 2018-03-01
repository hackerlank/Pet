package com.gzcbkj.chongbao.http;

/**
 * Created by gigabud on 17-12-22.
 */

import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public void post(String url, RequestBody body, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void post(String url, String json, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void postXml(String url, String xmlStr, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        RequestBody body = RequestBody.create(XML, xmlStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void get(String url, final Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


    /**
     * 异步下载文件
     *
     * @param url
     * @param file 本地文件存储的文件夹
     */
    public void downloadAsyn(final String url, final File file, final HttpCallBack callBack) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.failed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                FileOutputStream out = null;
                try {
                    is = response.body().byteStream();
                    File tempFile = new File(Utils.getSaveFilePath(BaseApplication.getAppContext(), file.getName() + ".download"));
                    out = new FileOutputStream(tempFile);
                    byte[] buf = new byte[4096];
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                    out.flush();
                    tempFile.renameTo(file);
                    if (callBack != null) {
                        callBack.successful();
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.failed(e);
                    }
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (Exception e) {
                        if (callBack != null) {
                            callBack.failed(e);
                        }
                    }
                }
            }
        });
    }

    public interface HttpCallBack {
        public void successful();

        public void failed(Exception e);
    }

}
