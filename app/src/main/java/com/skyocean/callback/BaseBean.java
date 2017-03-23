package com.skyocean.callback;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by DY on 2016/12/2.
 */

public class BaseBean {

    private String resultCode;
    private String resultMsg;
    private String data;

    /**
     * 获取单个对象
     * @param T
     * @param <T>
     * @return
     */
    public <T> T getDataToObject(Class<T> T){
        if (data != null){
            return JSON.parseObject(data, T);
        }else {
            return null;
        }
    }

    /**
     * 获取指定对象的集合
     * @param T
     * @param <T>
     * @return
     */
    public <T> List<T>  getDataToList(Class<T> T){
        if (data != null){
            return JSON.parseArray(data , T);
        }else {
            return null;
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
