package com.qiugong.greendao.db;

import android.database.sqlite.SQLiteDatabase;

import com.qiugong.greendao.Constants;

/**
 * @author qzx 20/2/12.
 */
public class BaseDaoFactory implements IBaseDaoFactory {

    private static final IBaseDaoFactory ourInstance = new BaseDaoFactory();
    private String databasePath = Constants.DATABASE_DEFAULT_PATH;
    private SQLiteDatabase sqLiteDatabase;

    public static IBaseDaoFactory getOurInstance() {
        return ourInstance;
    }

    private BaseDaoFactory() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
    }

    @Override
    public <T> IBaseDao<T> getBaseDao(Class<T> entityClass) {
        IBaseDao<T> baseDao = new BaseDao<>();
        baseDao.init(sqLiteDatabase, entityClass);
        return baseDao;
    }

    @Override
    public <M extends BaseDao<T>, T> IBaseDao<T> getBaseDao(Class<M> daoClass, Class<T> entityClass) {
        M m = null;
        try {
            m = daoClass.newInstance();
            m.init(sqLiteDatabase, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
