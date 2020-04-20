package com.baselibrary.activity;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public interface MPresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

}
