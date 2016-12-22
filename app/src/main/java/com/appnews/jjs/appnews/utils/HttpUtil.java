package com.appnews.jjs.appnews.utils;

import android.util.Log;

import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.StorageUtil;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.okhttp.OkHttpUtils;
import com.appnews.jjs.appnews.utils.okhttp.callback.FileCallBack;
import com.appnews.jjs.appnews.utils.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 15596 on 2016/11/14.
 *
 * 网络访问中间层
 *
 */

public class HttpUtil {

    public final String TAG = this.getClass().getSimpleName();

    /**
     * 执行Post请求返回Json字符串
     *
     * @param url
     * @param params
     * @param jsonCallBack
     */
    public void doPost(String url, Map<String, String> params, final JsonCallBack jsonCallBack) {

        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(String response, int id) {
                jsonCallBack.onSuccess(response);
            }
        });
    }


    /**
     * 执行Post请求返回Json字符串
     *
     * @param url
     * @param jsonCallBack
     */
    public void doPost(String url, final JsonCallBack jsonCallBack) {


        OkHttpUtils.post().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(String response, int id) {
                jsonCallBack.onSuccess(response);
            }
        });
    }

    /**
     * 执行Get请求返回Json字符串
     *
     * @param url
     * @param jsonCallBack
     */
    public void doGet(String url, final JsonCallBack jsonCallBack) {

        OkHttpUtils.get().url(Constants.BASEURL + url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(String response, int id) {
                jsonCallBack.onSuccess(response);
            }
        });
    }



    public void doGet(String url, Map<String, String> params, final JsonCallBack jsonCallBack) {

        for (String key : params.keySet()) {
            Log.i(TAG, "params:" + "key= " + key + " and value= " + params.get(key));
        }

        OkHttpUtils.get().url(Constants.BASEURL+url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(String response, int id) {
                jsonCallBack.onSuccess(response);
            }
        });
    }


    /**
     * 基于Http表单形式上传文件
     *
     * @param url
     * @param filePath
     * @param jsonCallBack
     */
    public void uploadFile(String url, String filePath, final JsonCallBack jsonCallBack) {
        File file = new File(filePath);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        OkHttpUtils.post().url(url).addFile("file", fileName, file).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(String response, int id) {
                jsonCallBack.onSuccess(response);
            }
        });
    }


    /**
     * 执行文件下载操作
     *
     * @param url
     * @param fileName
     * @param jsonCallBack
     */
    public void downLoadFile(String url, String fileName, final JsonCallBack jsonCallBack) {
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(StorageUtil.DOWNLOAD_DIR, fileName) {

            @Override
            public void onError(Call call, Exception e, int id) {
                jsonCallBack.onFailed(e);
            }

            @Override
            public void onResponse(File response, int id) {
                jsonCallBack.onSuccess(response.getAbsolutePath());
            }

        });

    }





}
