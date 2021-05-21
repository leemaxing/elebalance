package com.liicon.xgj.base;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 自动计算展示的View的个数
 * @author King
 * @version v1.0
 * @created 2013-12-27
 */
public class AutoPagerAdapter extends PagerAdapter {
	public static final int Defult_ColumnSize = 3;
	public static final int Defult_LineSize = 3;
	
	private int columnSize = Defult_ColumnSize;
	private int lineSize = Defult_LineSize;
	private int pagerSize;
	
	/* 内边距 */
	private int mPaddingLeft = 0;
	private int mPaddingTop = 0;
	private int mPaddingRight = 0;
	private int mPaddingBottom = 0;
	
	protected Context context;
	private Adapter mAdapter;
	private View container;

	public AutoPagerAdapter(Context ctx) {
		this(ctx, null);
	}
	public AutoPagerAdapter(Context ctx, Adapter adapter) {
		this(ctx, Defult_LineSize, Defult_ColumnSize, adapter);
	}
	public AutoPagerAdapter(Context ctx, int line, int column, Adapter adapter) {
		this.context = ctx;
		this.mAdapter = adapter;
		setLineSize(line);
		setColumnSize(column);
	}
	
	
	public void setAdapter(Adapter adapter) {
		this.mAdapter = adapter;
		this.notifyDataSetChanged();
	}
	public void setLineSize(int line) {
		if (line > 0) {
			this.lineSize = line;
		} else {
			this.lineSize = 1;
		}
		
		countPager();
	}
	public void setColumnSize(int column) {
		this.columnSize = column;
		countPager();
	}
	/**
	 * 计算每页占用的View个数
	 */
	private void countPager() {
		this.pagerSize = columnSize * lineSize;
	}
	
	@Override
	public int getCount() {
		return (mAdapter==null) ? 0 : (mAdapter.getCount()-1) / pagerSize + 1;
	}

	/**
	 * 设置整页的内边距 
	 */
    public void setPadding(int left, int top, int right, int bottom) {
		this.mPaddingLeft = left;
		this.mPaddingTop = top;
		this.mPaddingRight = right;
		this.mPaddingBottom = bottom;
		
		// 更新内边距
		final int count = getCount();
		for (int position=0; position<count; position++) {
			if (container != null) {
				View root = container.findViewWithTag(getClass().getSimpleName()+position);
				if (root != null) {
					root.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
					root.postInvalidate();
				}
			}
		}
	}

	/**
	 * 从指定的position创建page
	 * 
	 * @param container ViewPager容器
	 * @param position The page position to be instantiated.
	 * @return 返回指定position的page，这里不需要是一个view，也可以是其他的视图容器.
	 */
	@Override
	public View instantiateItem(View container, int position) {
		//Log.e("", "instantiateItem Group "+position);
		this.container = container;
		
		LinearLayout chlidView = new LinearLayout(context);
		chlidView.setOrientation(LinearLayout.VERTICAL);
		chlidView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
		chlidView.setTag(getClass().getSimpleName()+position);

		LinearLayout itemView = null;
		for (int i=position*pagerSize; i<(position+1)*pagerSize; i++) {
			if (i%columnSize == 0) {
				itemView = new LinearLayout(context);
				itemView.setOrientation(LinearLayout.HORIZONTAL);
				itemView.setGravity(Gravity.CENTER);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
				chlidView.addView(itemView, params);
			}
			
			LinearLayout view = new LinearLayout(context);
			view.setGravity(Gravity.CENTER);
			
			final int count = (mAdapter==null) ? 0 : mAdapter.getCount();
			if (i < count) {
				View child = mAdapter.getView(i, container, null);
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
		        if (params == null) {
		            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
		            params.gravity = Gravity.CENTER;
		        }
				view.addView(child, params);
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
			params.gravity = Gravity.CENTER;
			itemView.addView(view, params);
		}

		ViewPager.LayoutParams params = new ViewPager.LayoutParams();
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		((ViewGroup) container).addView(chlidView, 0, params);
		return chlidView;
	}
	
	/**
	 * 从指定的position销毁page 参数同上
	 */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewGroup) collection).removeView((View) view);
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
