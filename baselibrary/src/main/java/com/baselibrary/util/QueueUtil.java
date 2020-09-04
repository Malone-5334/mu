package com.baselibrary.util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class QueueUtil {

    List<String> data = new ArrayList<>(8);

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


}
