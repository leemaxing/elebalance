package com.liicon.manager;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.liicon.utils.version.SdkVersion;
import com.liicon.utils.version.SdkVersionCodes;

/**
 * 缓存管理工具类
 * @version  v0.1.1  king  2014-11-10  优化调用方法
 * @version  v0.1.0  king  2013-11-20  缓存管理目录，存储用户配置信息
 */
public final class FileManager {
    
    public final static String separator = File.separator;
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public final static String DIR_CACHE = "app_data";
    
    
    private FileManager(){
    }
    
    
    /**
     * 获取外置SD卡缓存目录路径
     * <p><b>
     * ！注意：若不存在，则返回null
     * </b></p>
     * @param packageName 包名
     * @return 返回:/storage/sdcard0/Android/data/you.packageName/
     */
    public static String getCacheDirBySDCard(String packageName) {
//      /mnt/sdcard/Android/data/com.liicon.simpledemo/cache
//      if (context != null) {
//          return context.getExternalCacheDir().getPath();
//      }
        if (!existSDCard()) {
            return null;
        }
        
        File path = Environment.getExternalStorageDirectory();
        if (path == null) {
            return null;
        }
        
        return path.getAbsolutePath() + separator + "Android" + separator + "data" + separator
                + packageName + separator + DIR_CACHE + separator;
    }
    /**
     * 获取内置SD卡缓存目录路径
     * <p><b>
     * ！注意：若不存在，则返回null
     * </b></p>
     * @param packageName 包名
     * @return 返回:/storage/sdcard1/Android/data/you.packageName/
     */
    public static String getCacheDirBySDCardInternal(String packageName) {
        String sdcardInternal = getSDCardInternalDir();
        if (TextUtils.isEmpty(sdcardInternal)) {
            return null;
        }
        
        // 获取缓存目录
        return sdcardInternal + "Android" + separator + "data" + separator
                + packageName + separator + DIR_CACHE + separator;
    }
    /**
     * 获取内置SD卡根目录
     * <p><b>
     * ！注意：若不存在，则返回null
     * </b></p>
     * @return 返回:/storage/sdcard1/
     */
    protected static String getSDCardInternalDir() {
        if (!existSDCard()) {
            return null;
        }
        
        // 获取SDCard目录
        File sdcardFile = Environment.getExternalStorageDirectory();
        if (sdcardFile != null && sdcardFile.exists()) {
            // 获取SDCard父级目录
            File sdrootFile = sdcardFile.getParentFile();
            if (sdrootFile != null && sdrootFile.exists() && sdrootFile.isDirectory()) {
                // 获取SDCard同级目录
                File[] files = sdrootFile.listFiles();
                if (files != null && files.length == 2) {
                    // 找到外置SDCard目录
                    File internalFile = files[0];
                    if (internalFile.equals(sdcardFile)) {
                        internalFile = files[1];
                    }
                    
                    // 判断文件夹是否存在且可用
                    if (internalFile != null && internalFile.exists()
                            && internalFile.isDirectory()) {
                        // 获取缓存目录
                        return internalFile.getAbsolutePath() + separator;
                    }
                }
            }
        }
        return null;
    }
    /**
     * 获取手机缓存目录路径
     * @param packageName 包名
     * @return 返回:/data/you.packageName/
     */
    public static String getCacheDirByData(String packageName) {
//      /data/data/com.liicon.simpledemo/cache
//      if (context != null) {
//          return context.getCacheDir().getPath();
//      }
        
        String dataDir = "/data";
        
        File path = Environment.getDataDirectory();
        if (path != null) {
            dataDir = path.getAbsolutePath();
        }
        return dataDir + separator + "data"  + separator
                + packageName + separator + DIR_CACHE + separator;
    }
    

    /**
     * 获取合适的缓存目录路径
     * <p>
     * 优先考虑SDCard，其次内置SDCard，最后APP数据存储目录
     * </p>
     * @param packageName 包名
     * @return
     */
    public static String getCacheDirByUsable(String packageName) {
        // 获取合适的存储路径
        String path = null;
        if (canUseSDCard()) {
            // 外置SDCard
            path = getCacheDirBySDCard(packageName);
        }
        if (TextUtils.isEmpty(path) && canUseSDCardInternal()) {
            // 内置SDCard
            path = getCacheDirBySDCardInternal(packageName);
        }
        if (TextUtils.isEmpty(path)) {
            // data/data
            path = getCacheDirByData(packageName);
        }
        
        File pathFile = new File(path);
        pathFile.mkdirs();
        return path;
    }
    /**
     * 获取合适的缓存目录路径，指定子目录
     * @param packageName 包名
     * @param dirName 缓存文件夹名（如：Log ==>返回:mnt/sdcard/you.packageName/Log/）
     * @return
     */
    public static String getCacheDirByUsable(String packageName, String dirName) {
        // 处理dir
        if (TextUtils.isEmpty(dirName) || dirName.equals(separator)) {
            dirName = "";
        } else if (!dirName.endsWith(separator)) {
            dirName += separator;
        }
        
        // 获取合适的存储路径
        String path = getCacheDirByUsable(packageName) + dirName;
        File pathFile = new File(path);
        pathFile.mkdirs();
        return path;
    }
    
    
    
    /**
     * 是否拥有读写外置存储的权限
     * @param context
     * @return
     */
    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * SDCard是否可用
     * <p>PS：一定存在</p>
     * @return 
     */
    public static boolean canUseSDCard() {
        if (existSDCard()) {
            // 是否可写
            return Environment.getExternalStorageDirectory().canWrite();
        }
        return false;
    }
    /**
     * SDCard是否存在
     * <p>PS：不一定可用</p>
     * @return 
     */
    public static boolean existSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    
    
    /**
     * 内置SDCard是否可用
     * <p>PS：一定存在</p>
     * @return 
     */
    public static boolean canUseSDCardInternal() {
        if (existSDCard()) {
            // 获取内置SDCard目录
            String sdcardInternal = getSDCardInternalDir();
            if (!TextUtils.isEmpty(sdcardInternal)) {
                // 是否可写
                if (new File(sdcardInternal).canWrite()) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 内置SDCard是否存在
     * <p>PS：不一定可用</p>
     * @return 
     */
    public static boolean existSDCardInternal() {
        if (existSDCard()) {
            // 获取内置SDCard目录
            String sdcardInternal = getSDCardInternalDir();
            if (!TextUtils.isEmpty(sdcardInternal)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    /** 
     * 获取SDCard可用大小
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardAvailableSize() {
        if (!existSDCard()) {
            return -1;
        }
        
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getAvailableBytes();
        } else {
            long availableBlocks = stat.getAvailableBlocks();
            long blockSize = stat.getBlockSize();
            return availableBlocks * blockSize;
        }
    }
    /** 
     * 获取SDCard总大小 
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardTotalSize() {
        if (!existSDCard()) {
            return -1;
        }

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getTotalBytes();
        } else {
            long totalBlocks = stat.getBlockCount();
            long blockSize = stat.getBlockSize();
            return totalBlocks * blockSize;
        }
    }
    
    /** 
     * 获取内置SDCard可用大小
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardInternalAvailableSize() {
        if (!existSDCardInternal()) {
            return -1;
        }
        
        File path = new File(getSDCardInternalDir());
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getAvailableBytes();
        } else {
            long availableBlocks = stat.getAvailableBlocks();
            long blockSize = stat.getBlockSize();
            return availableBlocks * blockSize;
        }
    }
    /** 
     * 获取内置SDCard总大小 
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardInternalTotalSize() {
        if (!existSDCardInternal()) {
            return -1;
        }
        
        File path = new File(getSDCardInternalDir());
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getTotalBytes();
        } else {
            long totalBlocks = stat.getBlockCount();
            long blockSize = stat.getBlockSize();
            return totalBlocks * blockSize;
        }
    }

    /**
     * 获取手机内部可用大小
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getDataAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getAvailableBytes();
        } else {
            long availableBlocks = stat.getAvailableBlocks();
            long blockSize = stat.getBlockSize();
            return availableBlocks * blockSize;
        }
    }
    /**
     * 获取手机内部总大小
     * @return 
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getDataTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        if (SdkVersion.isSupport(SdkVersionCodes.JELLY_BEAN_MR2)) {
            return stat.getTotalBytes();
        } else {
            long totalBlocks = stat.getBlockCount();
            long blockSize = stat.getBlockSize();
            return totalBlocks * blockSize;
        }
    }

    /**
     * 获取目录可用的空间大小
     * @param path 要检查的路径
     * @return 可用空间字节大小
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsableSpace(File path) {
        if (SdkVersion.isSupport(SdkVersionCodes.GINGERBREAD)) {
            return path.getUsableSpace();
        } else {
            StatFs stat = new StatFs(path.getPath());
            return stat.getBlockSize() * stat.getAvailableBlocks();
        }
    }
    /**
     * 获取目录总共的空间大小
     * @param path 要检查的路径
     * @return 总共空间字节大小
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getTotalSpace(File path) {
        if (SdkVersion.isSupport(SdkVersionCodes.GINGERBREAD)) {
            return path.getTotalSpace();
        } else {
            StatFs stat = new StatFs(path.getPath());
            return stat.getBlockSize() * stat.getBlockCount();
        }
    }
    
    
    
	

//    public static final String APP_CONFIG = "config";
//    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
//    public final static String CONF_COOKIE = "cookie";
//    public final static String CONF_LOAD_IMAGE = "perf_loadimage";
//    public final static String CONF_SCROLL = "perf_scroll";
//    public final static String CONF_VOICE = "perf_voice";
//    public final static String CONF_CHECKUP = "perf_checkup";
//    public final static String SAVE_IMAGE_PATH = "save_image_path";
//	private static Properties getProperties(Context context, String packageName) {
//		FileInputStream fis = null;
//		Properties props = new Properties();
//		try {
//			// 读取config文件
//			String dirConf = getCacheDataPath(context, packageName);
//			fis = new FileInputStream(dirConf + separator + APP_CONFIG);
//			
//			props.load(fis);
//		} catch (Exception e) {
//		} finally {
//			try {
//				fis.close();
//			} catch (Exception e) {
//			}
//		}
//		return props;
//	}
//
//	private static void saveProperties(Context ctx, Properties props) {
//		FileOutputStream fos = null;
//		try {
//			// 把config建在files目录下
//			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);
//
//			// 把config建在(自定义)app_config的目录下
//			File dirConf = ctx.getDir(APP_CONFIG, Context.MODE_PRIVATE);
//			File conf = new File(dirConf, APP_CONFIG);
//			fos = new FileOutputStream(conf);
//
//			props.store(fos, null);
//			fos.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fos.close();
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	public static void setPropertyAll(Context ctx, Properties ps) {
//		Properties props = getProperties(ctx);
//		props.putAll(ps);
//		saveProperties(ctx, props);
//	}
//	public static void setProperty(Context ctx, String key, String value) {
//		Properties props = getProperties(ctx);
//		props.setProperty(key, value);
//		saveProperties(ctx, props);
//	}
//    public static String getProperty(Context ctx, String key) {
//        Properties props = getProperties(ctx);
//        return (props != null) ? props.getProperty(key) : null;
//    }
//	public static void removeProperty(Context ctx, String... key) {
//		Properties props = getProperties(ctx);
//		for (String k : key)
//			props.remove(k);
//		saveProperties(ctx, props);
//	}
//
//    public static String getCookie(Context ctx) {
//        return getProperty(ctx, CONF_COOKIE);
//    }
//    public static void setCookie(Context ctx, String cookie) {
//        setProperty(ctx, CONF_COOKIE, cookie);
//    }
	
}
