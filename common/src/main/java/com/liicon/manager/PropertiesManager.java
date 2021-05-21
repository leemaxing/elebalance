package com.liicon.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;

import com.liicon.utils.lang.StringUtils;

/**
 * 应用程序属性管理器：用于保存用户相关信息及设置
 * @version  v0.1  king  2013-10-25  实现属性存取
 * @version  v0.1  king  2013.11.20  修改方法为静态方法
 * @version  v0.2  king  2014-12-16  重新组织类
 */
public final class PropertiesManager {
    
    private static final String DIR_NAME = "app";
    private static final String FILE_NAME = "config";
	
	private PropertiesManager() {
	}
	
	/**
	 * 读取保存数据的Properties对象
	 * @param ctx 
	 * @return
	 */
    public static Properties loadProperties(Context ctx) {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            // 读取files目录下的config
            // fis = activity.openFileInput(ConfigKeys.APP_PROPERTIES);

            // 读取app目录下的config
            File dirConf = ctx.getDir(DIR_NAME, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator + FILE_NAME);
            props.load(fis);
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return props;
    }
    

	private static void saveProperties(Context ctx, Properties props) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(ConfigKeys.APP_PROPERTIES, Context.MODE_PRIVATE);

			// 把app_properties建在(自定义)app_core的目录下
			File dirConf = ctx.getDir(DIR_NAME, Context.MODE_PRIVATE);
			File conf = new File(dirConf, DIR_NAME);
			fos = new FileOutputStream(conf);

			props.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

    
    /**
     * 删除数据
     * @param ctx
     * @param keys
     */
	public static void remove(Context ctx, String... keys) {
		Properties props = loadProperties(ctx);
		for (String key : keys)
			props.remove(key);
		saveProperties(ctx, props);
	}
	/**
	 * 判断是否包含该Key对应的数据
	 * @param context
	 * @param key
	 * @return
	 */
    public static boolean containsKey(Context context, String key) {
        return loadProperties(context).containsKey(key);
    }
	
	
	
	
	/**
     * 保存Properties内的所有数据
     * @param ctx
     * @param properties
     */
    public static void set(Context ctx, Properties properties) {
        Properties props = loadProperties(ctx);
        props.putAll(properties);
        saveProperties(ctx, props);
    }
    /**
     * 保存数据
     * @param ctx
     * @param key
     * @param value
     */
    public static void set(Context ctx, String key, String value) {
        Properties props = loadProperties(ctx);
        props.setProperty(key, value);
        saveProperties(ctx, props);
    }
    public static void set(Context context, String key, boolean value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, int value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, long value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, char value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, short value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, double value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, float value) {
        set(context, key, StringUtils.toString(value));
    }
    public static void set(Context context, String key, byte value) {
        set(context, key, StringUtils.toString(value));
    }
	
	
	
	
    /**
     * 获取数据
     * @param ctx
     * @param key
     * @return
     */
    public static String getString(Context ctx, String key) {
        Properties props = loadProperties(ctx);
        return (props != null) ? props.getProperty(key) : null;
    }
    /**
     * 获取boolean类型Property，并做非空判断
     * @param key
     * @return 默认：false
     */
    public static boolean getBoolean(Context context, String key) {
        String perf = getString(context, key);
        if (StringUtils.isEmpty(perf))
            return false;
        else
            return StringUtils.toBoolean(perf, false);
    }
    public static int getInt(Context context, String key) {
        return StringUtils.toInt(getString(context, key), -1);
    }
    public static long getLong(Context context, String key) {
        return StringUtils.toLong(getString(context, key), -1);
    }
    public static char getChar(Context context, String key) {
        return StringUtils.toChar(getString(context, key), (char)-1);
    }
    public static short getShort(Context context, String key) {
        return StringUtils.toShort(getString(context, key), (short)-1);
    }
    public static double getDouble(Context context, String key) {
        return StringUtils.toDouble(getString(context, key), (double)-1);
    }
    public static float getFloat(Context context, String key) {
        return StringUtils.toFloat(getString(context, key), (float)-1);
    }
    public static byte getByte(Context context, String key) {
        return StringUtils.toByte(getString(context, key), (byte)-1);
    }
    
}
