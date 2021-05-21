package com.liicon.utils.app;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.liicon.utils.log.LLog;

/**
 * 输入法管理工具类
 * @version  v0.1  king  2013.01.22  输入法弹出隐藏管理
 */
public class IMEUtills {
	
	private Activity mActivity;
	
	public IMEUtills(Activity activity) {
		this.mActivity = activity;
	}
	

	/**
	 * 切换软键盘状态（隐藏/显示）
	 */
	public void toggle() {
		getManager().toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);  
	}
	
	/**
	 * 显示选择输入法菜单
	 */
	public void showPicker() {
		getManager().showInputMethodPicker();
	}
	
	/**
	 * 隐藏输入法
	 */
	public boolean hide() {
		try {
			View view = mActivity.getCurrentFocus();
			if (view != null) {
				getManager().hideSoftInputFromWindow(view.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
			return true;
		} catch (Exception e) {
			LLog.e(e);
		}
		return false;
	}
	
	/**
	 * 获取输入法管理工具
	 * @return android.view.inputmethod.InputMethodManager
	 * @see android.view.inputmethod.InputMethodManager 参考InputMethodManager
	 */
	public InputMethodManager getManager() {
		return ((InputMethodManager)mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE));
	}
	
}
