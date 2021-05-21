package com.liicon.utils.app;

import android.content.Context;
import android.location.LocationManager;

/**
 * 获取GSP状态
 * @version  v1.0  lgc  2013-06-05  GSP状态判断
 * @version  v1.1  king  2014-12-24  代码组织
 */
public class GPSUtils {

	private GPSUtils() {
	}
	
	
	
	/**
     * 检测GPS是否开启
     * @param context
     * @return boolean true(GPS开启) false(GPS关闭)
     */
	public static boolean isEnabledGps(Context context) {
        LocationManager locationManager = AppUtils.getSystemService(context, Context.LOCATION_SERVICE);
        boolean status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return status;
    }

    /**
     * 设置GPS是否可用
     * @param context
     * @param enabled true(开启) false(关闭)
     */
    public static void setEnabledGps(Context context, boolean enabled){
        // 方式一
//		ContentResolver resolver = context.getContentResolver();
//		Settings.Secure.setLocationProviderEnabled(resolver, LocationManager.GPS_PROVIDER, enabled);
		
        // 方式二
        try {
            LocationManager locationManager = AppUtils.getSystemService(context, Context.LOCATION_SERVICE);
            locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, enabled);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
        }
	}

}
