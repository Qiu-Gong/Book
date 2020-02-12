package com.qiugong.greendao.bean;

import com.qiugong.greendao.annotation.DbField;
import com.qiugong.greendao.annotation.DbTable;

/**
 * @author qzx 20/2/12.
 */
@DbTable("tb_user")
public class User {

    @DbField("_id")
    private Integer id;
    @DbField("_account")
    private String account;
    @DbField("_password")
    private String password;

    public User(Integer id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }
}
