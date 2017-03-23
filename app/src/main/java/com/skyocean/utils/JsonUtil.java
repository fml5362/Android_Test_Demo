package com.skyocean.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by DY on 2016/12/7.
 */

public class JsonUtil {
    /**
     * 解析成单个对象
     *
     * @param text
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> T) {
        if (text != null) {
            return JSON.parseObject(text, T);
        }
        return null;
    }

    /**
     * 解析成数组
     *
     * @param text
     * @param T
     * @param <T>
     * @return
     */
    public static <T> List<T> parseObjectArray(String text, Class<T> T) {
        if (text != null) {
            return JSON.parseArray(text, T);
        }
        return null;
    }


    /**
     * 解析指定的java类为string
     */
    public static String getjsonString(Class<?> T) {
        return JSON.toJSONString(T);
    }
}
