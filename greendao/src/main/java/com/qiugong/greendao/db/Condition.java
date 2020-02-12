package com.qiugong.greendao.db;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * @author qzx 20/2/12.
 */
public class Condition<T> {

    private static final String TAG = "Condition";
    private String whereClause;
    private String[] whereArgs;

    // "id = ?", new String[]{"1"}
    public Condition(HashMap<String, Field> map, T where) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        Set<String> column = map.keySet();
        for (String key : column) {
            try {
                Field field = map.get(key);
                field.setAccessible(true);
                Object value = field.get(where);
                if (value == null) continue;

                list.add(value.toString());
                if (builder.length() == 0) {
                    builder.append(key).append(" = ?");
                } else {
                    builder.append(" and ").append(key).append(" = ?");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        whereClause = builder.toString();
        whereArgs = list.toArray(new String[0]);

        Log.d(TAG, "update whereClause:" + whereClause +
                " whereArgs:" + Arrays.toString(whereArgs));
    }

    public String getWhereClause() {
        return whereClause;
    }

    public String[] getWhereArgs() {
        return whereArgs;
    }
}
