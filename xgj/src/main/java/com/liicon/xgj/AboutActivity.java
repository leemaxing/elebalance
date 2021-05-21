package com.liicon.xgj;

import android.os.Bundle;

import com.liicon.xgj.base.BaseActivity;

/**
 * 关于我们
 * @author king
 */
public class AboutActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setNavigationBar();
        setTitle("关于我们");

    }
    
}
