package com.liicon.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

/**
 * 滑动翻页控件的适配器
 * @version v0.1 king 2014-9-24
 */
public class BasicPagerAdapter extends PagerAdapter {
	private List<View> views;
	
	public BasicPagerAdapter(List<View> views) {
		super();
		this.views = views!=null ? views : new ArrayList<View>();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return super.getPageTitle(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public int getCount() {
		return views.size();
	}
	
	/**
	 * 替换所有的视图
	 * @param views
	 */
	public void replaceAll(List<View> views) {
		this.views.clear();
		this.views.addAll(views);
		this.notifyDataSetChanged();
	}
}
