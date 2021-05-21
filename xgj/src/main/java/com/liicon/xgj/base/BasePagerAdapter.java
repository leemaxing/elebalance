package com.liicon.xgj.base;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * ViewPager的适配器
 */
public class BasePagerAdapter extends PagerAdapter {
	private List<View> mListViews;
	
	/**
	 * 构造方法
	 * @param listViews
	 */
	public BasePagerAdapter(List<View> listViews) {
		super();
		this.mListViews = listViews;
	}
	
	@Override
	public int getCount() {
		return mListViews.size();
	}
	
	public List<View> getListView() {
		return mListViews;
	}

	/**
	 * 从指定的position创建page
	 *
	 * @param position The page position to be instantiated.
	 * @return 返回指定position的page，这里不需要是一个view，也可以是其他的视图容器.
	 */
	@Override
	public View instantiateItem(View collection, int position) {
		((ViewGroup) collection).addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

	/**
	 * 从指定的position销毁page 参数同上
	 */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewGroup) collection).removeView(mListViews.get(position));
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (object);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
	
}
