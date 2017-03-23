package com.skyocean.utils;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.skyocean.ui.base.MyApplication;

import java.util.List;

public class SPUtils {

    private static SharedPreferences prefs = MyApplication.getInstance().getSharedPreferences("sp_sky_ocean" , 0);
    /**
     * 保存指定对象到SP
     *
     * @param key
     * @param obj
     */
    public static void saveObject(String key, Object obj) {
        String str = JSON.toJSONString(obj);
        prefs.edit().putString(key, str).commit();
    }

    /**
     * 从SP中获取知行的对象
     *
     * @param key
     * @param classItem
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromPrefs(String key, Class<T> classItem) {
        String str = prefs.getString(key, null);
        return JsonUtil.parseObject(str, classItem);
    }

    /**
     * 从SP中获取知行的对象的集合
     *
     * @param key
     * @param classItem
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectListFromPrefs(String key, Class<T> classItem) {
        String str = prefs.getString(key, null);
        return JsonUtil.parseObjectArray(str, classItem);
    }



    /**
     * 保存字符串到SP
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    /**
     * 从SP中获取指定KEY的字符串
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return prefs.getString(key, null);
    }

    /**
     * 保存long类型到SP
     *
     * @param key
     * @param value
     */
    public static void saveLong(String key, long value) {
        prefs.edit().putLong(key, value).commit();
    }

    /**
     * 从SP中获取指定KEY的long类型
     *
     * @param key
     * @return
     */
    public static long getLong(String key) {
        return prefs.getLong(key, 0);
    }

    /**
     * 保存boolean类型到SP
     */
    public static void saveBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存float类型到SP
     */
    public static void saveFloat(String key, float value) {
        prefs.edit().putFloat(key, value).commit();
    }

    /**
     * 从SP中获取指定KEY的float类型
     *
     * @param key
     * @return
     */
    public static float getFloat(String key) {
        return prefs.getFloat(key, 0);
    }

    /**
     * 从SP中获取指定KEY的boolean类型  默认值为true
     *
     * @param key
     * @return
     */
    public static boolean getBooleanFromPrefs(String key) {
        return prefs.getBoolean(key, true);
    }

    /**
     * 从SP中获取指定KEY的boolean类型  默认值为false
     *
     * @param key
     * @return
     */
    public static boolean getBooleanNoValueIsFalse(String key) {
        return prefs.getBoolean(key, false);
    }

}
