package com.qiugong.dongnao.x06;

import com.qiugong.dongnao.x06.annotation.Column;
import com.qiugong.dongnao.x06.annotation.Table;

/**
 * @author qzx 20/1/21.
 */
@Table("@person")
public class Person {
    @Column("@name")
    private String name;
    @Column("@user_name")
    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
