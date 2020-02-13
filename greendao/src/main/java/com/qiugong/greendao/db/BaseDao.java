package com.qiugong.greendao.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qiugong.greendao.annotation.DbField;
import com.qiugong.greendao.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        this.columnFieldMap = new HashMap<>();

        this.tableName = entityClass.getAnnotation(DbTable.class) != null ?
                entityClass.getAnnotation(DbTable.class).value() :
                entityClass.getSimpleName();

        String sql = createTableSql();
        Log.d(TAG, "init sql:" + sql);
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

    private ContentValues convertContentValues(T entity) {
        ContentValues values = new ContentValues();

        Set<String> column = columnFieldMap.keySet();
        for (String key : column) {
            try {
                Field field = columnFieldMap.get(key);
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object value = field.get(entity);
                if (value == null) continue;

                if (type == String.class) {
                    values.put(key, (String) value);
                } else if (type == Integer.class) {
                    values.put(key, (Integer) value);
                } else if (type == Long.class) {
                    values.put(key, (Long) value);
                } else if (type == Double.class) {
                    values.put(key, (Double) value);
                } else if (type == byte[].class) {
                    values.put(key, (byte[]) value);
                } else if (type == Boolean.class) {
                    values.put(key, (Boolean) value);
                } else {
                    throw new RuntimeException("type is error:" + type.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "ContentValues: " + values.toString());

        return values;
    }

    private List<T> convertQuery(Cursor query, T where) throws Exception {
        List<T> result = new ArrayList<>();

        while (query.moveToNext()) {
            String[] columnNames = query.getColumnNames();
            T item = (T) where.getClass().newInstance();
            for (String columnName : columnNames) {

                Field field = columnFieldMap.get(columnName);
                if (item != null && field != null) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    int columnIndex = query.getColumnIndex(columnName);

                    if (type == String.class) {
                        field.set(item, query.getString(columnIndex));
                    } else if (type == Integer.class) {
                        field.set(item, query.getInt(columnIndex));
                    } else if (type == Long.class) {
                        field.set(item, query.getLong(columnIndex));
                    } else if (type == Double.class) {
                        field.set(item, query.getDouble(columnIndex));
                    } else if (type == byte[].class) {
                        field.set(item, query.getBlob(columnIndex));
                    } else if (type == Boolean.class) {
                        field.set(item, query.getString(columnIndex));
                    } else {
                        throw new RuntimeException("type is error:" + type.getName());
                    }
                }
            }
            result.add(item);
        }

        Log.d(TAG, "convertQuery: " + result.toString());
        return result;
    }

    @Override
    public long insert(T entity) {
        ContentValues values = convertContentValues(entity);
        return sqLiteDatabase.insert(tableName, null, values);
    }

    @Override
    public long update(T entity, T where) {
        ContentValues values = convertContentValues(entity);
        Condition<T> condition = new Condition<>(columnFieldMap, where);

        return sqLiteDatabase.update(tableName, values,
                condition.getWhereClause(),
                condition.getWhereArgs());
    }

    @Override
    public int delete(T where) {
        Condition<T> condition = new Condition<>(columnFieldMap, where);
        return sqLiteDatabase.delete(tableName,
                condition.getWhereClause(),
                condition.getWhereArgs());
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Condition<T> condition = new Condition<>(columnFieldMap, where);
        String limitString = null;
        List<T> result = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }

        try {
            Cursor query = sqLiteDatabase.query(tableName, null,
                    condition.getWhereClause(), condition.getWhereArgs(), null,
                    orderBy, limitString);
            result = convertQuery(query, where);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
