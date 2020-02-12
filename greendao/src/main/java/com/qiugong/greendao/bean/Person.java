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

    public Person() {
    }

    public Person(String name, Integer age, Boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
