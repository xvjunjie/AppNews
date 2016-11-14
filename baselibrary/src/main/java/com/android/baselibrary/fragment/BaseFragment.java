package com.android.baselibrary.fragment;

/**
 * Created by 15596 on 2016/10/19.
 *
 * 规范Fragment的接口协议
 */


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.baselibrary.interfaces.IFragment;
import com.android.baselibrary.interfaces.IRegister;
import com.android.baselibrary.utils.SPreUtil;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment
        implements IFragment, IRegister, View.OnClickListener{
    private static final String SP_NAME         = "firstConfig";
    private static final String STATE_IS_HIDDEN = "isHidden";

    protected Activity mActivity;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this,parentView);
        SPreUtil spUtil = new SPreUtil(mActivity, SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initData();
        initView(parentView, savedInstanceState);
        return parentView;
    }


    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if(isHidden){
                transaction.hide(this);
                onFragmentHide();
            } else {
                transaction.show(this);
                onFragmentShow();
            }
            transaction.commit();
        }
        register();
    }


    @Override public void onDestroyView() {
        unRegister();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @Override public void onFirst() { }
    @Override public void initData() { }
    @Override public void initView(View parentView, Bundle savedInstanceState) { }
    @Override public void register() { }
    @Override public void unRegister() { }
    @Override public void onFragmentShow() { }
    @Override public void onFragmentHide() { }
    @Override public void showProgress() { }
    @Override public void hideProgress() { }
    @Override public void viewClick(View v) { }


    @Override public void onClick(View v) {
        viewClick(v);
    }
}