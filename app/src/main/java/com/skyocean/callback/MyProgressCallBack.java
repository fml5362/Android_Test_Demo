package com.skyocean.callback;


/**
 * 作者：李付领
 * 描述：
 * <p/>
 * 创建时间：${DATA} 10:03
 * 版本：
 */
public class MyProgressCallBack<T>{

    public void onSuccess(T result) {
        //根据公司业务需求，处理相应业务逻辑
    }

    public void onError(Throwable ex, boolean isOnCallback) {
        //根据公司业务需求，处理相应业务逻辑
    }

    public void onCancelled(String cex) {

    }

    public void onFinished() {

    }

    public void onWaiting() {

    }

    public void onStarted() {

    }

    public void onLoading(long total, long current, boolean isDownloading) {

    }

}

