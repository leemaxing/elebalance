package com.liicon.xgj.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 绘制指示器的View
 * @author King
 * @created v1.0 2013-12-24
 * @change v1.1 2014-06-09 增加指示器类型选择
 */
public class IndicatorView extends View {
	/** 当前页数 */
	private int mCurrentPager = -1;
	/** 总页页数 */
	private int mTotalPager = 0;
	/** 默认颜色 */
	private int mColorDefult = Color.LTGRAY;
	/** 选中项颜色 */
	private int mColorFocus = Color.RED;
	/** Item总宽度 */
	private int mItemWidth = ViewGroup.LayoutParams.MATCH_PARENT;
	/** Item总高度 */
	private int mItemHeight = 10;
	/** Item竖向间距 */
	private int mSpaceVertical = 0;
	/** Item横向间距 */
	private int mSpaceHorizontal = 0;
	
	public static final int TypeLine = 1;
	public static final int TypePointAuto = 2;
	public static final int TypePointCenter = 3;
	public static final int TypePointTop = 4;
	/** 指示器类型（横线、圆点） */
	private int mDrawType = TypeLine;
	
	
	public IndicatorView(Context context) {
		super(context);
	}
	public IndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	/**
	 * 设置指示器的类型
	 * @param drawType
	 */
	public void setDrawType(int drawType) {
		this.mDrawType = drawType;
	}
	/**
	 * Item的高度
	 * @return
	 */
	public int getIndicatorHeight() {
		return mItemHeight;
	}
	/**
	 * 设置Item的高度
	 * @param height
	 */
	public void setIndicatorHeight(int height) {
		this.mItemHeight = height;
		if (this.getLayoutParams() != null)
			this.getLayoutParams().height = height;
		this.postInvalidate();
	}

	/**
	 * Item的宽度
	 * @return
	 */
	public int getIndicatorWidth() {
		return mItemWidth;
	}
	/**
	 * 设置Item的宽度
	 * @param width
	 */
	public void setIndicatorWidth(int width) {
		this.mItemWidth = width;
		if (this.getLayoutParams() != null)
			this.getLayoutParams().width = width;
	}
	
	/**
	 * 设置Item的竖向间距
	 * @param space
	 */
	public void setIndicatorSpaceVertical(int space) {
		this.mSpaceVertical = space;
	}
	
	/**
	 * 设置Item的横向间距
	 * @param space
	 */
	public void setIndicatorSpaceHorizontal(int space) {
		this.mSpaceHorizontal = space;
	}
	
	
	/**
	 * 设置总页数
	 * @param pageCount 总页数
	 */
	public void setTotalPage(int pageCount) {
		mTotalPager = pageCount;
		if (mCurrentPager >= mTotalPager)
			mCurrentPager = mTotalPager - 1;
	}
	/**
	 * 设置默认颜色
	 * @param color
	 */
	public void setColorDefult(int color) {
		this.mColorDefult = color;
	}
	/**
	 * 设置选中项的颜色
	 * @param color
	 */
	public void setColorFocus(int color) {
		this.mColorFocus = color;
	}
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getCurrentPager() {
		return mCurrentPager;
	}
	/**
	 * 设置当前页码
	 * @param index
	 */
	public void setCurrentPager(int index) {
		if (index != -1 && (index < 0 || index >= mTotalPager))
			return;

		if (mCurrentPager != index) {
			mCurrentPager = index;
			this.invalidate();
		}
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (mTotalPager > 0) {
			if (mDrawType == TypeLine) {
				// 画线
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
						| Paint.FILTER_BITMAP_FLAG);
				paint.setStyle(Paint.Style.FILL);
		
				Rect rect = new Rect();
				this.getDrawingRect(rect);
				
				final float width = rect.width() / mTotalPager;
				
				float gap = 0;
				for (int i = 0; i < mTotalPager; i++) {
					if (i == mCurrentPager) {
						gap += rect.width() % mTotalPager;
						paint.setColor(mColorFocus);
					} else {
						paint.setColor(mColorDefult);
					}
					canvas.drawRect(i*width + mSpaceHorizontal/2, rect.top + mSpaceVertical/2, (i+1)*width + gap - mSpaceHorizontal/2, rect.bottom - mSpaceVertical/2, paint);
				}
			} else if (mDrawType == TypePointAuto) {
				// 画点
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
						| Paint.FILTER_BITMAP_FLAG);
				paint.setStyle(Paint.Style.FILL);
		
				Rect rect = new Rect();
				this.getDrawingRect(rect);

				// 绘制区域
				final float drawHeight = mItemHeight - (mSpaceVertical * 2);
				final float itemWidth = rect.width() / mTotalPager;
				final float radius = drawHeight / 2;

				float drawX = rect.left - itemWidth / 2;
				final float drawY = rect.top + mSpaceVertical + radius;
				for (int i = 0; i < mTotalPager; i++) {
					if (i == mCurrentPager) {
						paint.setColor(mColorFocus);
					} else {
						paint.setColor(mColorDefult);
					}
					
					// 画点
					drawX += itemWidth;
					
					canvas.drawCircle(drawX, drawY, radius, paint);
				}
			} else if (mDrawType == TypePointCenter) {
				// 画点
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
						| Paint.FILTER_BITMAP_FLAG);
				paint.setStyle(Paint.Style.FILL);
				
				Rect rect = new Rect();
				this.getDrawingRect(rect);
				
				// 绘制区域
				final float drawHeight = mItemHeight - (mSpaceVertical * 2);
				final float drawWidth = (drawHeight + (mSpaceHorizontal * 2)) * mTotalPager;
				final float itemWidth = drawHeight + (mSpaceHorizontal * 2);
				final float radius = drawHeight / 2;

				float drawX = rect.left + (rect.width() - drawWidth) / 2 + (radius + mSpaceHorizontal) - itemWidth;
				final float drawY = rect.top + mSpaceVertical + radius;
				for (int i = 0; i < mTotalPager; i++) {
					if (i == mCurrentPager) {
						paint.setColor(mColorFocus);
					} else {
						paint.setColor(mColorDefult);
					}
					
					drawX += itemWidth;
					
					canvas.drawCircle(drawX, drawY, radius, paint);
				}
			} else if (mDrawType == TypePointTop) {
				// 画点
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
						| Paint.FILTER_BITMAP_FLAG);
				paint.setStyle(Paint.Style.FILL);
				
				Rect rect = new Rect();
				this.getDrawingRect(rect);
				
				// 绘制区域
				final float drawHeight = mItemHeight - (mSpaceVertical * 2);
				final float drawWidth = (drawHeight + (mSpaceHorizontal * 2)) * mTotalPager;
				final float itemWidth = drawHeight + (mSpaceHorizontal * 2);
				final float radius = drawHeight / 2;
				
				float drawX = rect.left + (rect.width() - drawWidth) / 2 + (radius + mSpaceHorizontal) - itemWidth;
				final float drawY = rect.top + radius;
				for (int i = 0; i < mTotalPager; i++) {
					if (i == mCurrentPager) {
						paint.setColor(mColorFocus);
					} else {
						paint.setColor(mColorDefult);
					}
					
					drawX += itemWidth;
					
					canvas.drawCircle(drawX, drawY, radius, paint);
				}
			}
		}
	}

}
