package com.baselibrary.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2018/1/12 14:38
 * <p>
 * ByteUtil
 */

public class ByteUtil {

    private ByteUtil() {
        throw new UnsupportedOperationException("ByteUtil cannot be instantiated");
    }

    public static byte[] binaryToDecimal(int n) {
        byte[] b = new byte[2];
        String str = "";
        int i = 0;
        while (n != 0 && i < 2) {
            str = n % 2 + str;
            b[b.length - 1 - i] = (byte) (n % 2);
            n = n / 2;
            i++;
        }
        return b;

    }

    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getBitArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /**
     * 将byte[]转换为一个的byte数组，数组每个值代表bit
     */
    public static byte[] getBitArray(byte[] bytes) {
        byte[] array = new byte[8 * bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte[] b = getBitArray(bytes[i]);
            for (int j = 0; j < 8; j++) {
                array[i * 8 + j] = b[j];
            }
        }
        return array;
    }

    /**
     * 将byte[]转换为一个的byte数组，数组每个值代表bit
     */
//    public static byte bitToByte(String str) {
//        return Byte.valueOf(str);
//    }
    public static byte bitToByte(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {//4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }

    /**
     * 整数转字节数组
     *
     * @param i 十进制整数
     * @return 二进制数（4字节）
     */
    public static byte[] shortToByteArray(short i) {
        byte[] b = new byte[2];
        b[1] = (byte) (i & 0xff);
        b[0] = (byte) ((i >> 8) & 0xff);
        return b;
    }

    /**
     * 整数转字节数组
     *
     * @param b 二进制数（2字节）
     * @return 十进制整数
     */
    public static short bytesToShort(byte[] b) {
        return (short) (b[1] & 0xff | (b[0] & 0xff) << 8);
    }


    /**
     * 整数转字节数组
     *
     * @param i 十进制整数
     * @return 二进制数（4字节）
     */
    public static byte[] intToByteArray(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((i >> 24) & 0xFF);
        bytes[1] = (byte) ((i >> 16) & 0xFF);
        bytes[2] = (byte) ((i >> 8) & 0xFF);
        bytes[3] = (byte) (i & 0xFF);
        return bytes;
    }

    /**
     * 字节数组转整数
     *
     * @param b 二进制数（4字节）
     * @return 十进制整数
     */
    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            value += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return value;
    }

    /**
     * 字节数组转整数
     *
     * @param b      二进制数（4字节）
     * @param offset 初始偏移量
     * @return 十进制整数
     */
    public static int byteArrayToInt(byte[] b, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        byteBuffer.put(b, offset, 4);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    /**
     * 字节数组转整形
     *
     * @param b      二进制数（4字节）
     * @param offset 初始偏移量
     * @param length 长度
     * @return 十进制整数
     */
    public static int byteArrayToInt(byte[] b, int offset, int length) {
        int intValue = 0;
        for (int i = offset; i < (offset + length); i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - (i - offset)));
        }
        return intValue;
    }

    /**
     * 16进制整数转字节数组
     *
     * @param i 十六进制整数
     * @return 二进制数（2字节）
     */
    public static byte[] hexToByteArray(int i) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) i;
        bytes[0] = (byte) (i >>> 8);
        return bytes;
    }

    /**
     * 字节数组转16进制整数
     *
     * @param b 二进制数（2字节）
     * @return 十六进制整数
     */
    public static int byteArrayToHex(byte[] b) {
        int res = ((b[0] << 8) | ((b[1] << 24) >>> 24));
        return res;
    }

    /**
     * 长整数转字节数组
     *
     * @param l 十进制长整数
     * @return 二进制数（8字节）
     */
    public static byte[] longToByteArray(long l) {
        byte[] bytes = new byte[8];
        bytes[7] = (byte) l;
        bytes[6] = (byte) (l >>> 8);
        bytes[5] = (byte) (l >>> 16);
        bytes[4] = (byte) (l >>> 24);
        bytes[3] = (byte) (l >>> 32);
        bytes[2] = (byte) (l >>> 40);
        bytes[1] = (byte) (l >>> 48);
        bytes[0] = (byte) (l >>> 56);
        return bytes;
    }

    /**
     * 字节数组转长整数
     *
     * @param b 二进制数（8字节）
     * @return 十进制长整数
     */
    public static long byteArrayTolong(byte[] b) {
        long l0 = b[0];
        long l1 = b[1];
        long l2 = b[2];
        long l3 = b[3];
        long l4 = b[4];
        long l5 = b[5];
        long l6 = b[6];
        long l7 = b[7];
        long l = (l0 << 56) | (((l1 << 56) >>> 56) << 48) | (((l2 << 56) >>> 56) << 40) | (((l3 << 56) >>> 56) << 32) | (((l4 << 56) >>> 56) << 24) | (((l5 << 56) >>> 56) << 16) | (((l6 << 56) >>> 56) << 8) | ((l7 << 56) >>> 56);
        return l;
    }

    /**
     * 单精度浮点数转字节数组
     *
     * @param f 十进制单精度浮点数
     * @return 二进制数（4字节）
     */
    public static byte[] floatToByteArray(float f) {
        byte[] bytes = new byte[4];
        int l = Float.floatToIntBits(f);
        for (int i = 3; i >= 0; i--) {
            bytes[i] = new Integer(l).byteValue();
            l >>= 8;
        }
        return bytes;
    }

    /**
     * 字节数组转单精度浮点数
     *
     * @param b 二进制数（4字节）
     * @return 十进制单精度浮点数
     */
    public static float byteArrayToFloat(byte[] b) {
        int l = byteArrayToInt(b);
        float res = Float.intBitsToFloat(l);
        return res;
    }

    /**
     * 双精度浮点数转字节数组
     *
     * @param d 十进制双精度浮点数
     * @return 二进制数（8字节）
     */
    public static byte[] doubleToByteArray(double d) {
        long l = Double.doubleToLongBits(d);
        byte[] bytes = longToByteArray(l);
        return bytes;
    }

    /**
     * 字节数组转双精度浮点数
     *
     * @param b 二进制数（8字节）
     * @return 十进制双精度浮点数
     */
    public static double byteArrayToDouble(byte[] b) {
        long l = byteArrayTolong(b);
        double d = Double.longBitsToDouble(l);
        return d;
    }

    public static int lengthByte(byte[] b) {
        int length = 0;
        for (int i = 0; i < b.length; ++i) {
            if (b[i] == (byte) 0) {
                length = i;
                break;
            }
        }
        return length;
    }

    /**
     * 对象转字节数组
     *
     * @param o 对象
     * @return 二进制数
     */
    public static byte[] objectToByteArray(Object o) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 字节数组转对象
     *
     * @param b 二进制数
     * @return 对象
     */
    public static Object byteArrayToObject(byte[] b) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(b);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * BCD码转字符串
     *
     * @param b 二进制数
     * @return 10进制字符串
     */
    public static String bcdToString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append((byte) ((b[i] & 0xf0) >>> 4));
            sb.append((byte) (b[i] & 0x0f));
        }
        return sb.toString().substring(0, 1).equalsIgnoreCase("0") ? sb
                .toString().substring(1) : sb.toString();
    }

    /**
     * 字符串转BCD码
     *
     * @param s 10进制字符串
     * @return 二进制数
     */
    public static byte[] stringToBcd(String s) {
        int len = s.length();
        int mod = len % 2;
        if (mod != 0) {
            s = "0" + s;
            len = s.length();
        }
        byte abt[] = s.getBytes();
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        int j, k;
        for (int p = 0; p < s.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }


    /**
     * @param size
     * @return 格式化单位
     */
    public static String formatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
