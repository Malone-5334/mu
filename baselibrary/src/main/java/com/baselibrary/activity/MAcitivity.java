package com.baselibrary.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public interface MAcitivity {

    /**
     * 初始化 View, 如果 {@link #initView(Bundle)} 返回 0, 则不会调用 {@link Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */
    int initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);


    /**
     * 是否使用 EventBus
     *
     * @return
     */
    boolean useEventBus();

}
