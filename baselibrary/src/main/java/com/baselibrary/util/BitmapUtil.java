package com.baselibrary.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {
    public static byte[] compressBitmap(Bitmap bitmap, float size) {
        if (bitmap == null) {
            return null;
        }
        boolean need = getSizeOfBitmap(bitmap) <= size;//如果图片本身的大小已经小于这个大小了，就没必要进行压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        int quality = 100;
        if (!need) {
            while (baos.toByteArray().length / 1024f > size) {
                quality = quality - 4;// 每次都减少4
                baos.reset();// 重置baos即清空baos
                if (quality <= 0) {
                    break;
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }
        }
        bitmap.recycle();
        bitmap = null;
        return baos.toByteArray();
    }


    private static float getSizeOfBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100的话表示不压缩质量
        float length = baos.toByteArray().length / 1024.0f;
        return length;
    }

}
