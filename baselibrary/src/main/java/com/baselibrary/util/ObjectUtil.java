package com.baselibrary.util;

import java.lang.reflect.Field;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/23 16:01
 * <p>
 * ObjectUtil
 */
public class ObjectUtil {

    private ObjectUtil() {
        throw new UnsupportedOperationException("ObjectUtil cannot be instantiated");
    }

    /**
     * 对象复制
     *
     * @param old
     * @param now
     */
    private static void copy(Object old, Object now) {
        Class cls1 = old.getClass();
        Class cls2 = now.getClass();

        Field[] fields1 = cls1.getDeclaredFields();


    }


}
