package com.skyocean.utils;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.skyocean.R;
import com.skyocean.callback.MyProgressCallBack;
import com.skyocean.callback.ReqestCallBack;

import java.io.File;
import java.util.Map;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;



/**
 * 作者：李付领
 * 描述：
 * <p>
 * 创建时间：${DATA} 9:24
 * 版本：
 */
public class HttpUtil {

    /**
     * 发送get请求
     */
    public static void httpGet(String url, Map<String, String> map, final ReqestCallBack callBack) {
        Get(url, map, new CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });
    }

    /**
     * 发送post请求
     */
    public static void httpPost(String url, Map<String, Object> map,final ReqestCallBack callBack) {

        Post(url, map, new CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });
    }

    /**
     * 上传文件
     */

    public static void httpUploadFile(String url, Map<String, Object> map,final ReqestCallBack callBack) {
        UpLoadFile(url, map, new CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });
    }


    /**
     * 文件下载
     */
    public static void httpDownLoadFile(String url, String filePath, final MyProgressCallBack<File> callBack) {
        DownLoadFile(url, filePath, new CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });

    }

    /**
     * 下载文件，带进度
     */
    public static void httpDownLoadProgressFile(String url, String filePath,final MyProgressCallBack<File> downLoadProgressCallBack) {
        DownLoadFile(url, filePath, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                downLoadProgressCallBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                downLoadProgressCallBack.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                downLoadProgressCallBack.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                downLoadProgressCallBack.onFinished();
            }

            @Override
            public void onWaiting() {
                downLoadProgressCallBack.onWaiting();
            }

            @Override
            public void onStarted() {
                downLoadProgressCallBack.onStarted();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                downLoadProgressCallBack.onLoading(total, current, isDownloading);
            }
        });
    }

    /**
     * S
     * 发送get请求
     *
     * @param <T>
     */
    public static <T> Cancelable Get(String url, Map<String, String> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     *
     * @param <T>
     */
    public static <T> Cancelable Post(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Cancelable UpLoadFile(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Cancelable DownLoadFile(String url, String filepath, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


    /***************
     * 关于图片
     *****************/

    public static void httpGetImageFromUrl(ImageView imageView, String imgurl) {
        x.image().bind(imageView, imgurl, getImageOption(), new CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

                System.out.println("========================" + "onSuccess");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("========================" + "onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("========================" + "onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("========================" + "onFinished");
            }
        });


    }

    private static ImageOptions getImageOption() {
        ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                //设置加载失败后的图片
//                .setFailureDrawableId(R.drawable.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
//                .setCircular(true)
                //设置支持gif
                .setIgnoreGif(false)
                //设置圆角
                .setRadius(50)
                .setCrop(true)

//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
//                .setRadius(DensityUtil.dip2px(20))//ImageView圆角半径
//                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)//缩放
//                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
//                .setUseMemCache(true)//设置使用缓存
//                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                .build();

        return options;
    }
}