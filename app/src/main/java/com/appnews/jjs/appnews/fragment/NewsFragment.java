package com.appnews.jjs.appnews.fragment;

import android.widget.TextView;

import com.android.baselibrary.utils.JsonCallBack;
import com.android.baselibrary.utils.ToastUtil;
import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseFragment;
import com.appnews.jjs.appnews.bean.ThemesBean;
import com.appnews.jjs.appnews.config.Constants;
import com.appnews.jjs.appnews.utils.HttpUtil;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 15596 on 2016/10/12.
 * 待定页
 */

public class NewsFragment extends AppBaseFragment {


    @Bind(R.id.iv_show)
    TextView ivShow;

    private HttpUtil mHttpUtil;
    private Gson mGson;
    private ThemesBean mThemesBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initData() {
        super.initData();


        initMenuListView();
    }


    private void initMenuListView() {


        mHttpUtil = new HttpUtil();
        mHttpUtil.doGet(Constants.THEMES, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                mGson = new Gson();
                mThemesBean = new ThemesBean();
                mThemesBean = mGson.fromJson(result, ThemesBean.class);
//                ivShow.setText(mThemesBean.getLimit());
            }

            @Override
            public void onFailed(Exception e) {
                ToastUtil.showShortToast(mActivity, "获取失败" + e);

            }
        });


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
