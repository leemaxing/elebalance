package com.liicon.xgj.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.liicon.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 适用于从List获取数据的Adapter
 * @author King
 * @version beta1.0.1
 * @created 2013.09.17
 * @param <T> List集合的数据类型，如：SparseArray&lt;String&gt;
 */
public abstract class BearListAdapter<T> extends AbsBaseAdapter<T> {
	protected final Context context;
	private AdapterView.OnItemClickListener onItemClickListener = null;

	public BearListAdapter(final Context context, final List<T> list) {
		this(context, list, null);
	}
	public BearListAdapter(final Context context, final List<T> list, AdapterView.OnItemClickListener onItemClickListener) {
		super(list);
		this.context = context;
		this.onItemClickListener = onItemClickListener;
	}


	/**
	 * 此方法用于手动调用时使用
	 * @param position
	 * @return
	 */
	public final View getView(final int position) {
		return this.getView(position, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public final View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = this.getViewItem(position, convertView, (T)super.getItem(position));
		if (view == null)
			throw new NullPointerException("'getViewItem()' Donot return null.");
		
		if (onItemClickListener != null)
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClick(null, convertView, position, position);
				}
			});
		return view;
	}
	
	/**
	 * 获取ItemView
	 * @param convertView
	 * @param current 当前Item对应的数�?
	 * @return
	 */
	protected abstract View getViewItem(final int position, View convertView, final T current);
	

	/**
	 * 设置ItemClick监听事件
	 * @param onItemClickListener
	 */
	public void setOnItemClick(AdapterView.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	
}
