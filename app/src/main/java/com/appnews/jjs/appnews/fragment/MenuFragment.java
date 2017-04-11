package com.appnews.jjs.appnews.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.baselibrary.adapter.ViewHolder;
import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.LogUtil;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.activity.HomeActivity;
import com.appnews.jjs.appnews.adapter.AppBaseAdapter;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.appnews.jjs.appnews.bean.OthersBean;
import com.appnews.jjs.appnews.bean.ThemesBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.appnews.jjs.appnews.R.id.ll_menu;
import static com.appnews.jjs.appnews.R.id.lv_item;
import static com.appnews.jjs.appnews.R.id.tv_login;
import static com.appnews.jjs.appnews.R.id.tv_main;

public class MenuFragment extends AppBaseFragment implements View.OnClickListener {


    @Bind(tv_login)
    TextView tvLogin;

    @Bind(ll_menu)
    LinearLayout llMenu;
    @Bind(tv_main)
    TextView tvMain;
    @Bind(lv_item)
    ListView lvItem;


    private ThemesBean mThemesBean ;
    private HttpUtil  mHttpUtil;
    private Gson mGson;

    private List<OthersBean> othersDatas = new ArrayList<>();
    private AppBaseAdapter<OthersBean> mAppBaseAdapter;
    private MenuItemFragment mMenuItemFragment;




    @Override
    public int getLayoutResId() {
        return R.layout.menu;
    }

    @Override
    public void initData() {
        super.initData();

        initMenuListView();


    }



    private void initMenuListView() {
       /* new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });*/
        mHttpUtil = new HttpUtil();
        mHttpUtil.doGet(Constants.THEMES, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    LogUtil.d(result);
                    mGson = new Gson();
                    mThemesBean = new ThemesBean();
                    mThemesBean = mGson.fromJson(result,ThemesBean.class);
                  /*  List<OthersBean> othersAllDatas = mThemesBean.getOthers();
                    othersDatas.addAll(othersAllDatas);*/
                    othersDatas = mThemesBean.getOthers();

                    initSpringView();

                }else {
                    ToastUtil.showShortToast(mActivity,"数据不存在");
                }
            }

            @Override
            public void onFailed(Exception e) {
                ToastUtil.showShortToast(mActivity,"获取失败"+ e);
            }
        });

    }

    private void initSpringView() {

        mAppBaseAdapter = new AppBaseAdapter<OthersBean>(mActivity ,R.layout.menu_item ,othersDatas ) {
            @Override
            protected void convert(ViewHolder viewHolder, OthersBean item, int position) {
                String data = item.getName();
                viewHolder.setText(R.id.tv_item ,data);
            }
        };

        lvItem.setAdapter(mAppBaseAdapter);


        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMenuItemFragment = new MenuItemFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("id" ,othersDatas.get(position).getId());
                mBundle.putString("name",othersDatas.get(position).getName());
                mMenuItemFragment.setArguments(mBundle);
                LogUtil.d(othersDatas.get(position).getId());
                LogUtil.d(othersDatas.get(position).getName());


                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_content , mMenuItemFragment ,"news")
                        .addToBackStack(null)
                        .commit();
                ((HomeActivity)mActivity).setCurId(othersDatas.get(position).getId());
                ((HomeActivity)mActivity).closeMenu();

            }
        });

    }


    @OnClick(R.id.tv_main)
    public void intentMain(){
        ((HomeActivity) mActivity).intentHomeFragment();
        ((HomeActivity) mActivity).closeMenu();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
