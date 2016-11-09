package com.appnews.jjs.appnews.base;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.appnews.jjs.appnews.utils.SPreUtil;

/**
 * Created by 15596 on 2016/10/11.
 */

public abstract class AppBaseActivity extends BaseActivity {
    private SPreUtil mSPreUtil;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
