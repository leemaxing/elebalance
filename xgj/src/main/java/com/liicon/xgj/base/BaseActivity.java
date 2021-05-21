package com.liicon.xgj.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.liicon.ui.activity.AbsQuickActionBarActivity;
import com.liicon.xgj.R;

/**
 * Activity基类
 * @author king
 */
public class BaseActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setNavigationBar(){
		Window window = getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.setStatusBarColor(Color.rgb(25,137,250));
		}
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null){
			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle("");
		TextView textView = findViewById(R.id.toolbar_title);
		if (textView != null){
			textView.setText(title);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
	    case android.R.id.home:
	    	finish();
	    	break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
