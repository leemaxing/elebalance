package com.liicon.ui.uikit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

/**
 * 通知栏消息管理-工具类
 * @version  v0.1  king  2013.01.08  通知栏消息管理
 * @version  v0.2  king  2014-07-24  增加参数：put()可设置是否能被清除
 * @version  v0.3  king  2014-12-22  增加参数：put()可设置ICON
 */
public class NotificationUtils {
	
	private Context context;
	private NotificationManager notifi;
	
	public NotificationUtils(Context context) {
		this.context = context;
		// NotificationManager负责“发出”与“取消”
		notifi = (NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	
	
	/**
     * 添加消息
	 * @param clazz 目标Activity
	 * @param title 标题
	 * @param txt 内容
	 * @param iconResId 图标
	 * @param autoClear 是否自动清除
	 * @param notifiId 消息标识ID
	 */
	public void putActivity(Class<? extends Activity> clazz, String title, String txt, int iconResId, boolean autoClear, int notifiId) {
	    Intent intent = new Intent(this.context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
	    // 点击通知栏该条消息的处理对象
	    // 参数：Context，发送者的请求码（可以填0），用于系统发送的Intent，标志位
	    PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    put(pendingIntent, title, txt, iconResId, autoClear, notifiId);
	}
	/**
     * 添加消息
     * @param intent 点击消息后，跳转事件
     * @param title 标题
     * @param txt 内容
     * @param iconResId 图标
     * @param autoClear 是否自动清除
     * @param notifiId 消息标识ID
	 */
	public void putActivity(Intent intent, String title, String txt, int iconResId, boolean autoClear, int notifiId) {
		// 点击通知栏该条消息的处理对象
		// 参数：Context，发送者的请求码（可以填0），用于系统发送的Intent，标志位
		PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		put(pendingIntent, title, txt, iconResId, autoClear, notifiId);
	}
	
	/**
     * 添加消息
     * @param intent 点击消息后，跳转事件
     * @param title 标题
     * @param txt 内容
     * @param iconResId 图标
     * @param autoClear 是否自动清除
     * @param notifiId 消息标识ID
	 */
	public void putBroadcast(Intent intent, String title, String txt, int iconResId, boolean autoClear, int notifiId) {
		// 点击通知栏该条消息的处理对象
		// 参数：Context，发送者的请求码（可以填0），用于系统发送的Intent，标志位
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		put(pendingIntent, title, txt, iconResId, autoClear, notifiId);
	}
	
	/**
	 * 添加消息
	 * @param intent 点击消息后，跳转事件
	 * @param title 标题
	 * @param txt 内容
	 * @param autoClear 是否自动清除
	 * @param notifiId 消息标识ID
	 */
	public void putService(Intent intent, String title, String txt, int iconResId, boolean autoClear, int notifiId) {
		// 点击通知栏该条消息的处理对象
		// 参数：Context，发送者的请求码（可以填0），用于系统发送的Intent，标志位
		PendingIntent pendingIntent = PendingIntent.getService(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		put(pendingIntent, title, txt, iconResId, autoClear, notifiId);
	}
	
	/**
	 * 添加消息
	 * @param intent 点击消息后，跳转事件
	 * @param title 标题
	 * @param txt 内容
	 * @param autoClear 是否自动清除
	 * @param notifiId 消息标识ID
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void put(PendingIntent intent, String title, String txt, int iconResId, boolean autoClear, int notifiId) {
		// 用来描述出现在系统通知栏的信息（参数：icon的资源id，在状态栏上展示的滚动信息，时间）
		Notification notification;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			notification = new Notification.Builder(this.context)
					.setTicker(title)
					.setContentTitle(title)
					.setContentText(txt)
					.setSmallIcon(iconResId)
					.setLargeIcon(((BitmapDrawable) this.context.getResources()
								.getDrawable(iconResId)).getBitmap())
					.setContentIntent(intent)
					.setAutoCancel(autoClear)
					.build();
		} else {
			notification = new Notification(iconResId, title+":"+txt, System.currentTimeMillis());
			// 设置显示在通知下拉框中的信息（参数：Context，标题，内容，PendingIntent）
//			notification.setLatestEventInfo(this.context, title, txt, intent);
		}
		
		if (autoClear) {
			// 表示当用户点击 Clear 之后，能够清除该通知
			notification.flags = Notification.FLAG_AUTO_CANCEL;
		} else {
			notification.flags = Notification.FLAG_NO_CLEAR;
		}
		
		// 启动Notification（参数：标识Notification的ID（用来区分同一程序中的不同Notifycation，类型必须为int），要通知的Notification）
		notifi.notify(notifiId, notification);
	}
	
	/**
	 * 删除通知
	 * @param notifiId 消息标识ID
	 */
	public void cancel(int notifiId) {
		notifi.cancel(notifiId);
	}
	
}
