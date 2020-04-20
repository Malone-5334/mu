package com.baselibrary.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.baselibrary.util.MeasureUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/15 17:02
 * <p>
 * MarqueeTextView
 */
public class MarqueeTextView extends AppCompatTextView {

    /**
     * 默认跑马重复限度
     */
    private static final int DEFAULT_MARQUEE_REPEAT_LIMIT = -1;

    /**
     * 消息字幕切换
     */
    private static final int MESSAGE_MARQUEE_SWITCH = 0x0122;

    /**
     * 消息字幕开始
     */
    private static final int MESSAGE_MARQUEE_START = 0x0123;

    /**
     * 消息字幕停止
     */
    private static final int MESSAGE_MARQUEE_STOP = 0x0124;

    /**
     * 字幕宽度
     */
    private float marqueeWhith;

    /**
     * 字符限制
     */
    private int marqueeLimit;

    /**
     * 跑马灯内容列队
     */
    private LinkedBlockingQueue<String> queue;

    /**
     * 跑马灯列队管理
     */
    private QueueHandler queueHandler;


    public MarqueeTextView(Context context) {
        super(context);
        initView();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        queue = new LinkedBlockingQueue<>();
        queueHandler = new QueueHandler(this);
        setMarqueeRepeatLimit(DEFAULT_MARQUEE_REPEAT_LIMIT);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus)
            super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 添加字幕
     *
     * @param content
     */
    public void addMarquee(String content) {
        queue.add(content);
    }

    /**
     * 添加字幕
     *
     * @param marquee
     */
    public void addMarquee(List<String> marquee) {
        queue.addAll(marquee);
    }

    @Override
    public void setMarqueeRepeatLimit(int marqueeLimit) {
        super.setMarqueeRepeatLimit(marqueeLimit);
        this.marqueeLimit = marqueeLimit;
    }

    @Override
    public int getMarqueeRepeatLimit() {
        return super.getMarqueeRepeatLimit();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        marqueeWhith = MeasureUtil.getCharacterWidth(text, getTextSize());
    }

    private void stetText() {
        try {
            String marquee = queue.take();
            super.setText(marquee);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始
     */
    public void start() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        queueHandler.sendEmptyMessage(MESSAGE_MARQUEE_START);

    }

    /**
     * 结束
     */
    public void stop() {
        setEllipsize(TextUtils.TruncateAt.END);
        queueHandler.sendEmptyMessage(MESSAGE_MARQUEE_STOP);
    }

    /**
     * 列队管理
     */
    private static class QueueHandler extends Handler {
        private static final int MARQUEE_DELAY = 1200;
        private static final int MARQUEE_OFFSET = 30;
        private WeakReference<MarqueeTextView> reference;
        private int marqueeOffset;
        private int marqueeWhith;

        public QueueHandler(MarqueeTextView view) {
            reference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            MarqueeTextView view = reference.get();
            if (view == null) {
                return;
            }
            switch (msg.what) {
                case MESSAGE_MARQUEE_START:
                    view.stetText();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    }


}
