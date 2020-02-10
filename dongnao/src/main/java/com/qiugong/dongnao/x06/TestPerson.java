package com.qiugong.dongnao.x06;

import com.qiugong.dongnao.x06.annotation.Column;
import com.qiugong.dongnao.x06.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author qzx 20/1/21.
 */
public class TestPerson {

    public static void main(String[] args) {
        Person p1 = new Person();
        p1.setName("Qiu");
        p1.setUserName("Gong");

        String str = query(p1);
        System.out.println(str);
    }

    private static String query(Person person) {
        StringBuilder sb = new StringBuilder();
        Class p = person.getClass();
        boolean exist = p.isAnnotationPresent(Table.class);
        if (!exist) {
            System.out.println("Table isn't exist.");
            return null;
        }

        Table table = (Table) p.getAnnotation(Table.class);
        // @person
        String tableName = table.value();
        sb.append(tableName).append(" -> ");

        Field[] fArray = p.getDeclaredFields();
        for (Field field : fArray) {
            boolean fExist = field.isAnnotationPresent(Column.class);
            if (!fExist) {
                System.out.println("Field isn't exist.");
                return null;
            }

            Column column = field.getAnnotation(Column.class);
            // @name @user_name
            String columnName = column.value();
            // name userName
            String fieldName = field.getName();
            Object fieldValue = null;
            // getName getUserName
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                Method method = p.getMethod(getMethodName);
                fieldValue = method.invoke(person);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(columnName).append(":").append(fieldValue).append(" ");
        }

        return sb.toString();
    }
}
