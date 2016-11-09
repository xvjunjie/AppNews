package com.appnews.jjs.appnews.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Toast的   工具类
 * <p/>
 * Created by jie.wang
 * Date: 2016-7-28
 * Time: 23:21
 */
public class ToastUtil {
    private static Toast toast;
    private static View view;

    private ToastUtil() {
    }

    @SuppressLint("ShowToast")
    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    public static void showShort(Context context, CharSequence msg) {
            show(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);

    }

    public static void showShort(Context context, int resId) {
        show(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence msg) {
        show(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int resId) {
        show(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
    }

    private static void show(Context context, CharSequence msg,
                             int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
        }
    }

    private static void show(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
        }
    }

}
