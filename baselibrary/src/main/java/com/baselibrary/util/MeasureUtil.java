package com.baselibrary.util;

import android.graphics.Paint;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/15 10:24
 * <p>
 * MeasureUtil
 */
public class MeasureUtil {

    private MeasureUtil() {
        throw new UnsupportedOperationException("MeasureUtil cannot be instantiated");
    }

    /**
     * 获取文本长度
     *
     * @param sequence
     * @param size
     * @return
     */
    public static float getCharacterWidth(CharSequence sequence, float size) {
        if (null == sequence || "".equals(sequence)) {
            return 0.0f;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        float width = paint.measureText(sequence, 0, sequence.length());
        return width;
    }
}
