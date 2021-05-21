package com.liicon.volley.expand;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.liicon.utils.log.LLog;
import com.ta.util.cache.LruCache;

/**
 * 
 * @author King
 * @version v1.0
 * @created 2014-3-11
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageCache {
	public BitmapCache(int maxSize) {
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
