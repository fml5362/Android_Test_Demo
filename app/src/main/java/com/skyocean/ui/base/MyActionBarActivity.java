package com.skyocean.ui.base;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyocean.R;

/**
 * Created by DY on 2016/11/23.
 */

public class MyActionBarActivity extends FragmentActivity {
    /** 标题 */
    public ActionBar actionBar;
    /** 标题View */
    public View titleTabView;
    /** 左侧文字 */
    public TextView TiTLeft;
    /** 左侧图片 */
    public ImageView ImgLeft;
    /** 中间标题 */
    public TextView TiTCenter;
    /** 右边文字 */
    public TextView TiTRight;
    /** 右边标题 */
    public ImageView ImgRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    /**
     * 标题
     */
    @SuppressLint({ "NewApi", "InflateParams" })
    public void initActionBar() {
        if (getActionBar() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorAccent));
            actionBar = getActionBar();
            this.actionBar.setTitle("");
            this.actionBar.setBackgroundDrawable(colorDrawable);
            this.actionBar.setDisplayShowHomeEnabled(false);
            this.actionBar.setDisplayShowTitleEnabled(true);
            this.actionBar.setDisplayHomeAsUpEnabled(false);
            this.actionBar.setLogo(null);
            this.actionBar.setHomeButtonEnabled(true);
            this.actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.actionBar.setDisplayShowCustomEnabled(true);
            titleTabView = LayoutInflater.from(this).inflate(R.layout.head_title, null);
            this.actionBar.setCustomView(titleTabView);
            this.actionBar.show();
            TiTCenter = (TextView) titleTabView.findViewById(R.id.center_text);
            TiTLeft = (TextView) titleTabView.findViewById(R.id.left_text);
            ImgLeft = (ImageView) titleTabView.findViewById(R.id.left_img);
            TiTRight = (TextView) titleTabView.findViewById(R.id.right_text);
            ImgRight = (ImageView) titleTabView.findViewById(R.id.right_img);
        }
    }
    /**
     * 不需要标题栏时隐藏
     */
    public void setActionBarVib() {
        //.getSupportActionBar().hide();
        actionBar.hide();
    }

    /**
     * 设置头部样式是否显示
     *
     * @param Tl
     *            左侧文本
     * @param Il
     *            左侧图片
     * @param Cr
     *            中间标题
     * @param Tr
     *            右边文本
     * @param Ir
     *            右边标题
     */
    public void TitleVisib(int Tl, int Il, int Cr, int Tr, int Ir) {
        if (Tl == 1)
            TiTLeft.setVisibility(View.VISIBLE);
        if (Il == 1)
            ImgLeft.setVisibility(View.VISIBLE);
        if (Cr == 1)
            TiTCenter.setVisibility(View.VISIBLE);
        if (Tr == 1)
            TiTRight.setVisibility(View.VISIBLE);
        if (Ir == 1)
            ImgRight.setVisibility(View.VISIBLE);
    }
    /**
     * 验证是否为隐藏状态
     *
     * @param v
     * @return
     */
    private Boolean isVisib(View v) {
        if (v.getVisibility() == View.INVISIBLE) {
            return false;
        }
        return true;
    }
}