package com.baselibrary.activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.baselibrary.R;
import com.baselibrary.util.StatusBarManager;
import com.baselibrary.util.ToastUtil;
import com.baselibrary.widget.LoadingDialog;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

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
    public void showFailure(boolean show) {

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

    @Override
    public void upgrade() {
    }

    @Override
    public void restartDevice() {
        showLog("即将重启设备");
        String cmd = "su -c reboot";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            showLog("Error! Fail to reboot.");
        }
    }

    /**
     * 更新可用内存信息
     */
    @Override
    public String upDateMemInfo() {
        ActivityManager myActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        String leftMemSize = "0";
        try {
            assert myActivityManager != null;
            myActivityManager.getMemoryInfo(memoryInfo);
            long memSize = memoryInfo.availMem;
            //字符类型转换
            leftMemSize = Formatter.formatFileSize(getBaseContext(), memSize);
            showLog("Memory---" + leftMemSize);
            if (memSize <= 60 * 1024 * 1024) {
                restartDevice();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return leftMemSize;
    }

    @Override
    public void restartApp(long millis, boolean isNow) {
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + millis, restartIntent); // millis毫秒后重启应用
        if (isNow) {
            System.exit(0);
        }
    }

    @Override
    public String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    @Override
    public void updateSystemTime(int millis) {
//        SystemTimeUitl.setAutoDateTime(this,false);
//        String datetime = DateUtil.formatDateTime((((long) millis) * 1000), DateUtil.DF_SYSTIME);
//        ShellUtil.cmd(datetime);
//        try {
//            Process process = Runtime.getRuntime().exec("su");
//            DataOutputStream os = new DataOutputStream(process.getOutputStream());
////            os.writeBytes("setprop persist.sys.timezone GMT\n");//时区，会在设置时间的基础上+8小时
//            os.writeBytes("date  \""+datetime+"\"\n");
////            os.writeBytes("clock -w\n");
////            os.writeBytes("exit\n");
//            os.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);//API19
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    /**
     * 状态栏配置
     *
     * @param isTransparent         true 透明状态栏 fals 状态栏颜色为
     * @param isStatusBarColorLight true 状态栏内容颜色明亮的 false 状态栏内容颜色昏暗的
     */
    public void setStatusBar(boolean isTransparent, boolean isStatusBarColorLight) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (isTransparent) {
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isStatusBarColorLight) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 不透明Window
     */
    public void setWindowNoTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Resources.Theme theme = getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(android.R.attr.windowIsTranslucent, typedValue, true);
            if (typedValue.data == -1) {
                theme.applyStyle(R.style.WindowNoTranslucent, true);
            }
        }
    }

    /**
     * 沉寖式状态栏
     */
    public void immersiveBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarManager tintManager = new StatusBarManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorStatusBar);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
