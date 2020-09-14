package com.baselibrary.activity;

import android.text.TextUtils;
import android.util.Log;

import com.baselibrary.activity.util.HException;
import com.baselibrary.activity.util.ResultException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Incremental change is better than ambitious failure.
 *
 * @author : <a href="http://mysticcoder.coding.me/myBlog">MysticCoder</a>
 * @date : 2017/12/4
 * @desc :
 */


public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResultException) {
            onFailure(e.getMessage());
            onFailure(((ResultException) e).getCode(), e.getMessage());
            onFailure(((ResultException) e).getCodeStr(), e.getMessage());
            onFailure(((ResultException) e).getCodeStr(), e.getMessage(), ((ResultException) e).getJson());
        } else {
            String error = HException.handleException(e).getMessage();
            _onError(error);
            onError(error);
            onFailure("",error);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    public abstract void onSuccess(T response);

    /**
     * 服务器返回数据，但code不在约定成功范围内
     *
     * @param msg 服务器返回的数据
     */
    public void onFailure(String msg) {
    }

    public void onFailure(int code, String msg) {
    }

    public void onFailure(String code, String msg) {
    }

    public void onFailure(String code, String msg, String json) {
    }

    public void onError(String err) {
    }


//    public abstract void onError(String errorMsg);


    private void _onSuccess(T responce) {
    }

    private void _onFailure(String msg) {
        if (TextUtils.isEmpty(msg)) {
//                ToastUtils.show(R.string.response_return_error);
        } else {
//                ToastUtils.show(msg);
        }
    }

    private void _onError(String err) {
        Log.e("APIException", err);
    }
}
