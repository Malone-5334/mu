package com.baselibrary.util;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.baselibrary.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;

public class CrashHelper {
    private static final String TAG = CrashHelper.class.getSimpleName();
    public static final String BUGLY_APPID = "ed3b7daad6";
    public static final String BUGLY_APPKEY = "9666e70b-0fa1-499a-b8b4-ca1072906218";
    public static final long REPORT_DELAY = 2 * 1000;

    private CrashHelper() {
    }

    /**
     * 安装tinker
     */
    public static void installTinker() {
        Beta.installTinker();
    }

    /**
     * 初始化Bugly
     *
     * @param context Context
     */
    public static void initialize(final Context context) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

        // 设置是否开启热更新能力
        Beta.enableHotfix = true;

        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;

        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;

        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;

        /**
         *  全量升级状态回调
         */
        Beta.upgradeStateListener = new UpgradeStateListener() {

            @Override
            public void onUpgradeFailed(boolean b) {
                Log.e(TAG, "onUpgradeFailed");
            }

            @Override
            public void onUpgradeSuccess(boolean b) {
                Log.e(TAG, "onUpgradeSuccess");
            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Log.e(TAG, "onUpgradeNoVersion");
            }

            @Override
            public void onUpgrading(boolean b) {
                Log.e(TAG, "onUpgrading");
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Log.e(TAG, "onDownloadCompleted");
            }
        };

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {

            @Override
            public void onPatchReceived(String patchFileUrl) {
                Log.e(TAG, "onPatchReceived " + patchFileUrl);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Log.e(TAG, "onDownloadReceived " +
                        String.format(Locale.getDefault(),
                                "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Log.e(TAG, "onDownloadSuccess " + patchFilePath);
                // Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Log.e(TAG, "onDownloadFailure " + msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                Log.e(TAG, "onApplySuccess " + msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.e(TAG, "onApplyFailure " + msg);
            }

            @Override
            public void onPatchRollback() {
                Log.e(TAG, "onPatchRollback");
            }
        };

        Bugly.setUserId(context, "falue");
        Bugly.setUserTag(context, 0);
        Bugly.setAppChannel(context, "bugly");
        Bugly.setIsDevelopmentDevice(context, BuildConfig.DEBUG);

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppVersion(PackageManagerUtil.getVersionName(context));
        strategy.setAppPackageName(PackageManagerUtil.getPackageName(context));
        strategy.setAppReportDelay(REPORT_DELAY);

        long start = System.currentTimeMillis();
        Bugly.init(context, BUGLY_APPID, true, strategy);
        long end = System.currentTimeMillis();
        Log.e("init time ", end - start + "ms");
    }
}
