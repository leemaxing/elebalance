package com.liicon.application;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.EditText;

import com.liicon.config.BasicConfig;
import com.liicon.config.ConfigKeys;
import com.liicon.manager.IActivityManager;
import com.liicon.manager.PropertiesManager;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.app.AppUtils.AppInfo;
import com.liicon.utils.encrypt.SignInfo;
import com.liicon.utils.encrypt.SignInfoUtils;
import com.liicon.utils.lang.StringUtils;
import com.liicon.utils.log.LLog;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author King
 * @version 1.0
 * @created 2013.09.02
 * @change
 *   2013.10.25 加入LocHelp
 *   2013.11.20 加入缓存自动清理机制（设置缓存有效期）
 *   2014.09.09 提供自定义异常处理，增加当前实例的获取方法
 */
public class BasicAppHelper extends VolleyAppHelper {
    
    private static final String FINAL_KEY_APP_UNIQUEID = "FINAL_KEY_APP_UNIQUEID";
    private static final String FINAL_KEY_COOKIE = "FINAL_KEY_COOKIE";
	
	private final Map<String, Object> memCacheRegion = new Hashtable<String, Object>();
	private IActivityManager mAppManager;
	
	private String appUserAgent;
	
	protected static BasicAppHelper helper;

    protected BasicAppHelper() {
    }
    
    
    /**
     * 初始化（仅需初始化一次，一般在Application调用）
     * @param context
     */
    public static synchronized void init(Context context) {
        helper = new BasicAppHelper();
        helper.onCreate(context);
    }
    /**
     * 获取当前实例
     * @return
     */
    public static synchronized BasicAppHelper get() {
        synchronized (helper) {
            return helper;
        }
    }
    
	/**
	 * 获取应用程序Activity管理器
	 * @return 应用程序Activity管理器{@link com.liicon.manager.IActivityManager}
	 */
    public synchronized IActivityManager getActivityManager() {
        if (mAppManager == null) {
            mAppManager = new DefaultActivityManager();
        }
        return mAppManager;
    }
    
    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
		// 注册App异常崩溃处理器
		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getExceptionHandler());
	}
	
	/**
	 * 退出APP应用程序
	 */
	public void exit() {
	    getActivityManager().AppExit(getApplication());
	}
	
	/**
	 * APP崩溃异常处理:用于收集错误信息&发送错误报告
	 * @param activity
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	protected boolean handleException(final Activity activity, Throwable ex) {
		// 获取APP崩溃异常报告
		final String crashReport = ExceptionHandler.getCrashReport(ex);
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				sendAppCrashReport(activity, crashReport);
				Looper.loop();
			}
		}.start();
		return true;
	}

    /**
     * 发送App异常崩溃报告
     * @param context
     * @param crashReport
     */
    public void sendAppCrashReport(final Context context, final String crashReport) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_TITLE));
            builder.setMessage(BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_DESCRIPTION));
            builder.setPositiveButton(BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_SUBMIT),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // 发送异常报告
                            Intent i = new Intent(Intent.ACTION_SEND);
                            // i.setType("text/plain"); //模拟器
                            i.setType("message/rfc822"); // 真机
                            i.putExtra(Intent.EXTRA_EMAIL,
                                    new String[] {BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_EMAIL)});
                            i.putExtra(Intent.EXTRA_SUBJECT,
                                    BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_SUBJECT));
                            i.putExtra(Intent.EXTRA_TEXT, crashReport);
                            context.startActivity(Intent.createChooser(i, "发送错误报告"));
                            // 退出
                            getActivityManager().AppExit(context);
                        }
                    });
            builder.setNegativeButton(BasicConfig.getString(ConfigKeys.CONF_ERROR_APP_REPORT_CANCEL),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // 退出
                            getActivityManager().AppExit(context);
                        }
                    });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // 退出
                    getActivityManager().AppExit(context);
                }
            });
            builder.setCancelable(true);
            builder.show();
        } catch (RuntimeException e) {
            LLog.e(e);
        } catch (Throwable e) {
            LLog.e(e);
        }
    }

	/**
	 * APP退出前执行
	 */
	protected void handleAppExit() {
	}
	
	/**
	 * 用户代理
	 */
	protected String getUserAgent() {
		if(TextUtils.isEmpty(appUserAgent)) {
		    appUserAgent = BasicConfig.getString(ConfigKeys.CONF_USER_AGENT);
		    if(TextUtils.isEmpty(appUserAgent)) {
                AppInfo appInfo = this.getAppInfo();
                
	            StringBuilder ua = new StringBuilder(appInfo.packageName);
	            ua.append('/'+appInfo.versionName+'_'+appInfo.versionCode);//App版本
	            ua.append("/Android");//手机系统平台
	            ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
	            ua.append("/"+android.os.Build.MODEL); //手机型号
	            ua.append("/"+getAppId());//客户端唯一标识
	            appUserAgent = ua.toString();
		    }
		}
		return appUserAgent;
	}
	
	
	/**
	 * 检测当前系统声音是否为正常模式
	 * @return
	 */
	public boolean isAudioNormal() {
		AudioManager mAudioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}
	
	/**
	 * 获取App唯一标识（一般用作手机识别码）
	 * @return
	 */
	public String getAppId() {
		String uniqueID = PropertiesManager.getString(getApplication(), FINAL_KEY_APP_UNIQUEID);
		if (StringUtils.isEmpty(uniqueID)) {
			uniqueID = UUID.randomUUID().toString();
			PropertiesManager.set(getApplication(), FINAL_KEY_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}

	/**
	 * 获取APP签名信息
	 * @return
	 */
	public SignInfo getAppSignInfo() {
		return SignInfoUtils.getSignInfo(getApplication(), getPackageName());
	}
	/**
	 * 获取APP签名摘要(MD5加密)
	 * @return
	 */
	public String getAppSignDigest() {
		return SignInfoUtils.getSignDigest(getApplication(), getPackageName());
	}
	/**
	 * 检查签名的有效性（根据MD5摘要）
	 * @param digest 签名的MD5摘要
	 * @return ture:有效
	 */
	public boolean checkSignByDigest(String digest) {
		return SignInfoUtils.checkSignByDigest(getApplication(), getPackageName(), digest);
	}
	/**
	 * 检查签名的有效性（根据公钥）
	 * @param publicKey 公钥
	 * @return ture:有效
	 */
	public boolean checkSignByPublicKey(String publicKey) {
		return SignInfoUtils.checkSignByPublicKey(getApplication(), getPackageName(), publicKey);
	}

	/**
	 * 清除保存的缓存
	 */
	public void cleanCookie() {
	    PropertiesManager.remove(getApplication(), FINAL_KEY_COOKIE);
	}
    public static String getCookie(Context ctx) {
        return PropertiesManager.getString(ctx, FINAL_KEY_COOKIE);
    }

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() {
		// 清除webview缓存
	    getApplication().deleteDatabase("webview.db");
	    getApplication().deleteDatabase("webview.db-shm");
	    getApplication().deleteDatabase("webview.db-wal");
	    getApplication().deleteDatabase("webviewCache.db");
	    getApplication().deleteDatabase("webviewCache.db-shm");
	    getApplication().deleteDatabase("webviewCache.db-wal");
		// 清除数据缓存
		clearCacheFolder(getApplication().getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(getApplication().getCacheDir(), System.currentTimeMillis());
		// 清除编辑器保存的临时内容
		Properties props = PropertiesManager.loadProperties(getApplication());
		for (Object key : props.keySet()) {
			String _key = key.toString();
			if (_key.startsWith("temp"))
			    PropertiesManager.remove(getApplication(), _key);
		}
	}

	/**
	 * 清除缓存目录
	 * @param dir 目录
	 * @param numDays 当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * 将对象保存到内存缓存中
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		if (key == null) {
			return;
		}
		
		if (value == null) {
			memCacheRegion.remove(key);
		} else {
			memCacheRegion.put(key, value);
		}
	}

	/**
	 * 从内存缓存中获取对象
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T>T getMemCache(String key) {
		return (T) memCacheRegion.get(key);
	}
    
	/**
	 * 判断编辑框输入内容，是否为空【""、" "[半角]、"　"[全角]】
	 * @param editTexts 编辑框（EditText）
	 * @param errors 为空是提示信息（与编辑框相对应，即数组length一致）
	 * @return 若有值为空，返回true；否则，false
	 */
	public boolean isEmpty(EditText[] editTexts, String[] errors) {
		for (int i = 0; i < editTexts.length; i++) {
			EditText editText = editTexts[i];
			String val = StringUtils.trim(editText.getText().toString());
			if (TextUtils.isEmpty(val)) {
				ToastUtils.show(getApplication(), errors[i]);
				editText.requestFocus();
				return true;
			}
		}
		return false;
	}
	
}
