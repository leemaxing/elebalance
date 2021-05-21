package com.liicon.xgj.base;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 包含指示器的ViewPager
 * @author King
 * @version v1.0
 * @created 2013-12-24
 */
public class IndicatorViewPager extends RelativeLayout {
	private ViewPager mPager;
	private IndicatorView mIndicatorView;
	/** ViewPager监听事件 */
    private ViewPager.OnPageChangeListener onPageChangeListener = null;

    public IndicatorViewPager(Context context) {
        super(context);
        init();
    }
    public IndicatorViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }
    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
	
    /**
     * 初始化
     */
    private void init() {
    	int pagerId = Math.abs(getClass().getSimpleName().hashCode());

    	mIndicatorView = new IndicatorView(getContext());
    	mIndicatorView.setId(pagerId);
    	LayoutParams paramIndicator = new LayoutParams(mIndicatorView.getIndicatorWidth(), mIndicatorView.getIndicatorHeight());
    	paramIndicator.addRule(CENTER_HORIZONTAL, TRUE);
    	paramIndicator.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    	this.addView(mIndicatorView, paramIndicator);
    	
    	mPager = new ViewPager(getContext());
    	mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (onPageChangeListener != null)
					onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}

			@Override
			public void onPageSelected(int position) {
// 设置为当前页码
				mIndicatorView.setCurrentPager(position);

				if (onPageChangeListener != null)
					onPageChangeListener.onPageSelected(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (onPageChangeListener != null)
					onPageChangeListener.onPageScrollStateChanged(state);
			}
		});

    	LayoutParams paramPager = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    	paramPager.addRule(ABOVE, pagerId);
    	this.addView(mPager, paramPager);
    }
	

    /**
     * 设置总页数
     * @param pageCount
     */
	private void setTotalPage(int pageCount) {
		mIndicatorView.setTotalPage(pageCount);
		mIndicatorView.setCurrentPager(0);
	}
	
	public void setAdapter(final PagerAdapter adapter) {
		mPager.setAdapter(adapter);
		setTotalPage(adapter.getCount());
		
		// 观察者模式：观察数据的变化
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				setTotalPage(adapter.getCount());
			}
		});
	}
	public PagerAdapter getAdapter() {
		return mPager.getAdapter();
	}
	
	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		this.onPageChangeListener = listener;
	}
	
	
	
	public ViewPager getViewPager() {
		return mPager;
	}
	public IndicatorView getIndicatorView() {
		return mIndicatorView;
	}

	/**
	 * 设置默认颜色
	 * @param color
	 */
	public void setColorDefult(int color) {
		mIndicatorView.setColorDefult(color);
	}
	/**
	 * 设置选中项的颜色
	 * @param color
	 */
	public void setColorFocus(int color) {
		mIndicatorView.setColorFocus(color);
	}
	
	/**
	 * 获取当前选中�?
	 * @return
	 */
	public int getCurrentPager() {
		return mPager.getCurrentItem();
	}

	/**
	 * 设置当前选中项
	 * @param index
	 */
	public void setCurrentPager(int index) {
		mPager.setCurrentItem(index);
	}

}
