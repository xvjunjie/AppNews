package com.appnews.jjs.appnews.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseActivity;
import com.appnews.jjs.appnews.bean.NewsIdBean;
import com.appnews.jjs.appnews.bean.StoriesBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.appnews.jjs.appnews.widget.RevealBackgroundView;
import com.google.gson.Gson;

import butterknife.Bind;

/**
 * Created by 15596 on 2016/12/17.
 */

public class NewsItemContentActivity extends AppBaseActivity implements RevealBackgroundView.OnStateChangeListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.revealBackgroundView)
    RevealBackgroundView revealBackgroundView;

    private Gson mGson;
    private NewsIdBean mNewsIdBean;
    private boolean isLight = true;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coordinatorLayout.setVisibility(View.INVISIBLE);
        toolbar.setTitle("享受阅读的乐趣");
        toolbar.setBackgroundColor(ContextCompat.getColor(NewsItemContentActivity.this, isLight ? R.color.light_toolbar : R.color.dark_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webview.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webview.getSettings().setAppCacheEnabled(true);


        setupRevealBackground(savedInstanceState);

    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_news_content;
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil mHttpUtil = new HttpUtil();
                mBundle = getIntent().getExtras();
                StoriesBean mId  = (StoriesBean) mBundle.getSerializable("mStoriesBean");
                mHttpUtil.doGet(Constants.CONTENT + mId.getId(), new JsonCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (result!=null){
                            mGson = new Gson();
                            mNewsIdBean = new NewsIdBean();
                            mNewsIdBean = mGson.fromJson(result , NewsIdBean.class);
                            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                            String html = "<html><head>" + css + "</head><body>" + mNewsIdBean.getBody() + "</body></html>";
                            html = html.replace("<div class=\"img-place-holder\">", "");
                            webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {
                        ToastUtil.showShortToast(NewsItemContentActivity.this ,"获取失败"+ e);
                    }
                }) ;
            }
        }).start();

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
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
    }
}
