package com.baselibrary.activity.util;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/11/9 12:42
 * <p>
 * CacheManager
 */

public class CacheManager {

    public static final long CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private static final String FILE_NAME = "http_cache";
    private Context context;
    private File cacheFile;

    public CacheManager(Context context) {
        this.context = context;
        this.cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), FILE_NAME);
    }

    /**
     * OkHttp缓存
     *
     * @return
     */
    public Cache getCache() {
        Cache cache = new Cache(cacheFile, CACHE_MAX_SIZE);
        return cache;
    }

}
