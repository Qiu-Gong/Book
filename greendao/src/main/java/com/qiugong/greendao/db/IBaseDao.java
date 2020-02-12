package com.qiugong.greendao.db;

/**
 * @author qzx 20/2/12.
 */
public interface IBaseDao<T> {

    // 插入
    long insert(T entity);
}
