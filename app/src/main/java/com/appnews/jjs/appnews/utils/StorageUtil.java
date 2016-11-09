package com.appnews.jjs.appnews.utils;

import android.content.Context;
import android.os.Environment;

/**
 * 文件存储路径相关工具类
 * Created by Lichenwei
 * Date: 2016-01-07
 * Time: 09:04
 */
public class StorageUtil {
    public static String BASE_DIR = Environment.getExternalStorageDirectory()
            + "/FangKu";// BASE_DIR
    public static String USEER_DIR;// 用户相关的资源
    public static String CACHES_DIR;// 其他缓存目录
    public static String TEMP_DIR;// 文件缓存目录
    public static String DOWNLOAD_DIR;// 下载目录
    public static String AUDIO_DIR;// 音频目录
    public static String LOG_DIR;// 报错日志目录
    public static String IMAGE_DIR;// 图片目录

    public static void createDir(Context context) {

        USEER_DIR = BASE_DIR + "/" + context.getPackageName() + "/user";
        FileUtil.createDir(USEER_DIR);

        CACHES_DIR = BASE_DIR + "/" + context.getPackageName() + "/caches/";
        FileUtil.createDir(CACHES_DIR);

        TEMP_DIR = BASE_DIR + "/" + context.getPackageName() + "/tmp/";
        FileUtil.createDir(TEMP_DIR);

        DOWNLOAD_DIR = BASE_DIR + "/" + context.getPackageName()
                + "/downloads/";
        FileUtil.createDir(DOWNLOAD_DIR);

        AUDIO_DIR = BASE_DIR + "/" + context.getPackageName() + "/audio/";
        FileUtil.createDir(AUDIO_DIR);

        LOG_DIR = BASE_DIR + "/" + context.getPackageName() + "/log/";
        FileUtil.createDir(LOG_DIR);

        IMAGE_DIR = BASE_DIR + "/" + context.getPackageName() + "/image/";
        FileUtil.createDir(IMAGE_DIR);
    }



}
