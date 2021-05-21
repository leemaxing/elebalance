package com.liicon.db;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;

/**
 * SQLite管理工具
 * @version  v0.1  king  2014-12-15
 */
public class DBManager {

    /**
     * 创建DBManager单例
     * 
     * <pre>
     * 默认：
     * dbName="xUtils.db",
     * dbVersion=1,
     * DB文件保存路径=“APP内部缓存目录”（/data/data/youpackage/cache）
     * </pre>
     * 
     * @param context android.content.Context
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(Context context) {
        return DbUtils.create(context);
    }
    /**
     * 创建DBManager单例（根据dbName的不同，创建多个实例）
     * 
     * <pre>
     * 默认：
     * dbVersion=1,
     * DB文件保存路径=“APP内部缓存目录”（/data/data/youpackage/cache）
     * </pre>
     * 
     * @param context android.content.Context
     * @param dbName 数据库文件名
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(Context context, String dbName) {
        return DbUtils.create(context, dbName);
    }
    /**
     * 创建DBManager单例（根据dbName的不同，创建多个实例）
     * 
     * <pre>
     * 默认：
     * dbVersion=1
     * </pre>
     * 
     * @param context android.content.Context
     * @param dbDir 数据库文件存储路径
     * @param dbName 数据库文件名
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(Context context, String dbDir, String dbName) {
        return DbUtils.create(context, dbDir, dbName);
    }
    /**
     * 创建DBManager单例（根据dbName的不同，创建多个实例）
     * 
     * <pre>
     * 默认：
     * DB文件保存路径=“APP内部缓存目录”（/data/data/youpackage/cache）
     * </pre>
     * 
     * @param context android.content.Context
     * @param dbName 数据库文件名
     * @param dbVersion 数据库版本号
     * @param dbUpgradeListener 数据库版本升级通知接口
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(Context context, String dbName, int dbVersion, DbUpgradeListener dbUpgradeListener) {
        return DbUtils.create(context, dbName, dbVersion, dbUpgradeListener);
    }
    /**
     * 创建DBManager单例（根据dbName的不同，创建多个实例）
     * 
     * @param context android.content.Context
     * @param dbDir 数据库文件存储路径
     * @param dbName 数据库文件名
     * @param dbVersion 数据库版本号
     * @param dbUpgradeListener 数据库版本升级通知接口
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(Context context, String dbDir, String dbName, int dbVersion, DbUpgradeListener dbUpgradeListener) {
        return DbUtils.create(context, dbDir, dbName, dbVersion, dbUpgradeListener);
    }
    /**
     * 创建DBManager单例（根据dbName的不同，创建多个实例）
     * 
     * @param daoConfig DB操作的配置项{@link com.lidroid.xutils.DbUtils.DaoConfig}
     * @return DBManager实例{@link com.lidroid.xutils.DBManager}
     */
    public static DbUtils create(DaoConfig daoConfig) {
        return DbUtils.create(daoConfig);
    }
    
}
