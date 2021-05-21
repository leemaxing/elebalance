package com.liicon.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.liicon.json.Json;
import com.liicon.utils.lang.StringUtils;
import com.liicon.utils.pair.KeyValuePair;

/**
 * 配置管理类-抽象类
 */
abstract class AbsConfig {

    /** 是否DeBug模式（区分是否打印日志） */
    public boolean DEBUG = true;
    
    private Map<String, Object> mConfigMaps = new HashMap<String, Object>();
    

    /**
     * 设置配置项
     * @param keyValuePairs 配置项
     */
    protected void set(List<KeyValuePair<String, ?>> keyValuePairs) {
        if (keyValuePairs == null) {
            return;
        }
        
        for (KeyValuePair<String, ?> keyValuePair : keyValuePairs) {
            set(keyValuePair);
        }
    }
    /**
     * 设置配置项
     * @param keyValuePair 配置项
     */
    protected void set(KeyValuePair<String, ?> keyValuePair) {
        if (keyValuePair == null) {
            return;
        }
        set(keyValuePair.getKey(), keyValuePair.getValue());
    }
    /**
     * 设置配置项
     * @param key 配置项的名字
     * @param value 配置项的值
     */
    protected void set(String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        mConfigMaps.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    /**
     * 获取配置项的值
     * @param key 配置项的名字
     * @return 配置项的值
     */
    public <T> T get(String key) {
        try {
            return (T) mConfigMaps.get(key);
        } catch (ClassCastException e) {
            return null;
        }
    }
	
    
    /**
     * 初始化配置项
     */
    protected abstract void initialize();
	
	

    /**
     * 查看Config的类名
     * @return
     */
    public String getConfigClass() {
        return getClass().getName();
    }
    
    
    


    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key) {
        try {
            return (T) BasicConfig.getConfig().get(key);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    public static int getInt(String key) {
        try {
            return (int) Integer.parseInt(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    public static float getFloat(String key) {
        try {
            return (float) Float.parseFloat(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    public static double getDouble(String key) {
        try {
            return (double) Double.parseDouble(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    public static short getShort(String key) {
        try {
            return (short) Short.parseShort(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    public static byte getByte(String key) {
        try {
            return (byte) Byte.parseByte(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    public static boolean getBoolean(String key) {
        try {
            return (boolean) Boolean.parseBoolean(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return false;
        }
    }
   /* public static char getChar(String key) {
        try {
            return (char) String.valueOf(getObject(key);
        } catch (Exception e) {
            return (char) -1;
        }
    }*/
    public static long getLong(String key) {
        try {
            return (long) Long.parseLong(String.valueOf(getObject(key)));
        } catch (Exception e) {
            return -1;
        }
    }
    
    public static String getString(String key, Object... args) {
        return String.format(getString(key), args);
    }
    public static String getString(String key) {
        return  (String) getObject(key);
    }
    public static Date getDate(String key) {
        return (Date) getObject(key);
    }
    public static java.sql.Date getSqlDate(String key) {
        return (java.sql.Date) getObject(key);
    }
    public static byte[] getByteArray(String key) {
        return (byte[]) getObject(key);
    }
    public static Bitmap getBitmap(String key) {
        return (Bitmap) getObject(key);
    }
    public static Drawable getDrawable(String key) {
        return (Drawable) getObject(key);
    }
    public static Json getJson(String key) {
        return (Json) getObject(key);
    }
    
    
    
	
	
	/*
	 * 设置全局配置管理类
	 */
	private static BasicConfig config;
	private static boolean isInit = false;
	public static void setConfig(Class<? extends BasicConfig> configClass) {
        if (isInit) {
            throw new UnsupportedOperationException("Config already initialized.");
        }
        
        isInit = true;
	    try {
	        config = configClass.newInstance();
	    } catch (Exception e) { }
	    config.initialize();
	}
	public static void setConfig(BasicConfig config) {
        if (isInit) {
            throw new UnsupportedOperationException("Config already initialized.");
        }

        isInit = true;
        AbsConfig.config = config;
        AbsConfig.config.initialize();
	}
	public static void resetConfig() {
        if (isInit) {
            throw new UnsupportedOperationException("Config already initialized.");
        }

        isInit = true;
	    setConfig(new BasicConfig() {
	        @Override
	        protected void initialize() {
	        }
            @Override
            public String getConfigClass() {
                return "This is Default Config Class.";
            }
        });
	}
	public static BasicConfig getConfig() {
	    if (!isInit) {
	        throw new ExceptionInInitializerError("Config not initialized.");
	    }
	    
		return config;
	}

    @Override
    public String toString() {
        return mConfigMaps.toString();
    }
}
