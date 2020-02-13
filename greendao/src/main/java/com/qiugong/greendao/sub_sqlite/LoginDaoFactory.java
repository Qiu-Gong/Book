package com.qiugong.greendao.sub_sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.qiugong.greendao.bean.Login;
import com.qiugong.greendao.db.BaseDao;
import com.qiugong.greendao.db.BaseDaoFactory;
import com.qiugong.greendao.db.IBaseDao;
import com.qiugong.greendao.db.IBaseDaoFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qzx 20/2/13.
 */
public class LoginDaoFactory implements IBaseDaoFactory {

    private final static String databasePath = "data/data/com.qiugong.greendao/";

    private static final LoginDaoFactory ourInstance = new LoginDaoFactory();
    private Map<String, IBaseDao> map = Collections.synchronizedMap(new HashMap<String, IBaseDao>());

    public static LoginDaoFactory getOurInstance() {
        return ourInstance;
    }

    private String createDatabasePath() {
        Login where = new Login();
        where.setStatus(1);

        IBaseDao<Login> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(LoginDao.class, Login.class);
        List<Login> list = baseDao.query(where);
        Login login;
        if (list != null && list.size() == 1) {
            login = list.get(0);
            return databasePath + login.getId() + "_login.db";
        } else {
            return null;
        }
    }

    public <T> IBaseDao<T> getLoginDao(Class<T> entityClass) {
        String path = createDatabasePath();
        if (path == null) return null;

        IBaseDao<T> baseDao = map.get(path);
        if (baseDao != null) return baseDao;

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
        baseDao = new BaseDao<>();
        baseDao.init(sqLiteDatabase, entityClass);
        map.put(path, baseDao);

        return baseDao;
    }

    @Override
    public <T> IBaseDao<T> getBaseDao(Class<T> entityClass) {
        return null;
    }

    @Override
    public <M extends BaseDao<T>, T> IBaseDao<T> getBaseDao(Class<M> daoClass, Class<T> entityClass) {
        return null;
    }
}
