package com.skyocean.callback;

import com.alibaba.fastjson.JSON;

/**
 * Created by DY on 2016/11/24.
 */

abstract public class ReqestCallBack {
    //可以根据公司的需求进行统一的请求成功的逻辑处理
    public void onSuccess(String result) {
        if (result != null){
            BaseBean baseBean = JSON.parseObject(result , BaseBean.class);
            onSuceess(result , baseBean);
        }else {
            onSuceess(null , null);
        }

    }

    //可以根据公司的需求进行统一的请求网络失败的逻辑处理
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    public void onCancelled(String cex) {

    }

    public void onFinished() {

    }

    public void onSuceess(String result , BaseBean baseBean){

    }
}
