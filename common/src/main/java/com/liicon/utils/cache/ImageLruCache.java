package com.liicon.utils.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.liicon.utils.log.LLog;
import com.ta.util.cache.LruCache;

/**
 * 图片内存缓存
 * @version  v0.1  king  2014-03-11  LRU算法实现内存缓存
 * @version  v0.2  king  2014-11-24  实现单例模式
 */
public class ImageLruCache extends LruCache<String, Bitmap> implements ImageCache {
    private static ImageLruCache INSTANCE;
    /**
     * 默认内存的1/8
     * @param context
     * @return
     */
    public static ImageLruCache getInstance(Context context) {
        if (INSTANCE == null) {
            final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();  
            final int maxCacheSize = 1024 * 1024 * memClass / 8;
            INSTANCE = new ImageLruCache(maxCacheSize);
        }
        return INSTANCE;
    }
    public static ImageLruCache getInstance(int maxSize) {
        if (INSTANCE == null) {
            INSTANCE = new ImageLruCache(maxSize);
        }
        return INSTANCE;
    }
    
	protected ImageLruCache(int maxSize) {
		super(maxSize);
	}
	
	
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		LLog.i("Retrieved item from Mem Cache");
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
	    LLog.i("Added item to Mem Cache");
		put(url, bitmap);
	}

}
