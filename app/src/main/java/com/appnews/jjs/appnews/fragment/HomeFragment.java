package com.appnews.jjs.appnews.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.baselibrary.adapter.ViewHolder;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.activity.MainActivity;
import com.appnews.jjs.appnews.adapter.AppBaseAdapter;
import com.appnews.jjs.appnews.adapter.HomeImgAdapter;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.jude.rollviewpager.RollPagerView;
import com.liaoinstan.springview.widget.SpringView;

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
   /* @Bind(R.id.home)
    TextView home;*/
    @Bind(lv_view)
    ListView lvview;
    @Bind(R.id.spring_view)
    SpringView springView;




    private HomeImgAdapter mHomeImgAdapter;


    private AppBaseAdapter<String> mAppBaseAdapter;
    private List<String> mListDates = new ArrayList<String>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);

        rollView();//图片轮播
        initSpringView();
    }

    @Override
    public void initData() {
        super.initData();
        for (int i = 0; i < 10; i++) {
            String str = "我是第"+i+"个";
            mListDates.add(str);
        }
    }

    private void initSpringView() {
        mAppBaseAdapter = new AppBaseAdapter<String>(mActivity,R.layout.item_home,mListDates){
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_title ,mListDates.get(position));
                viewHolder.setImageResource(R.id.iv_title,R.mipmap.icon_weixin);

            }
        };
        lvview.setAdapter(mAppBaseAdapter);
        mAppBaseAdapter.notifyDataSetChanged();

        lvview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvview!=null && lvview.getChildCount()>0){
                    boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop()== 0);
                    ((MainActivity)mActivity).setSwipeRefreshEnable(enable);

                }
            }
        });
    }

    private void rollView() {
        mHomeImgAdapter = new HomeImgAdapter();
//        rollPagerView.setPlayDelay(2000);
        rollPagerView.setAdapter(mHomeImgAdapter);

    }

}
