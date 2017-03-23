package com.skyocean.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.skyocean.R;
import com.skyocean.ui.base.BaseActivity;
import com.skyocean.ui.base.MainActivity;


/**
 * Created by DY on 2016/11/23.
 */

public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 1:{
                    //跳转到主界面
                    GoActivity(MainActivity.class);
                    finish();
                }
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //隐藏actionBar
        setActionBarVib();
        initView();
    }

    private void initView() {

        mHandler.sendEmptyMessageDelayed(1 , 2000);
    }
}
