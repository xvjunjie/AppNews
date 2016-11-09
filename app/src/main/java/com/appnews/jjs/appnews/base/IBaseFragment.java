package com.appnews.jjs.appnews.base;

import android.os.Bundle;

/**
 * 基类Fragment接口
 * Created by chenwei.li
 * Date: 2016-01-03
 * Time: 23:23
 */
public interface IBaseFragment {

    /**
     * 绑定布局文件
     *
     * @return
     */
    int bindLayout();

    /**
     * 创建Fragment
     *
     * @param savedInstanceState
     */
    void createFragment(Bundle savedInstanceState);


    /**
     * 初始化View操作
     */
    void initView();

    /**
     * 获取对应数据
     */
    void getData();


}
