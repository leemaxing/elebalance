package com.liicon.ui.activity;

import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.log.LLog;
import com.liicon.utils.log.Level;

/**
 * 辅助快速开发的Activity基类
 * @version  v0.1  king  2014-12-18  简化常用功能的实现
 */
public abstract class AbsQuickActivity extends AbsLifeCycleActivity {
    
    public void toast(String text) {
        ToastUtils.show(this, text);
    }
    
    public void log(Level level, Object... messages) {
        LLog.log(level, messages);
    }
    
}
