package com.qiugong.greendao.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @author qzx 20/2/12.
 */
public interface IBaseDao<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass);

    // 插入
    long insert(T entity);

    //更新
    long update(T entity, T where);

    //删除
    int delete(T where);

    // 查询
    List<T> query(T where);

    // 查询
    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);
}
