package com.baselibrary.ftp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/6/8 12:25
 * <p>
 * FTPUtil
 */
public class FTPUtil {

    private FTPUtil() {
        throw new UnsupportedOperationException("FTPUtil cannot be instantiated");
    }

    /**
     * 解析用户
     *
     * @param address
     * @return
     */
    public static String parseUser(String address) {
        if (address != null || address.length() != 0) {
            String regex = "ftp://(.*?):(\\w|.)+@.+(:\\d{1,5})?/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            while (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 解析密码
     *
     * @param address
     * @return
     */
    public static String parsePass(String address) {
        if (address != null || address.length() != 0) {
            String regex = "ftp://\\w+\\:(.*?)@.+(:\\d{1,5})?/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            while (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 解析密码
     *
     * @param address
     * @return
     */
    public static String parseHost(String address) {
        if (address != null || address.length() != 0) {
            String regex = "ftp://\\w+\\:(\\w|.)+@(.*?)(:\\d{1,5})?/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            while (matcher.find()) {
                return matcher.group(2);
            }
        }
        return null;
    }

    /**
     * 解析端口
     *
     * @param address
     * @return
     */
    public static int parsePort(String address) {
        if (address != null || address.length() != 0) {
            String regex = "ftp://\\w+\\:(\\w|.)+@.:(.*?)/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            while (matcher.find()) {
                String port = matcher.group(2);
                if (Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(port).matches()) {
                    return Integer.parseInt(port);
                }
            }
        }
        return 21;
    }

    /**
     * 检测地址有效性
     *
     * @param address
     * @return
     */
    public static boolean checkAddress(String address) {
        if (address != null || address.length() != 0) {
            String regex = "ftp://\\w+\\:(\\w|.)+@.+(:\\d{1,5})?/";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String address = "ftp://gokeftp:gokeftp@mszj.iguyee.cn:8080/";
        boolean check = checkAddress(address);
        String user = parseUser(address);
        String pass = parsePass(address);
        String host = parseHost(address);
        int port = parsePort(address);
    }

}
