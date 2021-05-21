package com.liicon.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

/**
 * Activity拓展增强
 * @version  v0.1  king  2014-12-18  Activity拓展增强
 */
class Activity extends android.app.Activity {
    
    /**
     * Activity跳转
     * @param clazz 目标Activity
     */
    public void to(Class<? extends android.app.Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }
    /**
     * Activity跳转
     * @param clazz 目标Activity
     * @param bundle 参数
     */
    public void to(Class<? extends android.app.Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    
    

    /**
     * 从资源文件中获取动画
     * @param resId 资源ID
     * @return 动画
     */
    public Animation getAnimation(int resId) {
        return AnimationUtils.loadAnimation(this, resId);
    }
    /**
     * 从资源文件中获取动画
     * @param resId 资源ID
     * @return 动画
     */
    public LayoutAnimationController getLayoutAnimation(int resId) {
        return AnimationUtils.loadLayoutAnimation(this, resId);
    }
    /**
     * 从资源文件中获取尺寸
     * @param resId 资源ID
     * @return 尺寸（根据DisplayMetrics自动转换）
     */
    public float getDimension(int resId) {
        return getResources().getDimension(resId);
    }
    /**
     * 从资源文件中获取尺寸
     * @param resId 资源ID
     * @return 尺寸（像素偏移）
     */
    public float getDimensionPixelOffset(int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }
    /**
     * 从资源文件中获取尺寸
     * @param resId 资源ID
     * @return 尺寸（像素）
     */
    public float getDimensionPixelSize(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }
    /**
     * 从资源文件中获取动画
     * @param resId 资源ID
     * @param theme {@linkplain android.content.res.Resources.Theme 主题}
     * @return 动画
     */
    public Drawable getDrawable(int resId, Theme theme) {
        return getResources().getDrawable(resId, theme);
    }
    /**
     * 从资源文件中获取动画
     * @param resId 资源ID
     * @return 动画
     */
    public String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
    
}
