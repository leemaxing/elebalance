package com.liicon.xgj.base;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 将ListView转换成GridView的展示形式的Adapter
 * @author King
 * @version v1.0
 * @created 2014-1-10
 * @change
 *   	2014-1-13 修改getViewItem方法，分离getViewLayout、getViewData，以提高效率
 */
public abstract class ListToGridAdapter<T> extends BearListAdapter<T> {
	/** 默认的column大小 */
	public static final int Defult_ColumnSize = 3;
	
	/**
	 * 每项的宽度（item的宽度）
	 */
	private int itemWidth = 0;
	
	
	/**
	 * 每行的item个数
	 */
	private int numColumns = Defult_ColumnSize;
	/**
	 * item横向间距
	 */
	private int horizontalSpacing = 0;
	/**
	 * item竖向间距
	 */
	private int verticalSpacing = 0;
	
	
	public ListToGridAdapter(final Context ctx, final List<T> list) {
		this(ctx, list, Defult_ColumnSize);
	}
	public ListToGridAdapter(final Context ctx, final List<T> list, final int numColumns) {
		super(ctx, list);
		setNumColumns(numColumns);
		
		reset();
	}

	/**
	 * 重写getViewItem，实现类似GridView的效果
	 */
	protected final View getViewItem(final int position, View convertView, final T current) {
		LinearLayout chlidView;
		if (convertView == null) {
			chlidView = new LinearLayout(context);
			chlidView.setOrientation(LinearLayout.HORIZONTAL);
			chlidView.setMinimumHeight(2);
		} else {
			chlidView = (LinearLayout) convertView;
		}
		chlidView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		
		if (itemWidth > 0) {
			getCurrentViewItem(position, chlidView, current);
		} else {
			final ViewGroup view = chlidView;
			chlidView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					view.getViewTreeObserver().removeOnPreDrawListener(this);
					
					itemWidth = view.getWidth() / 3 - horizontalSpacing;
					getCurrentViewItem(position, view, current);
					return false;
				}
			});
		}
		
		return convertView = chlidView;
	}
	

	/**
	 * 真正获取ItemView的方法
	 * @param chlidView
	 * @param current 当前Item对应的数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void getCurrentViewItem(final int position, ViewGroup chlidView, final T current) {
		View itemView = null;
		for (int i=position*numColumns; i<(position+1)*numColumns; i++) {
			itemView = chlidView.getChildAt(i % numColumns);
			
			if (i < super.getCount()) {
				if (itemView == null) {
					// 重新获取布局
					itemView = this.getViewLayout(i, null, chlidView);

					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
					params.setMargins(horizontalSpacing / 2, verticalSpacing / 2, horizontalSpacing / 2, verticalSpacing / 2);
					chlidView.addView(itemView, params);
				}
				
				// 每次都重新获取数据
				this.getViewData(i, itemView, (T) getItem(i));
				if (getData().size() == 1 && isEmpty(getData().get(0))) {
					itemView.setVisibility(View.INVISIBLE);
				} else {
					itemView.setVisibility(View.VISIBLE);
				}
			} else {
				// 超过最后一行应该显示的item个数，则从最后面依次删除
				if (chlidView.getChildCount() > super.getCount() % numColumns) {
					chlidView.removeViewAt(chlidView.getChildCount() - 1);
				}
			}
			
			itemView = null;
		}
	}
	
	

	/**
	 * 仅获取布局
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getViewLayout(int position, View convertView, ViewGroup parent);
	
	/**
	 * 仅获取数据
	 * @param position
	 * @param convertView
	 * @param current
	 */
	public abstract void getViewData(int position, View convertView, final T current);
	
	
	
	
	
	/**
	 * 设置列的个数
	 * @param numColumns
	 */
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
	/**
	 * 设置列宽
	 * @param itemWidth
	 */
	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}
	/**
	 * 设置列横向间距
	 * @param horizontalSpacing
	 */
	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}
	/**
	 * 设置列竖向间距
	 * @param verticalSpacing
	 */
	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}
	

	public int getItemWidth() {
		return itemWidth;
	}
	public int getHorizontalSpacing() {
		return horizontalSpacing;
	}
	public int getVerticalSpacing() {
		return verticalSpacing;
	}
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * 重写getCount方法
	 */
	@Override
	public int getCount() {
		reset();
		return (super.getCount()-1) / numColumns + 1;
	}

	@Override
	public void notifyDataSetChanged() {
		reset();
		super.notifyDataSetChanged();
	}
	
	private void reset() {
		if (getData().size() < 1) {
			try {
				@SuppressWarnings("unchecked")
				Class<T> clazz = (Class<T>) getGenericType(getClass(), 0);
				getData().add(clazz.newInstance());
			} catch (Exception e) { }
		} else if (getData().size() > 1) {
			if (isEmpty(getData().get(0))) {
				getData().remove(0);
			} else if (isEmpty(getData().get(getData().size()-1))) {
				getData().remove(getData().size()-1);
			}
		}
	}
	/**
	 * 获取泛型的类型
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Type getGenericType(Class<?> clazz, int index) {
		ParameterizedType pType = (ParameterizedType) clazz.getGenericSuperclass();
		return pType.getActualTypeArguments()[index];
	}
	
	protected abstract boolean isEmpty(T item);
}
