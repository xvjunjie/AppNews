package com.android.baselibrary.interfaces;

/**
 * Created by 15596 on 2016/10/19.
 *
 * 规范注册的接口协议
 */

public interface IRegister {
    /**
     * 注册广播、服务
     */
    void register();

    /**
     * 注销广播、服务
     */
    void unRegister();
}