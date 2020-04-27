package com.baselibrary.activity;


import com.trello.rxlifecycle3.LifecycleTransformer;

/**
 * 作者：Malone
 * 时间： 2018/9/12.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public interface BaseView {

    <T> LifecycleTransformer<T> bindToLifecycle();

    /**
     * 显示网络加载失败
     *
     * @param show
     */
    void showFailure(boolean show);

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 显示进度
     *
     * @param msg 提示信息
     */
    void showProgress(String msg);

    /**
     * 显示进度
     *
     * @param msg      提示信息
     * @param progress 进度
     */
    void showProgress(String msg, int progress);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 弹窗提醒
     *
     * @param msg 提示文本
     */
    void showMessage(String msg);

    /**
     * 弹窗提醒
     *
     * @param msg 提示文本
     */
    void showLMessage(String msg);

    /**
     * 打印日志
     *
     * @param msg 信息
     */
    void showLog(String msg);

    /**
     * 打印日志
     *
     * @param tag 标识
     * @param msg 信息
     */
    void showLog(String tag, String msg);

    /**
     * 打印日志
     *
     * @param type 类型
     * @param tag  标识
     * @param msg  信息
     */
    void showLog(String type, String tag, String msg);

    /**
     * 升级
     */
    void upgrade();

    /**
     * 重启设备
     */
    void restartDevice();

    /**
     * 更新可用内存信息
     */
    String upDateMemInfo();

    /**
     * 重启APP
     *
     * @param millis 时间
     * @param isNow  是否立即重启
     */
    void restartApp(long millis, boolean isNow);

    /**
     * 隐藏导航栏
     */
    void hideBottomUIMenu();

    /**
     * 更新系统时间
     *
     * @param time 秒
     */
    void updateSystemTime(int time);

    /**
     * 获取设备Mac地址
     */
    String getMac();
}
