package com.liicon.application;

import java.util.Stack;

import com.liicon.manager.IActivityManager;
import com.liicon.utils.log.LLog;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author King
 * @version 1.0
 * @created 2013.09.02
 */
class DefaultActivityManager implements IActivityManager {
	
	private Stack<Activity> activityStack = new Stack<Activity>();
	
	DefaultActivityManager(){}
	
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		activityStack.add(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
	    if (activityStack==null || activityStack.isEmpty()) {
	        return null;
	    }
	    try {
	        return activityStack.lastElement();
        } catch (Exception e) {
            return null;
        }
	}
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
	    try {
	        Activity activity = currentActivity();
	        finishActivity(activity);
        } catch (Exception e) {
            LLog.e(e);
        }
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		activityStack.remove(activity);
		try {
		    activity.finish();
        } catch (Exception e) { }
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
			Activity activity = activityStack.get(i);
            if (null != activity){
            	activity.finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			// 通知程序，退出应用
		    BasicAppHelper.get().handleAppExit();
		} catch (Exception e) { }
		
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			activityMgr.restartPackage(context.getPackageName());
		} catch (Exception e) {	}
		
		try {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) { }
	}
}