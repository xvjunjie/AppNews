package com.appnews.jjs.appnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.baselibrary.adapter.ViewHolder;
import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.LogUtil;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.activity.HomeActivity;
import com.appnews.jjs.appnews.activity.NewsItemContentActivity;
import com.appnews.jjs.appnews.adapter.AppBaseAdapter;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.appnews.jjs.appnews.bean.StoriesBean;
import com.appnews.jjs.appnews.bean.ThemeIdBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 15596 on 2016/12/7.
 */

public class MenuItemFragment extends AppBaseFragment {

    @Bind(R.id.lv_view)
    ListView lvView;
    @Bind(R.id.spring_view)
    SpringView springView;

    private String urlID;
    private String title;
    private HttpUtil mHttpUtil;

    private ThemeIdBean mThemeIdBean;
    private StoriesBean mIdStoriesBean;
    private Gson mGson;
    private List<StoriesBean> mListIdStories = new ArrayList<>();
    private AppBaseAdapter<StoriesBean> mAppBaseAdapter;


    private TextView tv_title_hearder;
    private ImageView iv_title_hearder;
    private View hearder;
    private String topImg;

    private Bundle mBundle;
    private Handler  mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        hearder = LayoutInflater.from(mActivity).inflate(R.layout.news_header , lvView ,false);
        tv_title_hearder = (TextView) hearder.findViewById(R.id.tv_title);
        iv_title_hearder = (ImageView) hearder.findViewById(R.id.iv_title);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mune_item;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);


       /* Picasso.with(mActivity).load(topImg).into(iv_title_hearder);
        lvView.addHeaderView(hearder);*/

    }




    @Override
    public void initData() {
        super.initData();

        mBundle = getArguments();
        urlID = mBundle.getString("id");
        title = mBundle.getString("name");
//        LogUtil.d("item", urlID + title);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil = new HttpUtil();
                mHttpUtil.doGet(Constants.THEMENEWS + urlID, new JsonCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (result != null) {

                            LogUtil.d("idresult", result);

                            mGson = new Gson();
                            mThemeIdBean = new ThemeIdBean();
                            mThemeIdBean = mGson.fromJson(result, ThemeIdBean.class);

//                            LogUtil.d("name " , mThemeIdBean.getName());


                            List<StoriesBean> listAllStories = mThemeIdBean.getStories();
                            mListIdStories.addAll(listAllStories);//打个断点看看里面数值
                            initListView();

                            topImg = mThemeIdBean.getImage();
                            String topTitle = mThemeIdBean.getDescription();
                            tv_title_hearder.setText(topTitle);
                            Picasso.with(mActivity).load(topImg).into(iv_title_hearder);
                            lvView.addHeaderView(hearder);



                        } else {
                            ToastUtil.showShortToast(mActivity, "数据不存在");
                        }

                    }
                    @Override
                    public void onFailed(Exception e) {
                        ToastUtil.showShortToast(mActivity, "获取数据失败！");
                    }
                });

            }
        }).start();
    }



    public void initListView() {

        mAppBaseAdapter = new AppBaseAdapter<StoriesBean>(mActivity,R.layout.fragment_menuitem , mListIdStories) {
            @Override
            protected void convert(final ViewHolder viewHolder,final StoriesBean item,final int position) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d("setText" ,mListIdStories.get(position).getTitle());
                        String str = mListIdStories.get(position).getTitle();
                        viewHolder.setText(R.id.tv_menu_title ,str);

                        ImageView iv_item_img = viewHolder.getView(R.id.iv_title);

                        List<String> imgList  = mListIdStories.get(position).getImages();
                        if (imgList!=null){
                            String urlStr = imgList.get(0);//没数值
//                            LogUtil.d("jj_str",urlStr);
                            Picasso.with(mActivity).load(urlStr).into(iv_item_img);
                        }
                    }
                });
            }
        };
        lvView.setAdapter(mAppBaseAdapter);
        mAppBaseAdapter.notifyDataSetChanged();
        lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0] += view.getWidth() / 2;
                StoriesBean mId = (StoriesBean) parent.getAdapter().getItem(position);
                Intent mIntent = new Intent(mActivity , NewsItemContentActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("mStoriesBean",mId);
                mBundle.putIntArray(Constants.START_LOCATION, startingLocation);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        lvView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvView!=null && lvView.getChildCount()>0){
                    boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop()== 0);
                    ((HomeActivity)mActivity).setSwipeRefreshEnable(enable);

                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
