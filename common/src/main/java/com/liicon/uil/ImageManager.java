package com.liicon.uil;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.liicon.manager.FileManager;
import com.liicon.utils.log.LLog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片管理
 * @version  v0.1  king  2014-11-24  实现图片加载
 */
public class ImageManager {
    
    protected static ImageLoaderConfiguration getConfig(Context context,
            int imageOnLoadingRes, int imageOnFailRes) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(imageOnLoadingRes) // 加载中
        //.showImageForEmptyUri(R.drawable.qr_code_content) // resource or drawable
        .showImageOnFail(imageOnFailRes) // resource or drawable
        .resetViewBeforeLoading(false)  // default
        .delayBeforeLoading(50) // 加载前延迟时长
        .cacheInMemory(true) // default = false 是否使用内存缓存
        .cacheOnDisk(true) // default = false 是否使用文件缓存
        .considerExifParams(false) // default = false
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        .displayer(new SimpleBitmapDisplayer()) // default
        .handler(new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
//                LLog.e("dispatchMessage");
                super.dispatchMessage(msg);
            }
            @Override
            public void handleMessage(Message msg) {
//                LLog.e("handleMessage");
                super.handleMessage(msg);
            }
            @Override
            public boolean sendMessageAtTime(Message msg,
                    long uptimeMillis) {
//                LLog.e("sendMessageAtTime");
                return super.sendMessageAtTime(msg, uptimeMillis);
            }
        }) // default
        .build();


        File cacheDir = new File(FileManager.getCacheDirByUsable(context.getPackageName()));
        LLog.e(cacheDir.getAbsolutePath());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 缓存尺寸
                .threadPoolSize(3) // default 线程池大小
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 线程优先级
                .denyCacheImageMultipleSizesInMemory() // 拒绝缓存图片的多个大小
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) // 内存缓存
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 磁盘缓存
                .diskCacheSize(50 * 1024 * 1024) // 磁盘缓存大小
//                .diskCacheFileCount(300) //缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default 磁盘缓存文件名
                .imageDownloader(new BaseImageDownloader(context)) // default 图片下载器
                .imageDecoder(new BaseImageDecoder()) // default 图片解码器
                .defaultDisplayImageOptions(options) // default=DisplayImageOptions.createSimple() 图片显示选项
                .build();
        
        return config;
    }
    
    protected static ImageLoaderConfiguration getConfig(Context context,
            Drawable imageOnLoading, Drawable imageOnFail) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageOnLoading) // 加载中
                //.showImageForEmptyUri(R.drawable.qr_code_content) // resource or drawable
                .showImageOnFail(imageOnFail) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(50) // 加载前延迟时长
                .cacheInMemory(true) // default = false 是否使用内存缓存
                .cacheOnDisk(true) // default = false 是否使用文件缓存
                .considerExifParams(false) // default = false
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler() {
                    @Override
                    public void dispatchMessage(Message msg) {
                        LLog.e("dispatchMessage");
                        super.dispatchMessage(msg);
                    }
                    @Override
                    public void handleMessage(Message msg) {
                        LLog.e("handleMessage");
                        super.handleMessage(msg);
                    }
                    @Override
                    public boolean sendMessageAtTime(Message msg,
                            long uptimeMillis) {
                        LLog.e("sendMessageAtTime");
                        return super.sendMessageAtTime(msg, uptimeMillis);
                    }
                }) // default
                .build();
        

        File cacheDir = new File(FileManager.getCacheDirByUsable(context.getPackageName()));
        LLog.e(cacheDir.getAbsolutePath());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 缓存尺寸
                .threadPoolSize(3) // default 线程池大小
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 线程优先级
                .denyCacheImageMultipleSizesInMemory() // 拒绝缓存图片的多个大小
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) // 内存缓存
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 磁盘缓存
                .diskCacheSize(50 * 1024 * 1024) // 磁盘缓存大小
//                .diskCacheFileCount(300) //缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default 磁盘缓存文件名
                .imageDownloader(new BaseImageDownloader(context)) // default 图片下载器
                .imageDecoder(new BaseImageDecoder()) // default 图片解码器
                .defaultDisplayImageOptions(options) // default=DisplayImageOptions.createSimple() 图片显示选项
                .build();
        
        return config;
    }
    
    protected static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }
    
    /**
     * 初始化【只需调用一次】
     * @param config 配置项
     */
    public static void init(ImageLoaderConfiguration config) {
        getImageLoader().init(config);
    }
    /**
     * 初始化【只需调用一次】
     * @param context
     * @param imageOnLoading 加载中显示的图片
     * @param imageOnFail 加载失败显示的图片
     */
    public static void init(Context context,
            Drawable imageOnLoading, Drawable imageOnFail) {
        getImageLoader().init(getConfig(context, imageOnLoading, imageOnFail));
    }
    /**
     * 初始化【只需调用一次】
     * @param context
     * @param imageOnLoadingRes 加载中显示的图片资源
     * @param imageOnFailRes 加载失败显示的图片资源
     */
    public static void init(Context context,
            int imageOnLoadingRes, int imageOnFailRes) {
        getImageLoader().init(getConfig(context, imageOnLoadingRes, imageOnFailRes));
    }
    
    
    /**
     * 加载图片
     * @param view 将图片显示到该控件
     * @param url
     */
    public static void load(ImageView view, String url) {
        getImageLoader().displayImage(url, view);
    }
    /**
     * 加载图片
     * @param view 将图片显示到该控件
     * @param url
     */
    public static void load(final View view, String url) {
        if (view instanceof ImageView) {
            load((ImageView)view, url);
            return;
        }
        
        getImageLoader().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View viewNull) {
            }
            @Override
            public void onLoadingFailed(String imageUri, View viewNull,
                    FailReason failReason) {
            }
            @SuppressWarnings("deprecation")
            @Override
            public void onLoadingComplete(String imageUri, View viewNull, Bitmap loadedImage) {
                view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), loadedImage));
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }
    
    /**
     * 加载图片
     * @param view 将图片显示到该控件
     * @param url
     * @param options 图片显示配置项
     */
    public static void load(ImageView view, String url, DisplayImageOptions options) {
        getImageLoader().displayImage(url, view, options);
    }
    /**
     * 加载图片
     * @param view 将图片显示到该控件
     * @param url
     * @param listener 图片加载监听器
     */
    public static void load(ImageView view, String url, ImageLoadingListener listener) {
        getImageLoader().displayImage(url, view, listener);
    }
    /**
     * 加载图片
     * @param url
     * @param listener 图片加载监听器
     */
    @SuppressWarnings("rawtypes")
    public static void load(String url, ImageLoadCallBack callBack) {
        getImageLoader().loadImage(url, callBack);
    }
    /**
     * 加载图片
     * @param url
     * @param imageSize 图片大小
     * @param options 图片显示配置项
     * @param listener 图片加载监听器
     */
    @SuppressWarnings("rawtypes")
    public static void load(String url, ImageSize imageSize,
            DisplayImageOptions options, ImageLoadCallBack callBack) {
        getImageLoader().loadImage(url, imageSize, options, callBack);
    }
    
}
