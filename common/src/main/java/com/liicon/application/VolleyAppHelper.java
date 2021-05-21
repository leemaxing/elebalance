package com.liicon.application;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.liicon.config.BasicConfig;
import com.liicon.config.ConfigKeys;
import com.liicon.manager.FileManager;
import com.liicon.uil.ImageManager;
import com.liicon.utils.app.AppUtils;
import com.liicon.utils.app.AppUtils.AppInfo;
import com.liicon.volley.request.ParserRequest;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 封装对Volley进行控制的Application
 * @author King
 * @version v1.0
 * @created 2014-03-07
 */
abstract class VolleyAppHelper {
	
	/** 用于拦截所有的请求队列 */
	private RequestQueue mRequestQueue;
    private Application mApplication = null;
	
	protected void onCreate(Context context) {
	    if (mApplication==null || isMyAppThread()) {
	        mApplication = convertApplication(context);
	        
	        if (mApplication == null) {
	            throw new IllegalArgumentException("'context' Cannot getter android.app.Application.");
	        }

	        // 初始化图片加载管理器
	        ImageManager.init(mApplication,
	                BasicConfig.getInt(ConfigKeys.CONF_HTTP_IMAGE_LOADDING_RESID),
	                BasicConfig.getInt(ConfigKeys.CONF_HTTP_IMAGE_ERROR_RESID));
        }
	}
	
    /**
     * 从context中找到Application
     * @param context
     * @return
     */
    private Application convertApplication(Context context) {
        Application application = null;
        if (context != null) {
            if (context instanceof Activity) {
                application = ((Activity) context).getApplication();
            } else if (context instanceof Service) {
                application = ((Service) context).getApplication();
            } else if (context instanceof Application) {
                application = (Application) context;
            }
        }
        if (application == null) {
            application = mApplication;
        }
        return application;
    }

    public Application getApplication() {
        return mApplication;
    }
    
    public String getPackageName() {
        return mApplication.getPackageName();
    }
    /**
     * 获取App安装包信息
     * @return
     */
    public AppInfo getAppInfo() {
        return AppUtils.getCurrentAppInfo(mApplication);
    }
    

    /**
     * 是自己的应用程序所在的线程
     * @return
     */
    public boolean isMyAppThread() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果使用到百度地图或者类似启动remote service的第三方库，这个if判断不能少
        if (TextUtils.isEmpty(processAppName) || !processAppName.equals(getPackageName())) {
            // workaround for baidu location sdk
            // 百度定位服务运行在一个单独的进程，每次定位服务启动的时候，都会调用application::onCreate创建新的进程
            // 我们只需要初始化一次
            // 这个特殊处理是，如果从pid 找不到对应的processInfo.processName，
            // 则此application::onCreate 是被service 调用的，直接返回
            return false;
        }
        return true;
    }

    /**
     * 根据进程ID，获取App名字
     * @param pID
     * @return
     */
    public String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
//      PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = i.next();
            try {
                if (info.pid == pID) {
//                  CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    

	/**
	 * 获取RequestQueue，如果是null则创建
	 */
	protected RequestQueue getRequestQueue() {
		// 懒惰初始化,它是第一次访问时，将创建实例
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mApplication,new File(FileManager.getCacheDirByUsable(mApplication.getPackageName(), "http")));
		}
		return mRequestQueue;
	}

	/**
	 * 添加指定的请求到RequestQueue,可以指定标签，否则使用默认标签
	 */
	public void load(String tag, Request<?> request) {
		if (TextUtils.isEmpty(tag) && request instanceof ParserRequest) {
			tag = ((ParserRequest<?>)request).getParser().getClass().getName();
		}
		
		// 如果标签是空的，设置为默认标签
		request.setTag(TextUtils.isEmpty(tag) ? mApplication.getPackageName() : tag);
		// 设置超时时间、最大请求次数、退避指数
		request.setRetryPolicy(new DefaultRetryPolicy(BasicConfig.getInt(ConfigKeys.CONF_HTTP_TIMEOUT),
		        BasicConfig.getInt(ConfigKeys.CONF_HTTP_MAX_RETRIES),
		        BasicConfig.getFloat(ConfigKeys.CONF_HTTP_BACKOFF_MULTIPLIER)));
		getRequestQueue().add(request);
	}
	/**
	 * 添加指定的请求到RequestQueue,可以指定标签，否则使用默认标签【不能缓存】
	 */
	public void loadNoCache(String tag, Request<?> request) {
		request.setShouldCache(false);
		load(tag, request);
	}
	
	
	/**
	 * 添加指定的请求到RequestQueue,使用默认标签
	 */
	public <T> void load(Request<T> request) {
		load(null, request);
	}
	/**
	 * 添加指定的请求到RequestQueue,使用默认标签【不能缓存】
	 */
	public <T> void loadNoCache(Request<T> request) {
		loadNoCache(null, request);
	}
	

	/**
	 * 加载图片
	 */
	public void loadImage(View iv, String url) {
	    ImageManager.load(iv, url);
	}
	/**
	 * 加载图片
	 */
	public void loadImage(ImageView iv, String url, ImageLoadingListener listener) {
        ImageManager.load(iv, url, listener);
	}

	/**
	 * 根据标签名，取消请求
	 */
	public void cancelRequestByTag(String tag) {
		getRequestQueue().cancelAll(tag);
	}
	/**
	 * 根据实体类类型，取消请求
	 */
	public void cancelRequestByClass(Class<?> clazz) {
		getRequestQueue().cancelAll(clazz.getName());
	}
	
}
