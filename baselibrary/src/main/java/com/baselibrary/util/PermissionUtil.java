package com.baselibrary.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import com.baselibrary.util.phone.PermissionManager;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/9/13 15:46
 * <p>
 * 权限工具
 */

public class PermissionUtil {

    /**
     * 外部存储卡权限
     */
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x1120;

    /**
     * 发送短信权限
     */
    public static final int REQUEST_CODE_SEND_SMS = 0x1121;

    /**
     * 接收短信
     */
    public static final int REQUEST_CODE_RECEIVE_SMS = 0x1122;

    /**
     * 位置权限
     */
    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 0x1123;

    /**
     * 照相机权限
     */
    public static final int REQUEST_CODE_CAMERA = 0x1124;

    /**
     * 日历权限
     */
    public static final int REQUEST_CODE_CALENDAR = 0x1125;

    /**
     * 忽略省电优化
     */
    public static final int REQUEST_CODE_IGNOR_BATTERY_OPTIMIZATIONS = 0x1126;

    /**
     * 勿扰权限
     */
    public static final int REQUEST_CODE_NOTIFAICATION_POLICY_ACCESSGRANTED = 0x1127;

    /**
     * 多个权限
     */
    public static final int REQUEST_CODE_MARK_MORE = 0x1128;

    private PermissionUtil() {
        throw new UnsupportedOperationException("PermissionUtil cannot be instantiated");
    }

    /**
     * 判断申请外部存储所需权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestExternalStorage(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }
        return false;
    }

    /**
     * 判断申请发送短信权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestSendSms(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_SEND_SMS);
        }
        return false;
    }

    /**
     * 判断申请发送短信权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestSmsReceived(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_RECEIVE_SMS);
        }
        return false;
    }

    /**
     * 判断申请网络位置权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestFineLocation(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
        return false;
    }

    /**
     * 判断申请日期权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestCalendar(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_CODE_CALENDAR);
        }
        return false;
    }

    /**
     * 判断申请照相机权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestCameara(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (isActivate) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }
        return false;
    }

    /**
     * 判断申请忽略电池白名单权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestIgnoringBatteryOptimizations(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        String packageName = context.getPackageName();
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager == null || powerManager.isIgnoringBatteryOptimizations(packageName)) {
            return true;
        }
        if (isActivate) {
            try {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_IGNOR_BATTERY_OPTIMIZATIONS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断申请悬浮窗权限
     *
     * @param context    Context
     * @param isActivate 是否激活权限
     * @return
     */
    public static boolean mayRequestCanDrawOverlays(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (Settings.canDrawOverlays(context)) {
            return true;
        }
        if (isActivate) {
            PermissionManager.getInstance().applyOrShowFloatWindow(context);
        }
        return false;
    }

    /**
     * 判断申请勿扰权限
     *
     * @param context
     * @return
     */
    public static boolean mayRequestNotifaicationPolicyAccessGranted(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null && nm.isNotificationPolicyAccessGranted()) {
            return true;
        }
        if (isActivate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            } else {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, REQUEST_CODE_NOTIFAICATION_POLICY_ACCESSGRANTED);
            }
        }
        return false;
    }

    /**
     * 判断申请通知栏权限
     *
     * @param context
     * @return
     */
    public static boolean mayRequestNotification(Context context, boolean isActivate) {
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        if (nm.areNotificationsEnabled()) {
            return true;
        }
        if (isActivate) {
            mayRequestAllPermissionSetting(context);
        }
        return false;
    }

    /**
     * 判断申请自启动权限
     *
     * @param context
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void mayRequestSelfLaunching(Context context) {
        Intent intent = new Intent();
        String brand = Build.BRAND;
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("HLQ_Struggle", "******************当前手机型号为：" + brand);
            ComponentName componentName = null;
            if (brand.equals("xiaomi") || brand.equals("Xiaomi")) { // 红米Note5x测试通过
                componentName = ComponentName.unflattenFromString("com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity");
                Intent intentXiaomi = new Intent();
                intent.setClassName("com.miui.securitycenter/com.miui.permcenter.autostart", "AutoStartManagementActivity");
                if (context.getPackageManager().resolveActivity(intentXiaomi, 0) == null) {
                    componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                }
            } else if (brand.equals("Letv")) { // 乐视2测试通过
                intent.setAction("com.letv.android.permissionautoboot");
            } else if (brand.equals("samsung")) { // 三星Note5测试通过
                Intent intentSs;
                try {
                    componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm/.ui.ram.RamActivity");
                    intentSs = new Intent();
                    intentSs.setComponent(componentName);
                    context.startActivity(intentSs);
                    return;
                } catch (Exception e) {
                    // componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm/.ui.ram.AutoRunActivity");
                    componentName = ComponentName.unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity");
                    intentSs = new Intent();
                    intentSs.setComponent(componentName);
                    context.startActivity(intentSs);
                    return;
                }
            } else if (brand.equals("HUAWEI") || brand.equals("HONOR")) {
                try {
                    Intent intentHw = new Intent();
                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                    intentHw.setComponent(componentName);
                    context.startActivity(intentHw);
                    return;
                } catch (Exception e) {
                    componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity");
                }
            } else if (brand.equals("vivo")) { // VIVO测试通过
                componentName = ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
            } else if (brand.equals("Meizu")) { // 魅族
                componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");
            } else if (brand.equals("OPPO")) { // OPPO A57 R9S R11S测试通过
                Intent intentOppo;
                try {
                    componentName = ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
                    intentOppo = new Intent();
                    intentOppo.setComponent(componentName);
                    context.startActivity(intentOppo);
                    return;
                } catch (Exception e) {
                    try {
                        componentName = ComponentName.unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity");
                        intentOppo = new Intent();
                        intentOppo.setComponent(componentName);
                        context.startActivity(intentOppo);
                        return;
                    } catch (Exception ex) {
                        componentName = ComponentName.unflattenFromString("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity");
                        intentOppo = new Intent();
                        intentOppo.setComponent(componentName);
                        context.startActivity(intentOppo);
                        return;
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT >= 9) {
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {
            mayRequestAllPermissionSetting(context);
        }
    }

    /**
     * 判断申请多个权限
     *
     * @param context
     * @return
     */
    public static boolean mayRequestMore(Context context, String[] permission, boolean isActivates) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> ps = new ArrayList();
        boolean flag = false;
        boolean result = true;
        for (String p : permission) {
            flag = (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED);
            if (!flag) {
                ps.add(p);
            }
            result = (result && flag);
        }
        if (result) {
            return true;
        }
        String[] current = new String[ps.size()];
        for (int i = 0; i < ps.size(); i++) {
            current[i] = ps.get(i);
        }
        ActivityCompat.requestPermissions((Activity) context, current, REQUEST_CODE_MARK_MORE);
        return false;
    }

    /**
     * 判断申请设置界面权限
     *
     * @param context
     */
    public static void mayRequestAllPermissionSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

}
