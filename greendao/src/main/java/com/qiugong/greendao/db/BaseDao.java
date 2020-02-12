package com.qiugong.greendao.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qiugong.greendao.annotation.DbField;
import com.qiugong.greendao.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

/**
 * @author qzx 20/2/12.
 */
public class BaseDao<T> implements IBaseDao<T> {

    private static final String TAG = "BaseDao";

    private SQLiteDatabase sqLiteDatabase;
    private String tableName;
    private Class<T> entityClass;
    private HashMap<String, Field> columnFieldMap;

    void init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        this.columnFieldMap = new HashMap<>();

        this.tableName = entityClass.getAnnotation(DbTable.class) != null ?
                entityClass.getAnnotation(DbTable.class).value() :
                entityClass.getSimpleName();

        String sql = createTableSql();
        this.sqLiteDatabase.execSQL(sql);
        columnMapField();
    }

    private void columnMapField() {
        String sql = "select * from " + tableName + " limit 1, 0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        Field[] fields = entityClass.getDeclaredFields();

        for (String column : columnNames) {
            for (Field field : fields) {
                String fieldName = field.getAnnotation(DbField.class) != null ?
                        field.getAnnotation(DbField.class).value() :
                        field.getName();
                if (fieldName.equals(column)) {
                    columnFieldMap.put(column, field);
                    break;
                }
            }
        }

        cursor.close();
    }

    /**
     * create table if not exists
     * tb_user(_id integer, name varchar(20), password varchar(20))
     */
    private String createTableSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists ");
        builder.append(tableName).append("(");

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();

            String value = field.getAnnotation(DbField.class) != null ?
                    field.getAnnotation(DbField.class).value() :
                    field.getName();
            builder.append(value);

            if (type == String.class) {
                builder.append(" TEXT,");
            } else if (type == Integer.class) {
                builder.append(" INTEGER,");
            } else if (type == Long.class) {
                builder.append(" BIGINT,");
            } else if (type == Double.class) {
                builder.append(" DOUBLE,");
            } else if (type == byte[].class) {
                builder.append(" BLOB,");
            } else if (type == Boolean.class) {
                builder.append(" BOOLEAN,");
            } else {
                throw new RuntimeException("type is error:" + type.getName());
            }
        }

        if (builder.charAt(builder.length() - 1) == ',') {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public long insert(T entity) {
        ContentValues values = new ContentValues();

        Set<String> column = columnFieldMap.keySet();
        for (String key : column) {
            try {
                Field field = columnFieldMap.get(key);
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type == String.class) {
                    values.put(key, (String) field.get(entity));
                } else if (type == Integer.class) {
                    values.put(key, (Integer) field.get(entity));
                } else if (type == Long.class) {
                    values.put(key, (Long) field.get(entity));
                } else if (type == Double.class) {
                    values.put(key, (Double) field.get(entity));
                } else if (type == byte[].class) {
                    values.put(key, (byte[]) field.get(entity));
                }else if (type == Boolean.class) {
                    values.put(key, (Boolean) field.get(entity));
                } else {
                    throw new RuntimeException("type is error:" + type.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sqLiteDatabase.insert(tableName, null, values);
    }
}
