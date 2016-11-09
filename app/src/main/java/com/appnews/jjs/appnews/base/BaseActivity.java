package com.appnews.jjs.appnews.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.appnews.jjs.appnews.config.Constants;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    //当前Activity视图对象
    protected View mView;

//    public LoadingDialog mLoadingDialog = new LoadingDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//
        /**
         * 打印当前Activity名称
         */
        Log.i("BaseActivity", TAG);

        /**
         * 绑定视图
         */
        if (mView == null) {
            mView = LayoutInflater.from(this).inflate(bindLayout(), null);
        }

        Bmob.initialize(this, Constants.Bmob_APPID);

        setContentView(mView);
        /**
         * 依赖注入
         */
        ButterKnife.bind(this);

        /**
         *创建Activity
         */
        createActivity(savedInstanceState);
        /**
         * 初始化视图(设置默认值)
         */
        initView();
        /**
         * 获取对应数据(网络，数据库)
         */
        getData();

        /**
         * 加入ActivityManager管理队列
         */
        ActivityManager.getInstance().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 解绑ButterKnife
         */
        ButterKnife.unbind(this);

        /**
         * 销毁Activity
         */
        ActivityManager.getInstance().removeActivity(this);
    }
}
