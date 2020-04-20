package com.baselibrary.ftp;

/**
 * 作者：Malone
 * 时间： 2018/9/28.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class DownloadLisenter {
    public void onStart() {
    }

    public void onPause() {
    }

    public void onProgress(long max, long progress) {
    }

    public abstract void onCancel();

    public abstract void onError(Throwable t);

    public abstract void onComplete();
}
