package com.liicon.adapter;

import java.util.List;

import android.content.Context;

import com.android.volley.VolleyError;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.log.LLog;
import com.liicon.utils.log.Level;
import com.liicon.volley.error.VolleyErrorHelper;

public abstract class BasicAdapter<T> extends AbsQuickAdapter<T> {

    public BasicAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }
    public BasicAdapter(Context context, int layoutResId, List<T> data) {
        super(context,layoutResId,data);
    }
    
	
	public void replaceAll(List<T> result) {
		if (result == null) {
			super.clear();
		} else {
			super.replaceAll(result);
		}
	}


	public void toast(VolleyError error, boolean isLong) {
	    toast(VolleyErrorHelper.getMessage(error, context), isLong);
	}
	public void toast(String text, boolean isLong) {
		ToastUtils.show(context, text, isLong);
	}
	public void log(Level level, Object... messages) {
		LLog.log(level, messages);
	}
	
}
