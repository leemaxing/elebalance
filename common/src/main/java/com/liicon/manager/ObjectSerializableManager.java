package com.liicon.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;


/**
 * 对象序列化保存到本地
 * @version  v0.1  king  2014-12-16  保存对象到本地
 */
public class ObjectSerializableManager {
    
    private ObjectSerializableManager() {
    }
    

    /**
     * 保存对象
     * @param ser
     * @param file
     * @throws java.io.IOException
     */
    public static boolean saveObject(Context context, Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (Exception e) { }
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) { }
        }
    }

    /**
     * 读取对象
     * @param file
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (Exception e) { }
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) { }
        }
        return null;
    }
    

    /**
     * 保存磁盘缓存
     * @param key
     * @param value
     * @throws java.io.IOException
     */
    public static void saveDiskDataCache(Context context, String key, String value) throws IOException {
        final String cachefile = "cache_" + key + ".data";
        
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(cachefile, Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) { }
        }
    }

    /**
     * 获取磁盘缓存数据
     * @param key
     * @return
     * @throws java.io.IOException
     */
    public static String getDiskDataCache(Context context, String key) throws IOException {
        final String cachefile = "cache_" + key + ".data";
        if (!isExistDataCache(context, cachefile)) {
            return null;
        }
        
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(cachefile);
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) { }
        }
    }

    /**
     * 判断缓存是否失效
     * @param cachefile
     * @return
     */
    public static boolean isCacheDataFailure(Context context, String cachefile, long cacheTime) {
        boolean failure = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > cacheTime)
            failure = true;
        else if (!data.exists())
            failure = true;
        return failure;
    }
    

    /**
     * 判断缓存是否存在
     * @param cachefile
     * @return
     */
    private static boolean isExistDataCache(Context context, String cachefile) {
        return context.getFileStreamPath(cachefile).exists();
    }
    
}
