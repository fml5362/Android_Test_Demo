package com.skyocean.ui.base;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skyocean.receivers.NetReceivers;


/**
 * Created by DY on 2016/11/23.
 * 房梦林
 */

public class BaseActivity extends MyActionBarActivity {

    private IntentFilter mFilter = new IntentFilter();
    private NetReceivers mReceiver = new NetReceivers();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //监听网络状态
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    /**
     * 切换Activity
     *
     * @param c
     *            需要切换到的Activity
     */
    public void GoActivity(Class<?> c) {
        Intent intent = new Intent(this, c);
        this.startActivity(intent);
    }

    /**
     * 消息处理
     */
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleMessage(msg);
        }
    };

    /**
     * 处理消息
     * @param msg
     */
    protected void handleMessage(Message msg){}

    /**
     * 弹出吐司
     * @param str
     */
    public void showToast(String str){
        Toast.makeText(getApplicationContext(), str, 0).show();
    }

    /**
     * 获取textView的值
     * @param textView
     * @return
     */
    public String getTextValue(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 获取textView的值
     * @param editText
     * @return
     */
    public String getEditeTextValue(EditText editText){
        return editText.getText().toString().trim();
    }

    /**
     * SD卡是否挂载
     * @return
     */
    public static boolean hasSDCard(){
        String state= Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    /**
     * 隐藏或显示软键盘
     */

    public void hideOrShowKeybroad(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
