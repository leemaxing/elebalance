package com.liicon.utils.app;

import java.util.List;
import java.util.Locale;

import net.duohuo.dhroid.ioc.IocContainer;

import com.liicon.utils.lang.StringUtils;
import com.liicon.utils.log.LLog;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 网络状态处理工具类
 * @version  v0.1  king  2014-11-18  网络状态处理
 */
public class NetworkUtils {
    private NetworkUtils() {
    }

    public static final int NETTYPE_NOT_NETWORK = 0x00;
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_MOBILE = 0x04;
    public static final int NETTYPE_MOBILE_CMWAP = 0x02;
    public static final int NETTYPE_MOBILE_CMNET = 0x03;


	/**
	 * 检测网络是否可用
	 * @param context {@link android.content.Context}
	 * @return true:网络可用，false:不可用
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
	    try {
	        ConnectivityManager cm = AppUtils.getSystemService(context, Context.CONNECTIVITY_SERVICE);
	        if (cm != null) {
	            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	            return networkInfo;
	        }
	    } catch (Exception e) { }
	    return null;
	}
	
	
    /**
     * 检测网络是否已连接
     * @param context {@link android.content.Context}
     * @return true:网络可用，false:不可用
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }
    /**
     * 检测网络是否可用
     * @param context {@link android.content.Context}
     * @return true:网络可用，false:不可用
     */
    public static boolean isAvailable(final Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable();
    }

    
    /**
     * 获取当前网络类型
     * @param context {@link android.content.Context}
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络 4：MOBILE网络（WAP/NET）
     */
    public static int getNetworkType(Context context) {
        int netType = NETTYPE_NOT_NETWORK;
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo == null || !networkInfo.isConnected()) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            netType = NETTYPE_MOBILE;
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
                    netType = NETTYPE_MOBILE_CMNET;
                } else {
                    netType = NETTYPE_MOBILE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 判断当前网络是否是移动数据网络
     * @param context {@link android.content.Context}
     * @return true:移动网络，false:非移动网络或未连接
     */
    public static boolean isConnectMobile(Context context) {
        int networkType = getNetworkType(context);
        return networkType==NETTYPE_MOBILE;
    }
    /**
     * 判断当前网络是否是WIFI
     * @param context {@link android.content.Context}
     * @return true:WIFI，false:非WIFI或未连接
     */
	public static boolean isConnectWifi(final Context context) {
        if (getNetworkType(context) == NETTYPE_WIFI) {
            return true;
        }
        return false;
	}
	
	
	/**
     * 判断WIFI是否打开
     * @param context {@link android.content.Context}
	 * @return true:已打开WIFI，false:未打开WIFI
	 */
	public static boolean isEnabledWifi(final Context context) {
	    try {
	        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	        return manager.isWifiEnabled();
        } catch (Exception e) { }
	    return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
    public static final String WIFI = "Wi-Fi";
    public static final String TWO_OR_THREE_G = "2G/3G";
    public static final String UNKNOWN = "Unknown";

    private static String convertIntToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
                + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }

    /***
     获取当前网络类型
     * 
     * @param pContext
     * @return type[0] WIFI , TWO_OR_THREE_G , UNKNOWN type[0] SubtypeName
     */
    public static String[] getNetworkState(Context pContext) {
        String[] type = new String[2];
        type[0] = "Unknown";
        type[1] = "Unknown";
        // �鿴Ȩ��
        if (pContext.getPackageManager().checkPermission(
                "android.permission.ACCESS_NETWORK_STATE",
                pContext.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            ConnectivityManager localConnectivityManager = (ConnectivityManager) pContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (localConnectivityManager == null)
                return type;

            NetworkInfo localNetworkInfo1 = localConnectivityManager
                    .getNetworkInfo(1);
            if ((localNetworkInfo1 != null)
                    && (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED)) {
                type[0] = "Wi-Fi";
                type[1] = localNetworkInfo1.getSubtypeName();
                return type;
            }
            NetworkInfo localNetworkInfo2 = localConnectivityManager
                    .getNetworkInfo(0);
            if ((localNetworkInfo2 == null)
                    || (localNetworkInfo2.getState() != NetworkInfo.State.CONNECTED))
                type[0] = "2G/3G";
            type[1] = localNetworkInfo2.getSubtypeName();
            return type;
        }
        return type;
    }

    /***
     *获取wifi 地址
     * 
     * @param pContext
     * @return
     */

    public static String getWifiAddress(Context pContext) {
        String address = DEFAULT_WIFI_ADDRESS;
        if (pContext != null) {
            WifiInfo localWifiInfo = ((WifiManager) pContext
                    .getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
            if (localWifiInfo != null) {
                address = localWifiInfo.getMacAddress();
                if (address == null || address.trim().equals(""))
                    address = DEFAULT_WIFI_ADDRESS;
                return address;
            }

        }
        return DEFAULT_WIFI_ADDRESS;
    }

    /***
     *获取wifi ip地址
     * 
     * @param pContext
     * @return
     */
    public static String getWifiIpAddress(Context pContext) {
        WifiInfo localWifiInfo = null;
        if (pContext != null) {
            localWifiInfo = ((WifiManager) pContext.getSystemService(Context.WIFI_SERVICE))
                    .getConnectionInfo();
            if (localWifiInfo != null) {
                String str = convertIntToIp(localWifiInfo.getIpAddress());
                return str;
            }
        }
        return "";
    }

    /**
     * 获取WifiManager
     * 
     * @param pContext
     * @return
     */
    public static WifiManager getWifiManager(Context pContext) {
        return (WifiManager) pContext.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 网络可用
     * android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * 
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            LLog.e(e);
        }
        return false;
    }
    
    
    /***
     *  wifi状态
     * @param pContext
     * @return
     */
    public static boolean isWifi(Context pContext) {
        if ((pContext != null)
                && (getNetworkState(pContext)[0].equals("Wi-Fi"))) {
            return true;
        } else {
            return false;
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    


    /**
     * 设置WIFI是否可用
     * @param context
     * @param enabled
     * @return
     */
    public static void setWifiEnabled(Context context, boolean enabled){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }

    /**
     * 扫描WiFi列表
     * @param context
     * @return
     */
    public static List<ScanResult> getScanResults(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> list = null;
        //开始扫描WiFi
        boolean flag = wifiManager.startScan();
        if(!flag){
            // 打开失败，重试
            getScanResults(context);
        } else {
            // 得到扫描结果
            list = wifiManager.getScanResults();
        }
        return list;
    }

    /**
     * 根据SSID过滤扫描结果
     * @param context
     * @param bssid
     * @return
     */
    public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
        if (StringUtils.isEmpty(bssid)) {
            return null;
        }
        
        ScanResult scanResult = null;
        // 得到扫描结果
        List<ScanResult> list = getScanResults(context);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                // 得到扫描结果
                scanResult = list.get(i);
                if (scanResult.BSSID.equals(bssid)) {
                    break;
                }
            }
        }
        return scanResult;
    }

    /**
     * 获取连接的WIFI信息
     * @param context
     * @return
     */
    public static WifiInfo getConnectionInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }
    
}