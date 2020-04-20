package com.baselibrary.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.baselibrary.util.ToastUtil;
import com.baselibrary.widget.LoadingDialog;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class BaseActivity extends RxAppCompatActivity implements MAcitivity, BaseView {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
                //如果要使用 Eventbus 请将此方法返回 true
                if (useEventBus()) {
                    //注册 Eventbus
                    EventBus.getDefault().register(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            this.mUnbinder = null;
        }

        //如果要使用 Eventbus 请将此方法返回 true
        if (useEventBus()) {
            //解除注册 Eventbus
            EventBus.getDefault().unregister(this);
        }

    }

    /**
     * 子类Activity要使用EventBus只需要重写此方法返回true即可
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void showLoading() {
        LoadingDialog.showed(this, "加载中...", false, false);
        showLog("showload");
    }

    @Override
    public void showProgress(String msg) {
        LoadingDialog.showed(this, msg, false, false);
    }

    @Override
    public void showProgress(String msg, int progress) {
        showProgress(msg + progress);
    }

    @Override
    public void hideLoading() {
        LoadingDialog.hideed();
        showLog("hideload");
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void showLMessage(String msg) {
        ToastUtil.showLong(this, msg);
    }

    @Override
    public void showLog(String msg) {
        Log.i(this.getClass().getName(), msg);
    }

    @Override
    public void showLog(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void showLog(String type, String tag, String msg) {
        switch (type) {
            default:
            case "i":
                showLog(tag, msg);
                break;
            case "d":
                Log.d(tag, msg);
                break;
            case "e":
                Log.e(tag, msg);
                break;
            case "v":
                Log.v(tag, msg);
                break;
            case "w":
                Log.w(tag, msg);
                break;
        }
    }
}
