package com.appnews.jjs.appnews.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理类
 * Created by chenwei.li
 * Date: 2016-01-03
 * Time: 23:58
 */
public class ActivityManager {

    private static Stack<Activity> mActivityList;
    private static ActivityManager mActivityManager;

    /**
     * 构造方法私有化
     */
    private ActivityManager() {
    }

    /**
     * 获取ActivityManager实例对象
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (mActivityManager == null) {
            mActivityManager = new ActivityManager();
        }
        return mActivityManager;
    }

    /**
     * 添加Activity对象到Activity集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new Stack<Activity>();
        }
        mActivityList.add(activity);
    }


    /**
     * 关闭指定Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityList.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    /**
     * 关闭指定Activity
     *
     * @param cls
     */
    public void removeActivity(Class<?> cls) {
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    /**
     * 关闭所有的Activity
     */
    public void removeAllActivity() {
        if (mActivityList != null && mActivityList.size() >= 0) {
            for (Activity activity : mActivityList) {
                activity.finish();
                activity = null;
            }
        }
    }
}
