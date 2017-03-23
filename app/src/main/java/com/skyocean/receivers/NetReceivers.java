package com.skyocean.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;

import com.skyocean.utils.NetUtils;
import com.skyocean.view.dialog.AlertDialog;

/**
 * Created by DY on 2016/11/23.
 */

public class NetReceivers extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetUtils.isNetworkConnected(context);
            System.out.println("网络状态：" + isConnected);
            System.out.println("wifi状态：" + NetUtils.isWifiConnected(context));
            System.out.println("移动网络状态：" + NetUtils.isMobileConnected(context));
            System.out.println("网络连接类型：" + NetUtils.getConnectedType(context));
            if (isConnected) {
            } else {
                new AlertDialog(context).builder().setTitle("提示").setMsg("检测到无网络连接")
                        .setPositiveButton("设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = null;
                                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                context.startActivity(intent);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        }
    }

}

