package com.baselibrary.util;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * Author: zhangmanji
 * E-mail: 549492777@qq.com
 * CreatedTime: 2017/11/9 16:38
 * <p>
 * util
 */


public class GroupUtil {

    private GroupUtil() {
        throw new UnsupportedOperationException(
                "GroupUtil cannot be instantiated");
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     *
     * @param view
     */
    public static void restoreViewTouchDelegate(final View view) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                bounds.setEmpty();
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    public static boolean canScroll(ScrollView scrollView) {
        View child = scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = child.getHeight();
            return scrollView.getHeight() < childHeight;
        }
        return false;
    }

    public static void setBackgroundAlpha(Window window, float bgAlpha) {
        WindowManager.LayoutParams lp = window
                .getAttributes();
        lp.alpha = bgAlpha;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }

    /**
     * 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，那么sourceBean中的值会覆盖tagetBean重点的值
     *
     * @param sourceBean 被提取的对象bean
     * @param targetBean 用于合并的对象bean
     * @return targetBean, 合并后的对象
     */

    public static Object combineSydwCore(Object sourceBean, Object targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targetBean;
    }

    public static Object combineSydwCore2(Object source, Object target) {
        Class sourceBeanClass = source.getClass();
        Class targetBeanClass = target.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        try {
            for (int i = 0; i < sourceFields.length; i++) {
                Field sourceField = sourceFields[i];
                sourceField.setAccessible(true);
                for (int j = 0; j < targetFields.length; j++) {
                    Field targetField = targetFields[j];
                    targetField.setAccessible(true);
                    if (sourceField.getName().equals(targetField.getName())) {
                        if (sourceField.get(source) == null) {
                            continue;
                        }
                        targetField.set(target, sourceField.get(source));
                        break;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static int countRow(int num, int column) {
        if (num == 0) {
            return 0;
        } else {
            int a = num / column;
            if (a == 0) {
                return 1;
            } else {
                if (num % column == 0) {
                    return a;
                } else {
                    return a + 1;
                }
            }
        }
    }

    public static void deletNotify(Context context, int notifyId) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notifyId);
    }

    public static void showKeyboard(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

    public static void failoverPrice(String price) throws Exception {
        //正整数   　  　//正浮限制一位小数
        if (price.matches("^[0-9]*[1-9][0-9]*$") || price.matches("^\\d+\\.[0-9]{1}")) {
        } else if (price == null || price.equals("")) {
            throw new Exception("价格未填完整");
        } else {
            throw new Exception("价格不合法最多一位小数");

        }
    }

    public static void failoverNum(String num) throws Exception {
        //正整数   　//^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$　　//正浮
        if (num.matches("^[0-9]*[1-9][0-9]*$") || num.matches("^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$")) {

        } else {
            throw new Exception("目标不合法");

        }
    }

}
