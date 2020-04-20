package com.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baselibrary.R;


/**
 * Toast统一管理类
 */
public class ToastUtil {
    private static Toast toast;
    private static ViewHolder viewHolder;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {

        ((Activity) context).getWindowManager().getDefaultDisplay();
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }
        toast.show();
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, int message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            viewHolder = new ViewHolder(context);
            toast.setView(viewHolder.view);
//            toast.setGravity(Gravity.TOP, 0, getScreenHeight(context) / 4);
            viewHolder.msg.setText(message);
        } else {
            viewHolder.msg.setText(message);
        }

        toast.show();
    }

    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    /**
     * 获取屏幕高度
     */
    private static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * Bind View
     */
    static class ViewHolder {
        View view;
        TextView msg;

        ViewHolder(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
            msg = (TextView) view.findViewById(R.id.tv_toast_message);
        }
    }


}
