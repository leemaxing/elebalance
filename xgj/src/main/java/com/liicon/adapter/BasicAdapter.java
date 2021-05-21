package com.liicon.adapter;

import java.util.List;

import android.content.Context;


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
	
}
