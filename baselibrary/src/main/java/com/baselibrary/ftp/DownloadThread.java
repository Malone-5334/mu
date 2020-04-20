package com.baselibrary.ftp;

import android.util.Log;

import com.baselibrary.util.ByteUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：Malone
 * 时间： 2018/9/28.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public class DownloadThread {
    private static final String TAG = DownloadThread.class.getName();

    /**
     * 下载状态-开始
     */
    public static final int DOWNLOAD_SATUS_START = 0x1001;

    /**
     * 下载状态-进度
     */
    public static final int DOWNLOAD_SATUS_PROGRESS = 0x1002;

    /**
     * 下载状态-暂停
     */
    public static final int DOWNLOAD_SATUS_PAUSE = 0x1003;

    /**
     * 下载状态-取消
     */
    public static final int DOWNLOAD_SATUS_CANCEL = 0x1004;

    /**
     * 下载状态-错误
     */
    public static final int DOWNLOAD_SATUS_ERROR = 0x1005;

    /**
     * 下载状态-完成
     */
    public static final int DOWNLOAD_SATUS_COMPLETE = 0x1006;

    /**
     * 内容输入流
     */
    private InputStream inputStream;

    /**
     * 内容长度
     */
    private long length;

    /**
     * 内容存储
     */
    private String storage;

    /**
     * 内容下载监听接口
     */
    private DownloadLisenter lisenter;

    /**
     * 状态
     */
    private int status;

    /**
     * 百分比
     */
    private float percent;

    public DownloadThread(InputStream inputStream, long length, String storage, DownloadLisenter lisenter) {
        this.inputStream = inputStream;
        this.length = length;
        this.storage = storage;
        this.lisenter = lisenter;
    }

    /**
     * 开始下载
     */
    public void start() {
        if (status == DOWNLOAD_SATUS_START
                || status == DOWNLOAD_SATUS_PROGRESS) {
            return;
        }
        status = DOWNLOAD_SATUS_START;
        DownloadRunnable runnable = new DownloadRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 暂停下载
     */
    public void pause() {
        status = DOWNLOAD_SATUS_PAUSE;
    }

    /**
     * 下载取消
     */
    public void cancel() {
        status = DOWNLOAD_SATUS_CANCEL;
    }

    /**
     * 下载线程
     */
    private class DownloadRunnable implements Runnable {

        @Override
        public void run() {
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                String parentPath = storage.substring(0, storage.lastIndexOf("/") + 1);
                File parentFile = new File(parentPath);
                if (!parentFile.exists() || parentFile.mkdirs()) {
                    return;
                }
                bis = new BufferedInputStream(inputStream);
                fos = new FileOutputStream(new File(storage));
                long max = length;
                long progress = 0;
                float tempPercent = 0.0f;
                int len = -1;
                byte[] buffer = new byte[1024];
                do {
                    if (status == DOWNLOAD_SATUS_START) {
                        if (lisenter != null) {
                            lisenter.onStart();
                        }
                    }

                    if (status == DOWNLOAD_SATUS_PAUSE) {
                        if (lisenter != null) {
                            lisenter.onPause();
                        }
                    }

                    if (status == DOWNLOAD_SATUS_CANCEL) {
                        if (lisenter != null) {
                            lisenter.onCancel();
                        }
                        break;
                    }

                    if ((len = bis.read(buffer)) == -1) {
                        status = DOWNLOAD_SATUS_COMPLETE;
                        if (lisenter != null) {
                            lisenter.onComplete();
                        }
                        break;
                    }
                    fos.write(buffer, 0, len);
                    progress += len;
                    tempPercent = (float) progress / max;
                    if (tempPercent - percent > 0.0001) {
                        percent = tempPercent;
                        status = DOWNLOAD_SATUS_PROGRESS;
                        if (lisenter != null) {
                            lisenter.onProgress(max, progress);
                        }
                        Log.i(TAG, "下载进度：" + ByteUtil.formatSize(progress) + "/" + ByteUtil.formatSize(max));
                    }
                } while (true);
            } catch (Exception e) {
                e.printStackTrace();
                status = DOWNLOAD_SATUS_ERROR;
                if (lisenter != null) {
                    lisenter.onError(e.getCause());
                }
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
