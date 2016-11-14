package com.appnews.jjs.appnews.activity;

import com.android.baselibrary.activity.BaseSplashActivity;
import com.appnews.jjs.appnews.R;

import java.util.List;


/**
 * Created by 15596 on 2016/10/11.
 */

public class SplashActivity extends BaseSplashActivity {
    private final int     mPlayerTime = 5000;
    private final float   mStartAlpha = 100f;
    private final boolean isExpand = true;

    @Override
    protected void setSplashResources(List<SplashImgResource> resources) {

        resources.add(new SplashImgResource(R.mipmap.background, mPlayerTime, mStartAlpha, isExpand));

    }

    @Override
    protected boolean isAutoStartNextActivity() {
        return true;
    }

    @Override
    protected Class<?> nextActivity() {
        return MainActivity.class;
    }
}
