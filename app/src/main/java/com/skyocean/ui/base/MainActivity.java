package com.skyocean.ui.base;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.skyocean.R;
import com.skyocean.ui.home.Home1;
import com.skyocean.ui.home.Home2;
import com.skyocean.ui.home.Home3;
import com.skyocean.ui.home.Home4;
import com.skyocean.ui.home.Home5;

/**
 * Created by DY on 2016/12/2.
 */

public class MainActivity extends BaseActivity {


    private Home1 home1;
    private Home2 home2;
    private Home3 home3;
    private Home4 home4;
    private Home5 home5;
    private RadioButton radio1,radio2,radio3,radio4 , radio5;
    private RadioGroup mRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);
        setActionBarVib();
        initView();
    }
    private void initView()
    {
        mRadioGroup=(RadioGroup) findViewById(R.id.menu_group);
        radio1=(RadioButton) findViewById(R.id.radio_home);
        radio2=(RadioButton) findViewById(R.id.radio_account);
        radio3=(RadioButton) findViewById(R.id.radio_money);
        radio4=(RadioButton) findViewById(R.id.radio_more);
        radio5=(RadioButton) findViewById(R.id.radio_five);
        handleAction();
        showFragment(1);
    }
    private void handleAction() {
        mRadioGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switchTab(i);
                    }
                });
    }

    protected void switchTab(int id) {
        switch (id) {
            case R.id.radio_home:
                showFragment(1);
                break;
            case R.id.radio_account:
                showFragment(2);
                break;
            case R.id.radio_money:
                showFragment(3);
                break;
            case R.id.radio_more:
                showFragment(4);
                break;
            case R.id.radio_five:
                showFragment(5);
                break;
        }
    }

    private void showFragment(int index) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        hideFragment(ft);
        switch (index) {
            case 1:
                if (home1 != null)
                    ft.show(home1);
                else {
                    home1 = new Home1();
                    ft.add(R.id.frame, home1);
                }
                break;
            case 2:
                if (home2 != null){
                    ft.show(home2);
                }
                else {
                    home2 = new Home2();
                    ft.add(R.id.frame, home2);
                }
                break;
            case 3:
                if (home3 != null){
                    ft.show(home3);
                }
                else {
                    home3 = new Home3();
                    ft.add(R.id.frame, home3);
                }
                break;
            case 4:
                if (home4 != null){
                    ft.show(home4);
                }
                else {
                    home4 = new Home4();
                    ft.add(R.id.frame, home4);
                }
                break;
            case 5:
                if (home5 != null){
                    ft.show(home5);
                }
                else {
                    home5 = new Home5();
                    ft.add(R.id.frame, home5);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (home1 != null)
            ft.hide(home1);
        if (home2 != null)
            ft.hide(home2);
        if (home3 != null)
            ft.hide(home3);
        if (home4 != null)
            ft.hide(home4);
        if (home5 != null)
            ft.hide(home5);
    }



}
