package com.baselibrary.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baselibrary.util.ToastUtil;
import com.baselibrary.widget.LoadingDialog;
import com.trello.rxlifecycle3.components.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Malone
 * 时间： 2018/10/9.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class BaseFragment extends RxFragment implements MAcitivity, BaseView {

    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用inflater.inflate(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                view = inflater.inflate(layoutResID, null);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this, view);
                //如果要使用 Eventbus 请将此方法返回 true
                if (useEventBus()) {
                    //注册 Eventbus
                    EventBus.getDefault().register(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            this.mUnbinder = null;
        }
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        LoadingDialog.showed(getContext(), "加载中...", false, false);
        showLog("showload");
    }

    @Override
    public void showProgress(String msg) {
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
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void showLMessage(String msg) {
        ToastUtil.showLong(getContext(), msg);
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
