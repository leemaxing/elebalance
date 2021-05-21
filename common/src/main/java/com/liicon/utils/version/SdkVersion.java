package com.liicon.utils.version;

/**
 * Android SDK 版本鉴别
 * 
 * @version v0.1 king 2014-11-6 版本号常量，判断是否支持某个版本API
 */
public class SdkVersion extends SdkVersionCodes {
	
	/**
	 * 是否支持指定版本
	 * @see com.liicon.utils.version.SdkVersion.SdkVersionCodes
	 * @param version
	 * @return
	 */
	public static boolean isSupport(int version) {
		if (android.os.Build.VERSION.SDK_INT >= version) {
			return true;
		}
		return false;
	}

}
