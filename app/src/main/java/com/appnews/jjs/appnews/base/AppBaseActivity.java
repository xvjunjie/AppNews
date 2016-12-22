package com.appnews.jjs.appnews.base;

import android.os.Bundle;
import android.view.WindowManager;

import com.android.baselibrary.activity.BaseActivity;

/**
 * Created by 15596 on 2016/10/19.
 */

public  abstract class AppBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

}
