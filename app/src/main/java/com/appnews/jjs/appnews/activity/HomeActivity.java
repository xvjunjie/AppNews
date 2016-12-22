package com.appnews.jjs.appnews.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.baselibrary.utils.SPreUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseActivity;
import com.appnews.jjs.appnews.db.CacheDbHelper;
import com.appnews.jjs.appnews.fragment.HomeFragment;

import butterknife.Bind;

import static com.appnews.jjs.appnews.R.id.fl_content;

/**
 * Created by 15596 on 2016/12/8.
 */

public class HomeActivity extends AppBaseActivity{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(fl_content)
    FrameLayout flContent;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.dl_layout)
    DrawerLayout dlLayout;


    private boolean isLight;
    private SPreUtil mSPreUtil;
    protected String currentId;
    private long firstTime;
    private CacheDbHelper mCacheDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((HomeActivity) mActivity).setToolBarTitle("今日热闻");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.acitvity_main_test;
    }

    @Override
    public void initData() {
        super.initData();
        mSPreUtil = new SPreUtil(HomeActivity.this, "SPreUtil");
        isLight = mSPreUtil.getBooleanValue("isLight", true);
        mCacheDbHelper = new CacheDbHelper(this, 1);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        initToolBar();
        initDrawerLayout();
        intentHomeFragment();
        initSwipeRefresh();

    }

    public void intentHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                .replace(fl_content, new HomeFragment(), "latest")
                .commit();
        currentId = "latest";

    }


    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, isLight ? R.color.light_toolbar : R.color.dark_toolbar));
        setSupportActionBar(toolbar);
    }

    public void setCurId(String id) {
        currentId = id;
    }


    //给其他fragment 调用设置标题
    public void setToolBarTitle(String title) {
        toolbar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main , menu);
        menu.getItem(0).setTitle(mSPreUtil.getBooleanValue("isLight", true) ? "夜间模式" : "日间模式");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_mode){
            isLight = !isLight;
            item.setTitle(isLight? "夜间模式":"日间模式");
            toolbar.setBackgroundColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar ));
            setStatusBarColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
            // TODO: 2016/12/8  
            if (currentId.equals("latest")) {
                ((HomeFragment) getSupportFragmentManager().findFragmentByTag("latest")).updateTheme();
            } else {
//                ((NewsFragment) getSupportFragmentManager().findFragmentByTag("news")).updateTheme();
            }
//            ((MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_fragment)).updateTheme();
            mSPreUtil.putBooleanValue("isLight", isLight);

        }
        return super.onOptionsItemSelected(item);
    }




    private void initDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, dlLayout,
                toolbar, R.string.app_name, R.string.app_name);
        dlLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initSwipeRefresh() {

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(HomeActivity.this, android.R.color.holo_blue_bright),
                ContextCompat.getColor(HomeActivity.this, android.R.color.holo_green_light),
                ContextCompat.getColor(HomeActivity.this, android.R.color.holo_orange_light),
                ContextCompat.getColor(HomeActivity.this, android.R.color.holo_red_light)
        );

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                replaceFragment();
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    public void closeMenu() {
        dlLayout.closeDrawers();
    }

    //设置是否刷新供其它地方调用
    public void setSwipeRefreshEnable(boolean enable) {
        swipeRefresh.setEnabled(enable);
    }



    public void replaceFragment() {
        if (currentId.equals("latest")) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    .replace(R.id.fl_content,
                            new HomeFragment(), "latest").commit();
        }
    }


    public boolean isLight() {
        return isLight;
    }

    public CacheDbHelper getCacheDbHelper() {
        return mCacheDbHelper;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (dlLayout.isDrawerOpen(Gravity.LEFT)){
            closeMenu();
        }else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(flContent, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(isLight ? android.R.color.holo_blue_dark : android.R.color.black));
                sb.show();
                firstTime = secondTime;
            } else {
                finish();
            }
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
