package com.appnews.jjs.appnews.fragment;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.adapter.HomeImgAdapter;
import com.appnews.jjs.appnews.adapter.HomeListAdapter;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.jude.rollviewpager.RollPagerView;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.appnews.jjs.appnews.R.id.lv_view;

/**
 * Created by 15596 on 2016/10/12.
 * 主页
 */

public class HomeFragment extends AppBaseFragment {
    @Bind(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @Bind(R.id.home)
    TextView home;
    @Bind(lv_view)
    ListView lvview;
    @Bind(R.id.spring_view)
    SpringView springView;


    private HomeImgAdapter mHomeImgAdapter;

    private HomeListAdapter mHomeListAdapter;
    private List<String> mListDates = new ArrayList<String>();

    @Override
    public int bindLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void createFragment(Bundle savedInstanceState) {


    }

    @Override
    public void initView() {
        rollView();//图片轮播
        initData();
        initSpringView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            String str = "我是第"+i+"个";
            mListDates.add(str);
        }
    }

    private void initSpringView() {
        mHomeListAdapter = new HomeListAdapter(mActivity, mListDates);
        lvview.setAdapter(mHomeListAdapter);
        mHomeListAdapter.notifyDataSetChanged();

    }

    private void rollView() {
        mHomeImgAdapter = new HomeImgAdapter();
//        rollPagerView.setPlayDelay(2000);
        rollPagerView.setAdapter(mHomeImgAdapter);

    }

    @Override
    public void getData() {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
