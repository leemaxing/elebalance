package com.liicon.xgj.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liicon.xgj.base.AutoPagerAdapter;
import com.liicon.xgj.base.BearListAdapter;

import java.util.List;

/**
 * 菜单列表
 * @author king
 */
public class MenuPagerAdapter extends AutoPagerAdapter {
	private static final float _SCALE_SIZE = 0.8F;
	
	private int itemWidth;
	private int itemHegiht;
	
	public MenuPagerAdapter(Context context, List<ItemAction> datas, int width, int height) {
		super(context, 2, 2, null);
		
		setCenterSpace(width, height);
		super.setAdapter(new BearListAdapter<ItemAction>(context, datas) {
			@Override
			protected View getViewItem(int position, View convertView, final ItemAction current) {
				LinearLayout layout = new LinearLayout(context);
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setGravity(Gravity.BOTTOM);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
				params.gravity = Gravity.CENTER;
				layout.setLayoutParams(params);
				
				
				ImageView logo = new ImageView(context);
				logo.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, current.actionClass);
						context.startActivity(intent);
					}
				});
				LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        itemWidth);
				paramsButton.gravity = Gravity.BOTTOM;
				logo.setLayoutParams(paramsButton);
				logo.setImageResource(current.drawable);
				
				TextView name = new TextView(context);
				name.setText(current.title);
				name.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
				name.setTextColor(Color.parseColor("#000000"));
				name.setTextSize(20);
				LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
				paramsName.gravity = Gravity.TOP;
				paramsName.topMargin = 20;
				name.setLayoutParams(paramsName);
				
				layout.addView(logo);
				layout.addView(name);
				return layout;
			}
		});
	}
	
	/**
	 * 改变Item间距
	 */
	public void setCenterSpace(int width, int height) {
		final int groupWidth = width / 2;
		final int groupHeight = height / 2;

		this.itemWidth = (int) (groupWidth * _SCALE_SIZE);
		this.itemHegiht = (int) (groupHeight * _SCALE_SIZE);
		if (this.itemWidth > this.itemHegiht * _SCALE_SIZE) {
			this.itemWidth = (int) (this.itemHegiht * _SCALE_SIZE);
		}
		
		int space = (int) ((groupWidth - this.itemWidth) * _SCALE_SIZE);
		if (space < 0) {
			space = 2;
		}
		
		int paddingHorizontal = (int) (groupWidth - this.itemWidth - space);
		int paddingVertical = (int) (groupHeight - this.itemHegiht - space) + (groupHeight - groupWidth);
		if (paddingHorizontal < 0) {
			paddingHorizontal = space / 2;
		}
		if (paddingVertical < 0) {
			paddingVertical = space / 2;
		}

		setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
	}
	
	
	
	/**
	 * Item项的数据、意图
	 */
	public static class ItemAction {
		private String title;
		private Class<? extends Activity> actionClass;
		private int drawable;
		
		public ItemAction(String title, Class<? extends Activity> actionClass, int drawable) {
			this.title = title;
			this.actionClass = actionClass;
			this.drawable = drawable;
		}
		public ItemAction(Context context, int titleId, Class<? extends Activity> actionClass, int drawable) {
			this.title = context.getString(titleId);
			this.actionClass = actionClass;
			this.drawable = drawable;
		}
	}
}
