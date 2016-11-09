package com.appnews.jjs.appnews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.appnews.jjs.appnews.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 15596 on 2016/10/18.
 */

public class MySwipActivity extends AppCompatActivity {
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_swip);
        ButterKnife.bind(this);


        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                textView1.setText("正在刷新");
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        textView1.setText("刷新完成");
                        swipeContainer.setRefreshing(false);
                    }
                }, 6000);
            }

        });
    }
}
