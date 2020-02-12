package com.qiugong.greendao.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author qzx 20/2/12.
 */
public class BaseDaoFactory {

    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();
    private String databasePath;
    private SQLiteDatabase sqLiteDatabase;

    public static BaseDaoFactory getOurInstance() {
        return ourInstance;
    }

    private BaseDaoFactory() {
        databasePath = "data/data/com.qiugong.greendao/qiu.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao<T> baseDao = new BaseDao<>();
        baseDao.init(sqLiteDatabase, entityClass);
        return baseDao;
    }
}
