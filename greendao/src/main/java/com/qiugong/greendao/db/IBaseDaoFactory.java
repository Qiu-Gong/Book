package com.qiugong.greendao.db;

/**
 * @author qzx 20/2/13.
 */
public interface IBaseDaoFactory {

    <T> IBaseDao<T> getBaseDao(Class<T> entityClass);

    <M extends BaseDao<T>, T> IBaseDao<T> getBaseDao(Class<M> daoClass, Class<T> entityClass);
}
