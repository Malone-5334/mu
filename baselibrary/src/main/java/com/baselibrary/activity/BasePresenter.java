package com.baselibrary.activity;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public class BasePresenter<V extends BaseView> implements MPresenter<V> {


    /**
     * 由于Presenter 经常性的持有Activity 的强引用，如果在一些请求结束之前Activity 被销毁了，Activity对象将无法被回收，此时就会发生内存泄露。
     * 这里我们使用虚引用和泛型来对MVP中的内存泄漏问题进行改良。
     */
    protected Reference<V> mView;

    protected CompositeDisposable mCompositeDisposable;


    protected V getView() {
        return mView.get();
    }


    /*****************************************************************************************************/
    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前已使用 {@link } RxLifecycle避免内存泄漏,此方法作为备用方案
     *
     * @param disposable
     */
    protected void addDisposabel(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable.add(disposable);
        Log.d("订阅", mCompositeDisposable.toString() + "个数" + mCompositeDisposable.size());
    }

    public void unDispose() {

        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    /*****************************************************************************************************/

    @Override
    public void attachView(V view) {

        this.mView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        unDispose();//备用方案
    }
}
