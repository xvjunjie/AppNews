package com.appnews.jjs.appnews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 基类Fragment
 * Created by chenwei.li
 * Date: 2016-01-03
 * Time: 23:52
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    //当前Fragment视图对象
    protected View mView;


    /**
     * Activity对象，避免getActivity()出现null
     */
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /**
         * 创建Fragment
         */
        createFragment(savedInstanceState);

        /**
         * 绑定视图
         */
        if (mView == null) {
            mView = inflater.inflate(bindLayout(), container, false);
        }
        /**
         * 依赖注入
         */
        ButterKnife.bind(this, mView);
        /**
         * 初始化视图,默认值
         */
        initView();
        /**
         * 获取对应数据(网络，数据库)
         */
        getData();

        return mView;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 解绑ButterKnife
         */
        ButterKnife.unbind(this);

    }
}
