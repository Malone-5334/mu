package com.baselibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/10/8 18:51
 * <p>
 * IntentUtil
 */

public class IntentUtil {

    public static final int REQUESTCODE_OPEN_LOCATION_SETTING = 0x1301;

    private IntentUtil() {
        throw new UnsupportedOperationException("IntentUtil cannot be instantiated");
    }

    /**
     * 打开游览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开位置设置
     *
     * @param context
     */
    public static void openLocationSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUESTCODE_OPEN_LOCATION_SETTING);
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * 打开设置
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void openSetting(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }

    /**
     * 分享文字
     *
     * @param context
     * @param text
     */
    public static void openShare(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    /**
     * 分享图片
     *
     * @param uri
     */
    public static void openShare(Context context, Uri uri) {
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setType("image/jpeg");
        imageIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    /**
     * 分享多个图片
     *
     * @param context
     * @param uri
     */
    public static void openShareMultiple(Context context, ArrayList<Uri> uri) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(intent, "多文件分享"));
    }

    /**
     * 安装文件
     *
     * @param context
     * @param path
     */
    public static void installApk(Context context, String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 显性声明
     *
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

}
