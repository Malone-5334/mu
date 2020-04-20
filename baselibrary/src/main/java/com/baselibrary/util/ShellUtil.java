package com.baselibrary.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/22 9:43
 * <p>
 * ShellUtil
 */
public class ShellUtil {
    private final static String TAG = ShellUtil.class.getName();

    private ShellUtil() {
        throw new UnsupportedOperationException("ShellUtil cannot be instantiated");
    }

    @Deprecated
    public static boolean isRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("id\n".getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = bufferedReader.readLine();
            if (s.contains("uid=0")) return true;
        } catch (IOException e) {
            Log.e(TAG, "没有root权限");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null)
                process.destroy();
        }
        return false;
    }

    public static boolean checkRooted() {
        boolean result = false;
        try {
            result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 安装
     *
     * @param path
     */
    public static boolean install(String path) {
        if (path == null || !path.endsWith(".apk")) {
            return false;
        }

        File apk = new File(path);
        if (!apk.exists()) {
            return false;
        }
        int result = cmd("chmod 777 " + apk.getPath() + " \n" +
                "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + apk.getPath() + " \n");
        if (result == 0) {
            Log.d(TAG, "安装成功");
            return true;
        } else if (result == 1) {
            Log.d(TAG, "安装失败");
            return false;
        } else {
            Log.d(TAG, "未知情况");
            return false;
        }
    }

    /**
     * 卸载
     */
    public static void uninstall() {
    }

    /**
     * 启动
     */
    public static void launch(Context context, String className) {
        cmd("am start -S  " + context.getPackageName() + "/" + className + " \n");
    }

    /**
     * 执行CMD
     */
    public static int cmd(String cmd) {
        if (cmd == null || cmd.length() == 0) {
            return -1;
        }
        Process process = null;
        DataOutputStream dos = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("su");
            OutputStream os = process.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeBytes(cmd);
            dos.writeBytes("exit \n");
            dos.flush();
            return process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (process != null) {
                try {
                    process.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return -1;
    }

}
