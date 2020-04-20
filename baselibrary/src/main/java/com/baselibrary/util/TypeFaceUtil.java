package com.baselibrary.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/4/17 15:35
 * <p>
 * TypeFaceUtil
 */
public class TypeFaceUtil {

    private TypeFaceUtil() {
        throw new UnsupportedOperationException("TypeFaceUtil cannot be instantiated");
    }

    public static Typeface getGobold(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Gobold.ttf");
        return typeface;
    }

    public static Typeface getLcdisplayRegular(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lcdisplay-Regular.ttf");
        return typeface;
    }
}
