package com.liicon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * Adapter辅助类
 * @version  v0.1  king  2014-12-26  Adapter辅助类拓展
 */
public class BasicAdapterHelper extends AbsAdapterHelper {

    private BasicAdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        super(context, parent, layoutId, position);
    }

    /**
     * This method is the only entry point to get a BaseAdapterHelper.
     * @param context     The current context.
     * @param convertView The convertView arg passed to the getView() method.
     * @param parent      The parent arg passed to the getView() method.
     * @return A BaseAdapterHelper instance.
     */
    public static BasicAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId) {
        return get(context, convertView, parent, layoutId, -1);
    }

    /** This method is package private and should only be used by QuickAdapter. */
    static BasicAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BasicAdapterHelper(context, parent, layoutId, position);
        }

        // Retrieve the existing helper and update its position
        BasicAdapterHelper existingHelper = (BasicAdapterHelper) convertView.getTag();
        existingHelper.setPosition(position);
        return existingHelper;
    }
    
    
    

    /**
     * 设置控件是否可用
     */
    public BasicAdapterHelper setEnabled(int viewId, boolean enabled) {
        View view = getView(viewId);
        view.setEnabled(enabled);
        return this;
    }
    /**
     * 设置控件单击事件
     */
    public BasicAdapterHelper setOnClickListener(int viewId, View.OnClickListener listener) {
    	View view = getView(viewId);
    	view.setOnClickListener(listener);
    	return this;
    }
    
}
