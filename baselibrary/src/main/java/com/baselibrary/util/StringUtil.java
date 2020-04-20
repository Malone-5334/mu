package com.baselibrary.util;

/**
 * 作者：Malone
 * 时间： 2018/10/26.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public class StringUtil {

    //字符串转换为ASCII码
    public static byte[] StringToAscii(String s) {
        char[] chars = s.toCharArray(); //把字符中转换为字符数组
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }
}
