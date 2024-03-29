package com.liicon.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the convert() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * @param <T> The type of the items in the list.
 */
public abstract class AbsQuickAdapter<T> extends AbsBaseQuickAdapter<T, BasicAdapterHelper> {
	/**
     * Create a QuickAdapter.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public AbsQuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public AbsQuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context,layoutResId,data);
    }

	protected BasicAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
		return BasicAdapterHelper.get(context, convertView, parent, layoutResId, position);
	}
}
