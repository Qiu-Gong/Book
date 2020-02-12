package com.qiugong.greendao.bean;

import com.qiugong.greendao.annotation.DbField;
import com.qiugong.greendao.annotation.DbTable;

/**
 * @author qzx 20/2/12.
 */
@DbTable("tb_person")
public class Person {

    @DbField("_name")
    private String name;
    @DbField("_age")
    private Integer age;
    @DbField("_sex")
    private Boolean sex;

    public Person(String name, Integer age, Boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
