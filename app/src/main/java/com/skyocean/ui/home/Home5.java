package com.skyocean.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyocean.R;
import com.skyocean.ui.base.BaseFragment;

/**
 * Created by DY on 2016/12/7.
 */

public class Home5 extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        TextView t=(TextView) view.findViewById(R.id.homeid);
        t.setText("Home5");
        TextView title=(TextView) view.findViewById(R.id.center_text);
        title.setText("Home5");
        return view;
    }
}
