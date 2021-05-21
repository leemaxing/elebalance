package com.liicon.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.liicon.application.BasicAppHelper;

/**
 * 生命周期管理Activity
 * @version  v0.1  king  2014.12.18  管理生命周期
 */
class AbsLifeCycleActionBarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 添加Activity到堆栈
        BasicAppHelper.get().getActivityManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 结束Activity&从堆栈中移除
        BasicAppHelper.get().getActivityManager().finishActivity(this);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onSetContentView();
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onSetContentView();
    }
    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onSetContentView();
    }
    
    /**
     * 调用setContentView()时执行的方法
     */
    protected void onSetContentView() {
    }
    
}
