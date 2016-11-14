package com.appnews.jjs.appnews.fragment;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appnews.jjs.appnews.R;
import com.appnews.jjs.appnews.base.AppBaseFragment;

import butterknife.Bind;

public class MenuFragment extends AppBaseFragment {


    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_backup)
    TextView tvBackup;
    @Bind(R.id.tv_download)
    TextView tvDownload;
    @Bind(R.id.ll_menu)
    LinearLayout llMenu;
    @Bind(R.id.tv_main)
    TextView tvMain;
    @Bind(R.id.lv_item)
    ListView lvItem;

    @Override
    public int getLayoutResId() {
        return R.layout.menu;
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
