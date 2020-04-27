package com.baselibrary.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import javax.inject.Inject;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    @Inject
    public P mPresenter;

    /**
     * create presenter
     *
     * @return presenter
     */
    protected abstract void createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createPresenter();
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
