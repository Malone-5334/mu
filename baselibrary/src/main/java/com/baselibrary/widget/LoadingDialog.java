package com.baselibrary.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.baselibrary.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

public class LoadingDialog extends AlertDialog {

    private static LoadingDialog loadingDialog;

    TextView tvMessage;

    CharSequence message;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 显示加载进度
     *
     * @param context
     * @param message
     * @param cancelble
     */
    public static void showed(Context context, CharSequence message, boolean cancelble) {
        if (loadingDialog != null) {
            loadingDialog = null;
            System.gc();
        }
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(cancelble);
        loadingDialog.show();
    }

    public static void showed(Context context, CharSequence message, boolean cancelble, boolean canceledOnTouchOutside) {
        if (loadingDialog != null) {
            loadingDialog = null;
            System.gc();
        }
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(cancelble);
        loadingDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        loadingDialog.show();
    }

    /**
     * 隐藏加载进度
     */
    public static void hideed() {
        if (loadingDialog == null) {
            return;
        }
        loadingDialog.dismiss();
        loadingDialog = null;
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
        tvMessage = findViewById(R.id.tv_dialog_loading_message);
        if (message != null) {
            setMessage(message);
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
        if (tvMessage != null) {
            tvMessage.setText(message);
        } else {
            this.message = message;
        }
    }

}
