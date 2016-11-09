package com.appnews.jjs.appnews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseActivity;
import com.appnews.jjs.appnews.fragment.HomeFragment;
import com.appnews.jjs.appnews.fragment.MyFragment;
import com.appnews.jjs.appnews.fragment.NewsFragment;
import com.appnews.jjs.appnews.utils.SPreUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppBaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.iv_house_tab)
    ImageView ivHouseTab;
    @Bind(R.id.tv_house_tab)
    TextView tvHouseTab;
    @Bind(R.id.ll_house_tab)
    LinearLayout llHouseTab;
    @Bind(R.id.iv_rent_tab)
    ImageView ivRentTab;
    @Bind(R.id.tv_rent_tab)
    TextView tvRentTab;
    @Bind(R.id.ll_rent_tab)
    LinearLayout llRentTab;
    @Bind(R.id.iv_me_tab)
    ImageView ivMeTab;
    @Bind(R.id.tv_me_tab)
    TextView tvMeTab;
    @Bind(R.id.ll_me_tab)
    LinearLayout llMeTab;
    @Bind(R.id.dl_layout)
    DrawerLayout dlLayout;


    private boolean isLight;
    private SPreUtil mSPreUtil;

    private HomeFragment mHomeFragment;
    private NewsFragment mNewsFragment;
    private MyFragment mMyFragment;

    @Override
    public int bindLayout() {
        return R.layout.acitvity_main_test;
    }

    @Override
    public void createActivity(Bundle savedInstanceState) {
        mSPreUtil = new SPreUtil(MainActivity.this, "SPreUtil");
        isLight = mSPreUtil.getBooleanValue("isLight", true);
    }

    @Override
    public void initView() {
        defautFragment();
        initToolBar();
        initDrawerLayout();
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(MainActivity.this,android.R.color.holo_blue_bright),
                ContextCompat.getColor(MainActivity.this,android.R.color.holo_green_light),
                ContextCompat.getColor(MainActivity.this,android.R.color.holo_orange_light),
                ContextCompat.getColor(MainActivity.this,android.R.color.holo_red_light)
        );

        swipeRefresh.setOnRefreshListener(this);

    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,dlLayout,
                toolbar,R.string.app_name,R.string.app_name);
        dlLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, isLight ? R.color.light_toolbar : R.color.dark_toolbar));
//        setToolBarTitle("首页");
        setSupportActionBar(toolbar);
    }


    public void setToolBarTitle(String title) {
        toolbar.setTitle(title);
    }


    //进入默认页面
    private void defautFragment() {
        selectTab(0);
    }

    @Override
    public void getData() {

    }

    @OnClick({R.id.ll_house_tab, R.id.ll_rent_tab, R.id.ll_me_tab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_house_tab:
                resetColor();
                selectTab(0);
                break;
            case R.id.ll_rent_tab:
                resetColor();
                selectTab(1);
                break;
            case R.id.ll_me_tab:
                resetColor();
                selectTab(2);
                break;
        }
    }

    private void selectTab(int i) {
        FragmentManager  fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fm.beginTransaction();

        hideFragment(fragmentTransaction);

        switch (i) {
            case 0:
                if (mHomeFragment==null){
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fl_content , mHomeFragment);
                }else {
                    fragmentTransaction.show(mHomeFragment);
                }
                ivHouseTab.setImageResource(R.mipmap.tab_house);
                tvHouseTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.activation));
                break;
            case 1:
                if (mNewsFragment==null){
                    mNewsFragment = new NewsFragment();
                    fragmentTransaction.add(R.id.fl_content , mNewsFragment);

                }else {
                    fragmentTransaction.show(mNewsFragment);
                }
                ivRentTab.setImageResource(R.mipmap.tab_shouzu);
                tvRentTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.activation));
                break;
            case 2:
                if (mMyFragment == null){
                    mMyFragment = new MyFragment();
                    fragmentTransaction.add(R.id.fl_content , mMyFragment);
                }else {
                   fragmentTransaction.show(mMyFragment);
                }

                ivMeTab.setImageResource(R.mipmap.tab_me);
                tvMeTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.activation));
                break;

        }

        fragmentTransaction.commit();

    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {

        if (mHomeFragment!=null){
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mNewsFragment!=null){
            fragmentTransaction.hide(mNewsFragment);
        }
        if (mMyFragment!=null){
            fragmentTransaction.hide(mMyFragment);
        }

    }


    /*重置颜色*/
    private void resetColor() {
        ivHouseTab.setImageResource(R.mipmap.tab_house_dark);
        tvHouseTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.no_activation));

        ivRentTab.setImageResource(R.mipmap.tab_shouzu_dark);
        tvRentTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.no_activation));

        ivMeTab.setImageResource(R.mipmap.tab_me_dark);
        tvMeTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.no_activation));
    }



}
