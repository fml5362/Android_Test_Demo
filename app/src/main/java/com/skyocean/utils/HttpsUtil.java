package com.skyocean.utils;

import android.content.Context;
import android.util.Log;

import com.skyocean.callback.ReqestCallBack;
import com.skyocean.ui.base.MyApplication;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by DY on 2016/11/24.
 */

public class HttpsUtil {
    /**
     * Https 证书验证对象
     */
    private static SSLContext s_sSLContext = null;

    /**
     * https的Post请求
     * @param callBack
     */
    public static void sendPost(String url,Map<String, Object> map, final ReqestCallBack callBack) {
        send(url, map, new Callback.CommonCallback<String>() {
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
     * https 上传文件
     * @param callBack
     */
    public void uploadFile(String url,Map<String, Object> map, final ReqestCallBack callBack){

        try {
            sendSync(url, map, new Callback.TypedCallback<String>() {
                @Override
                public Type getLoadType() {
                    return null;
                }

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
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Https请求发送
     *
     * @param map   发送的请求
     * @param callBack 回调对象（具体接口形式参见xUtils sample的httpFragment.java）
     * @return true=正常调用 false＝异常调用
     */
    public static boolean send(String url,Map<String, Object> map ,Callback.CommonCallback callBack) {
        /* 判断https证书是否成功验证 */
        SSLContext sslContext = getSSLContext(MyApplication.getInstance());
        if (null == sslContext) {
            if (BuildConfig.DEBUG) Log.d("HttpUtil", "Error:Can't Get SSLContext!");
            return false;
        }
        //绑定SSL证书
        RequestParams params = new RequestParams(url);
        params.setSslSocketFactory(sslContext.getSocketFactory());
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().request(HttpMethod.POST, params, callBack);
        return true;
    }

    /**
     * Https下载图片
     *
     * @param context Activity（fragment）的资源上下文
     * @return InputStream
     */
    public static InputStream getRequestInputstream(Context context, String path) throws Exception {
        /* 判断https证书是否成功验证 */
        SSLContext sslContext = getSSLContext(context);
        if (null == sslContext) {
            if (BuildConfig.DEBUG) Log.d("HttpUtil", "Error:Can't Get SSLContext!");
            return null;
        }
        //绑定SSL证书
        java.net.URL url = new java.net.URL(path);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(sslContext.getSocketFactory());
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //连接
        conn.connect();
        return conn.getInputStream();
    }

    /**
     * Https请求发送(同步请求 上传图片使用)
     *
     * @param map   发送的请求
     * @param callBack 回调对象（具体接口形式参见xUtils sample的httpFragment.java）
     * @return true=正常调用 false＝异常调用
     */
    public static boolean sendSync(String url ,Map<String , Object> map, Callback.TypedCallback callBack) throws Throwable {
        /* 判断https证书是否成功验证 */
        SSLContext sslContext = getSSLContext(MyApplication.getInstance());
        if (null == sslContext) {
            if (BuildConfig.DEBUG) Log.d("HttpUtil", "Error:Can't Get SSLContext!");
            return false;
        }
        //绑定SSL证书
        //绑定SSL证书
        RequestParams params = new RequestParams(url);
        params.setSslSocketFactory(sslContext.getSocketFactory());
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().requestSync(HttpMethod.POST, params, callBack);
        return true;
    }

    /**
     * 获取Https的证书
     *
     * @param context Activity（fragment）的上下文
     * @return SSL的上下文对象
     */
    private static SSLContext getSSLContext(Context context) {
        if (null != s_sSLContext) {
            return s_sSLContext;
        }

        //以下代码来自百度 参见http://www.tuicool.com/articles/vmUZf2
        CertificateFactory certificateFactory = null;

        InputStream inputStream = null;
        KeyStore keystore = null;
        String tmfAlgorithm = null;
        TrustManagerFactory trustManagerFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");

            inputStream = context.getAssets().open("server.cer");//这里导入SSL证书文件
//            inputStream = context.getAssets().open("51p2b_server_bs.pem");//这里导入SSL证书文件

            Certificate ca = certificateFactory.generateCertificate(inputStream);

            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, null);
            keystore.setCertificateEntry("ca", ca);

            tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keystore);

            // Create an SSLContext that uses our TrustManager
            s_sSLContext = SSLContext.getInstance("TLS");
            s_sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return s_sSLContext;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
