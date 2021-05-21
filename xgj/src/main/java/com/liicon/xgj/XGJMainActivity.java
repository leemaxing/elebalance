package com.liicon.xgj;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.liicon.xgj.adapter.MenuPagerAdapter;
import com.liicon.xgj.adapter.MenuPagerAdapter.ItemAction;
import com.liicon.xgj.base.BaseActivity;
import com.liicon.xgj.base.IndicatorView;
import com.liicon.xgj.base.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * @author king
 */
public class XGJMainActivity extends BaseActivity {
	private IndicatorViewPager mListMenu;
	/** 是否检测过新版本 */
	private boolean isCheckUpdate = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_xgj);
		 
		isCheckUpdate = false;

		final ImageView ivLogo = (ImageView) findViewById(R.id.static_id_head_finish);
		if (ivLogo != null) {
			ivLogo.setImageResource(R.drawable.ic_logo);
		}
		
		mListMenu = (IndicatorViewPager) findViewById(R.id.main_viewpager_menu);
		IndicatorView indicatorView = mListMenu.getIndicatorView();
		indicatorView.setDrawType(IndicatorView.TypePointCenter);
		indicatorView.setColorFocus(Color.parseColor("#999999"));
		
		final List<ItemAction> datas = new ArrayList<ItemAction>();
		datas.add(new ItemAction(this, R.string.title_activity_xian_su_qi, XianSuQiActivity.class, R.drawable.ic_menu_tool));
		datas.add(new ItemAction(this, R.string.title_activity_limit_switch, LimitSwitchActivity.class, R.drawable.ic_menu_switch));
		datas.add(new ItemAction(this, R.string.title_activity_up, UpActivity.class, R.drawable.ic_menu_up));
		datas.add(new ItemAction(this, R.string.title_activity_down, DownActivity.class, R.drawable.ic_menu_down));
		//datas.add(new ItemAction(this, R.string.title_activity_up_and_down_zoom, UpAndDownZoomActivity.class));
		datas.add(new ItemAction(this, R.string.title_activity_balance, BalanceActivity.class, R.drawable.ic_menu_balance));
		datas.add(new ItemAction(this, R.string.title_activity_about, AboutActivity.class, R.drawable.ic_menu_about));

        mListMenu.getViewPager().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mListMenu.getViewPager().getViewTreeObserver().removeOnPreDrawListener(this);
                int widthListMenu = mListMenu.getWidth();
                int heightListMenu = mListMenu.getHeight();

                // 指示器
                mListMenu = (IndicatorViewPager) findViewById(R.id.main_viewpager_menu);
                IndicatorView indicatorView = mListMenu.getIndicatorView();

                // 指示器大小
                final int pointHeight = 20;
                int indicatorHeight = (int) (heightListMenu - widthListMenu - pointHeight*2);
                if (indicatorHeight < pointHeight) {
                    indicatorHeight = pointHeight;
                }

                indicatorView.setIndicatorSpaceVertical((indicatorHeight - pointHeight) / 2);
                indicatorView.setIndicatorSpaceHorizontal(16);
                indicatorView.setIndicatorHeight(indicatorHeight);

                // 翻页控件
                int widthViewPager = widthListMenu;
                int heightViewPager = heightListMenu - indicatorHeight;

                MenuPagerAdapter adapter = new MenuPagerAdapter(XGJMainActivity.this, datas, widthViewPager, heightViewPager);
                mListMenu.setAdapter(adapter);
                adapter.setCenterSpace(widthViewPager, heightViewPager);
                return false;
            }
        });
	}

}
