package com.appnews.jjs.appnews.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.baselibrary.adapter.ViewHolder;
import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.LogUtil;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.activity.HomeActivity;
import com.appnews.jjs.appnews.activity.LatestContentActivity;
import com.appnews.jjs.appnews.adapter.AppBaseAdapter;
import com.appnews.jjs.appnews.adapter.HomeImgAdapter;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.appnews.jjs.appnews.bean.BeforeBean;
import com.appnews.jjs.appnews.bean.LatestNewsBean;
import com.appnews.jjs.appnews.bean.StoriesBean;
import com.appnews.jjs.appnews.bean.TopStoriesBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.db.CacheDbHelper;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.appnews.jjs.appnews.R.id.lv_view;

/**
 * Created by 15596 on 2016/10/12.
 * 主页
 */

public class HomeFragment extends AppBaseFragment {
    @Bind(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @Bind(lv_view)
    ListView lvview;
    @Bind(R.id.spring_view)
    SpringView springView;

//    private RollPagerView rollPagerView;

    private HomeImgAdapter mHomeImgAdapter;
    private HttpUtil mHttpUtil;
    private Gson mGson;
    private LatestNewsBean mLatestNewsBean;
    private StoriesBean mStoriesBean ;
    private TopStoriesBean mTopStoriesBean;
    private List<StoriesBean> mListStoriesDatas = new ArrayList<>();
    private Handler mHandler = new Handler();
    private AppBaseAdapter<StoriesBean> mAppBaseAdapter;
    private CacheDbHelper mCacheDbHelper;
    private String mDate;
    private boolean isLoading = false;
    private BeforeBean mBeforeBean;
    private boolean isLight;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*rollPagerView = (RollPagerView) container.findViewById(R.id.rollPagerView);
        rollPagerView.seton*/
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);

        isLight = ((HomeActivity)mActivity).isLight();
    }


    @Override
    public void initData() {
        super.initData();
        isLoading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil = new HttpUtil();
                mHttpUtil.doGet(Constants.LATESTNEWS, new JsonCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        if (result!=null){
                            LogUtil.d(result);

                            SQLiteDatabase db = ((HomeActivity) mActivity).getCacheDbHelper().getWritableDatabase();
                            db.execSQL("replace into CacheList(date,json) values(" + Constants.LATEST_COLUMN + ",' " + result + "')");
                            db.close();

                            mGson = new Gson();
                            mLatestNewsBean =  new LatestNewsBean();
                            mLatestNewsBean = mGson.fromJson(result,LatestNewsBean.class);
                            mDate = mLatestNewsBean.getDate();

                            getTopDatas();
                            getHomeListDatas();
                            initSpringView();
                            isLoading = false;

                        }else {
                            ToastUtil.showShortToast(mActivity,"数据获取异常！");
                        }

                    }

                    @Override
                    public void onFailed(Exception e) {
                        ToastUtil.showShortToast(mActivity,"获取失败"+ e);
                    }
                });


            }
        }).start();

    }

    public void getHomeListDatas() { //主界面Item数据
        List<StoriesBean> StoriesAllDatas =mLatestNewsBean.getStories();
        mStoriesBean = new StoriesBean();
        mListStoriesDatas.addAll(StoriesAllDatas);
    }


    public void getTopDatas() {//滚动图数据
        List<TopStoriesBean> TopStoriesAllDatas = mLatestNewsBean.getTop_stories();
        mTopStoriesBean = new TopStoriesBean();
        mHomeImgAdapter = new HomeImgAdapter(mActivity);
        mHomeImgAdapter.setTopEntities(TopStoriesAllDatas);
        rollPagerView.setAdapter(mHomeImgAdapter);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }



    public void initSpringView() {
        mAppBaseAdapter = new AppBaseAdapter<StoriesBean>(mActivity,R.layout.item_home, mListStoriesDatas){
            @Override
            protected void convert(final ViewHolder viewHolder,final StoriesBean item,final int position) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.setText(R.id.tv_title ,mListStoriesDatas.get(position).getTitle());
                        ImageView ivImgShow = viewHolder.getView(R.id.iv_title);
                        List<String> mlist = item.getImages();
                        String imgURL = item.getImages().get(0);
                        Picasso.with(mActivity).load(imgURL).into(ivImgShow);
                    }
                });
            }
        };
        lvview.setAdapter(mAppBaseAdapter);
        mAppBaseAdapter.notifyDataSetChanged();

        lvview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0] += view.getWidth() / 2;
                StoriesBean storiesBean = (StoriesBean) parent.getAdapter().getItem(position);
                Intent mIntent = new Intent(mActivity , LatestContentActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("storiesBean",storiesBean);
                mBundle.putIntArray(Constants.START_LOCATION, startingLocation);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                mActivity.overridePendingTransition(0, 0);
            }
        });


        lvview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvview!=null && lvview.getChildCount()>0){
                    boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop()== 0);
                    ((HomeActivity)mActivity).setSwipeRefreshEnable(enable);
                    if (firstVisibleItem + visibleItemCount == totalItemCount && !isLoading) {
                        loadMore(Constants.BEFORE + mDate);
                    }
                }
            }
        });
    }

    public void loadMore(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil = new HttpUtil();
                mHttpUtil.doGet(url, new JsonCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (result!=null){
                            LogUtil.d(result);

                            mBeforeBean = new BeforeBean();
                            mGson = new Gson();
                            mBeforeBean = mGson.fromJson(result , BeforeBean.class);
                            List<StoriesBean> mStoriesListAll = mBeforeBean.getStories();
                            mListStoriesDatas.addAll(mStoriesListAll);
                        }

                    }

                    @Override
                    public void onFailed(Exception e) {
                        ToastUtil.showShortToast(mActivity,"获取失败"+ e);
                    }
                });

            }
        });


    }

    public String convertDate(String date){
        String result = date.substring(0 ,4);
        result += "年";
        result += date.substring(4, 6);
        result +="月";
        result += date.substring(6 , 8);
        result += "日";
        return result;

    }

    public void updateTheme() {
        isLight = ((HomeActivity) mActivity).isLight();
        mAppBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
