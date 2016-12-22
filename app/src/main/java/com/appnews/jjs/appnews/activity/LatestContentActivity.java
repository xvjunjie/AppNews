package com.appnews.jjs.appnews.activity;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.LogUtil;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseActivity;
import com.appnews.jjs.appnews.bean.NewsIdBean;
import com.appnews.jjs.appnews.bean.StoriesBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.appnews.jjs.appnews.widget.RevealBackgroundView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by 15596 on 2016/12/7.
 */

public class LatestContentActivity extends AppBaseActivity implements RevealBackgroundView.OnStateChangeListener {

    @Bind(R.id.revealBackgroundView)
    RevealBackgroundView revealBackgroundView;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.webview)
    WebView webview;

    private HttpUtil mHttpUtil;
    private StoriesBean mStoriesBean = new StoriesBean() ;
    private Gson mGson;
    private NewsIdBean mNewsIdBean;
    private Intent mIntent;
    private boolean isLight = true;
    private Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webview.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webview.getSettings().setAppCacheEnabled(true);

        appBarLayout.setVisibility(View.INVISIBLE);

//        toolbar.setBackgroundColor(ContextCompat.getColor(LatestContentActivity.this, isLight ? R.color.light_toolbar : R.color.dark_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_latestcontent;
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil = new HttpUtil();
                Bundle mBundle  = getIntent().getExtras();
                mStoriesBean = (StoriesBean) mBundle.getSerializable("storiesBean");

                mHttpUtil.doGet(Constants.CONTENT + mStoriesBean.getId(), new JsonCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (result!=null){
                            LogUtil.d("content",result);
                            mGson = new Gson();
                            mNewsIdBean = new NewsIdBean();
                            mNewsIdBean = mGson.fromJson(result , NewsIdBean.class);
                            Picasso.with(LatestContentActivity.this).load(mNewsIdBean.getImage()).into(iv);
                            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                            String html = "<html><head>" + css + "</head><body>" + mNewsIdBean.getBody() + "</body></html>";
                            html = html.replace("<div class=\"img-place-holder\">", "");
                            webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);

                            collapsingToolbarLayout.setTitle(mStoriesBean.getTitle());
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
                            collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));

                        }
                    }

                    @Override
                    public void onFailed(Exception e) {
                        ToastUtil.showShortToast(LatestContentActivity.this ,"获取失败"+ e);
                    }
                });


            }
        }).start();

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setupRevealBackground(savedInstanceState);
        setStatusBarColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
    }


    private void setupRevealBackground(Bundle savedInstanceState) {
        revealBackgroundView.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            mBundle = getIntent().getExtras();
            final int[] startingLocation = mBundle.getIntArray(Constants.START_LOCATION);
            revealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    revealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                    revealBackgroundView.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            revealBackgroundView.setToFinishedFrame();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            appBarLayout.setVisibility(View.VISIBLE);
            setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @TargetApi(21)
    private void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If both system bars are black, we can remove these from our layout,
            // removing or shrinking the SurfaceFlinger overlay required for our views.
            Window window = this.getWindow();
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
    }
}
