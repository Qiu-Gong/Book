package com.qiugong.greendao.sub_sqlite;

import android.util.Log;

import com.qiugong.greendao.bean.Login;
import com.qiugong.greendao.db.BaseDao;

import java.util.List;

/**
 * @author qzx 20/2/13.
 */
public class LoginDao extends BaseDao<Login> {

    private static final String TAG = "LoginDao";

    @Override
    public long insert(Login entity) {
        Log.d(TAG, "login dao insert");
        List<Login> list = query(new Login());

        for (Login login : list) {
            Log.d(TAG, "用户 " + login.getUser() + " 退出登陆");
            Login where = login.copy(login);
            login.setStatus(0);
            update(login, where);
        }

        Log.d(TAG, "用户 " + entity.getUser() + " 登陆");
        entity.setStatus(1);
        return super.insert(entity);
    }
}
