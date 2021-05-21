package com.liicon.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * ActionBarActivity拓展增强
 * @version  v0.1  king  2014-12-18  ActionBarActivity拓展增强
 */
class ActionBarActivity extends AppCompatActivity {
    
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
     * 根据类名生成Tag
     * @param clazz
     * @return
     */
    public String getFragmentTag(Class<? extends Fragment> clazz) {
        return clazz.getName();
    }
    /**
     * 替换Fragment
     * @param id
     * @param fragment
     * @param isBack
     */
    public void replaceFragment(int id, Fragment fragment, boolean isBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (isBack) transaction.addToBackStack(null);
//      transaction.commit();
        transaction.commitAllowingStateLoss();
    }
    /**
     * 添加Fragment
     * @param id
     * @param fragment
     * @param isBack
     */
    public void addFragment(int id, Fragment fragment, boolean isBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(id, fragment);
        if (isBack) transaction.addToBackStack(null);
//      transaction.commit();
        transaction.commitAllowingStateLoss();
    }
    /**
     * 根据ID查找Fragment
     * @param fid
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Fragment> T findFragmentById(int fid) {
        return (T) getSupportFragmentManager().findFragmentById(fid);
    }
    /**
     * 根据Tag查找Fragment
     * @param tag
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Fragment> T findFragmentByTag(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }
    /**
     * 根据Class查找Fragment
     * @param clzz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Fragment> T findFragmentByClass(Class<T> clzz) {
        return (T) getSupportFragmentManager().findFragmentByTag(getFragmentTag(clzz));
    }
    /**
     * 所有可back的Fragment全部回退
     */
    public void popBackStackAll() {
        while (getSupportFragmentManager().popBackStackImmediate()) {}
    }
    
}
