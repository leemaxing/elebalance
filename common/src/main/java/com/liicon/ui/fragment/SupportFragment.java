package com.liicon.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.log.LLog;
import com.liicon.utils.log.Level;
import com.liicon.volley.error.VolleyErrorHelper;

/**
 * V4兼容支持的Fragment
 * @version  v0.1  king  2014-12-24  V4兼容支持
 */
public class SupportFragment extends Fragment {

    public SupportFragment(){
        super();
    }

    private Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }
    public Activity getParentActivity() {
        if (mActivity == null) {
            this.mActivity = super.getActivity();
        }
        return mActivity;
    }
    public Context getContext() {
        Context context = getParentActivity();
        if (context == null) {
            View view = getView();
            if (view != null) {
                context = view.getContext();
            }
        }
        return context;
    }

    
    
    
    
    public int getColor(int colorId) {
        return getResources().getColor(colorId);
    }


    
    
    public View findViewById(int id) {
        return findViewById(id, getView());
    }
    public View findViewById(int id, View root) {
        if (root == null)
            return null;
        return root.findViewById(id);
    }
    
    

    public void toast(VolleyError error) {
        toast(VolleyErrorHelper.getMessage(error, getContext()));
    }
    public void toast(String text) {
        ToastUtils.show(getContext(), text);
    }
    
    public void log(Level level, Object... messages) {
        LLog.log(level, messages);
    }

    /**
     * Activity跳转
     */
    public void to(Intent intent) {
        startActivity(intent);
    }
    /**
     * Activity跳转
     * @param clazz 目标Activity
     */
    public void to(Class<? extends Activity> clazz) {
        to(new Intent(getContext(), clazz));
    }
    /**
     * Activity跳转
     * @param clazz 目标Activity
     * @param bundle 参数
     */
    public void to(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null && !bundle.isEmpty()) {
            intent.putExtras(bundle);
        }
        to(intent);
    }
    
    
    
}
