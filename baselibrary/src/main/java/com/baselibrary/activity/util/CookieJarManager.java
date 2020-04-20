package com.baselibrary.activity.util;

import android.content.Context;

import com.baselibrary.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/11/9 12:07
 * <p>
 * CookieJarManager
 */

public class CookieJarManager implements CookieJar {
    private static final String FILE_NAME = "cookie";
    private Context context;
    private HashMap<String, List<Cookie>> cookieStore;

    public CookieJarManager(Context context) {
        this.context = context;
        this.cookieStore = new HashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookieStore.size() == 0 && cookies.size() > 0) {
            putCookie(url, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookieStore.size() == 0 && getCookie(url).size() > 0) {
            cookieStore.put(url.host(), getCookie(url));
        }
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies == null ? new ArrayList<Cookie>() : cookies;
    }

    /**
     * 保存Cookie
     *
     * @param url
     * @param cookies
     */
    private void putCookie(HttpUrl url, List<Cookie> cookies) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cookie>>() {
        }.getType();
        String json = gson.toJson(cookies, type);
        SharedPreferencesUtil.put(context, FILE_NAME, url.host(), json);
    }

    /**
     * 请求Cookie
     *
     * @param url
     * @return
     */
    public List<Cookie> getCookie(HttpUrl url) {
        List<Cookie> cookies = null;
        String json = (String) SharedPreferencesUtil.get(context, FILE_NAME, url.host(), "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cookie>>() {
        }.getType();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        if (jsonElement.isJsonArray()) {
            cookies = gson.fromJson(json, type);
        }
        return cookies == null ? new ArrayList<Cookie>() : cookies;
    }

    /**
     * 清除Cookie
     */
    public void clearCookie() {
        SharedPreferencesUtil.clear(context, FILE_NAME);
        cookieStore.clear();
    }

}
