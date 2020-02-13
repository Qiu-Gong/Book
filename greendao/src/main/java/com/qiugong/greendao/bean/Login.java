package com.qiugong.greendao.bean;

import com.qiugong.greendao.annotation.DbField;
import com.qiugong.greendao.annotation.DbTable;

/**
 * @author qzx 20/2/12.
 */
@DbTable("tb_login")
public class Login {

    @DbField("_id")
    private Integer id;
    @DbField("_user")
    private String user;
    @DbField("_password")
    private String password;
    @DbField("_status")
    private Integer status;

    public Login() {
    }

    public Login(Integer id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    private Login(Integer id, String user, String password, Integer status) {
        this(id, user, password);
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public Login copy(Login source) {
        return new Login(source.id, source.user, source.password, source.status);
    }
}
