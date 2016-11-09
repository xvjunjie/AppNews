package com.appnews.jjs.appnews.base;

import android.os.Bundle;

/**
 * 基类Activity接口
 *
 */
public interface IBaseActivity {

    /**
     * 绑定布局文件
     *
     * @return
     */
    public int bindLayout();

    /**
     * 创建Activity
     *
     * @param savedInstanceState
     */
    public void createActivity(Bundle savedInstanceState);


    /**
     * 初始化View操作
     */
    public void initView();

    /**
     * 获取对应数据
     */
    public void getData();


}
