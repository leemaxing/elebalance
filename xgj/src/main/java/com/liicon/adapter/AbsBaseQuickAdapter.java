package com.liicon.adapter;

import java.util.List;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * 基础的快速开发Adapter
 * <p>
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the convert() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * </p>
 * @param <T> The type of the items in the list.
 */
abstract class AbsBaseQuickAdapter<T, H extends AbsAdapterHelper> extends AbsBaseAdapter<T> {
    protected final Context context;
	protected final int layoutResId;
	protected boolean displayIndeterminateProgress = false;

    /**
     * Create a QuickAdapter.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public AbsBaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public AbsBaseQuickAdapter(Context context, int layoutResId, List<T> data) {
    	super(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        int extra = displayIndeterminateProgress ? 1 : 0;
        return super.getCount() + extra;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= data.size() ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            final H helper = getAdapterHelper(position, convertView, parent);
            convert(position, helper, getItem(position));
            return helper.getView();
        }

        return createIndeterminateProgressView(convertView, parent);
    }

    private View createIndeterminateProgressView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            FrameLayout container = new FrameLayout(context);
            container.setForegroundGravity(Gravity.CENTER);
            ProgressBar progress = new ProgressBar(context);
            container.addView(progress);
            convertView = container;
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return position < data.size();
    }

    public void showIndeterminateProgress(boolean display) {
        if (display == displayIndeterminateProgress) return;
        displayIndeterminateProgress = display;
        notifyDataSetChanged();
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(int position, H helper, T item);

	/**
	 * You can override this method to use a custom BaseAdapterHelper in order to fit your needs
	 *
	 * @param position The position of the item within the adapter's data set of the item whose view we want.
	 * @param convertView The old view to reuse, if possible. Note: You should check that this view
	 *        is non-null and of an appropriate type before using. If it is not possible to convert
	 *        this view to display the correct data, this method can create a new view.
	 *        Heterogeneous lists can specify their number of view types, so that this View is
	 *        always of the right type (see {@link #getViewTypeCount()} and
	 *        {@link #getItemViewType(int)}).
	 * @param parent The parent that this view will eventually be attached to
	 * @return An instance of BaseAdapterHelper
	 */
	protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

}
