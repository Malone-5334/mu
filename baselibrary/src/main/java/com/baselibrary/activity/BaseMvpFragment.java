package com.baselibrary.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import javax.inject.Inject;

/**
 * 作者：Malone
 * 时间： 2018/10/9.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    @Inject
    protected P mPresenter;

    /**
     * create presenter
     *
     * @return presenter
     */
    protected abstract void createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        createPresenter();
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
