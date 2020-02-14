package com.qiugong.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.qiugong.greendao.bean.Login;
import com.qiugong.greendao.bean.Person;
import com.qiugong.greendao.bean.User;
import com.qiugong.greendao.db.BaseDaoFactory;
import com.qiugong.greendao.db.IBaseDao;
import com.qiugong.greendao.sub_sqlite.LoginDao;
import com.qiugong.greendao.sub_sqlite.LoginDaoFactory;
import com.qiugong.greendao.upgrade.UpgradeManager;

public class MainActivity extends Activity {

    private int cnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBaseDao<Person> person = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
                person.insert(new Person("QiuGong_" + cnt, 18, true));

                IBaseDao<User> user = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                user.insert(new User(cnt, "Qiu", "123456"));

                cnt++;
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setAccount("Wang");
                User where = new User();
                where.setId(1);

                IBaseDao<User> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                baseDao.update(user, where);
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person where = new Person();
                where.setName("QiuGong_1");

                IBaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
                baseDao.delete(where);
            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User where = new User();
                where.setPassword("123456");

                IBaseDao<User> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                baseDao.query(where);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login(cnt, "N0" + cnt, "123456");

                IBaseDao<Login> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(LoginDao.class, Login.class);
                baseDao.insert(login);
                cnt++;
            }
        });

        findViewById(R.id.subInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBaseDao<Person> person = LoginDaoFactory.getOurInstance().getLoginDao(Person.class);
                person.insert(new Person("QiuGong_" + cnt, 18, true));

                cnt++;
            }
        });

        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpgradeManager manager = new UpgradeManager();
                manager.checkVersion(MainActivity.this);
                manager.startUpgradeDb(MainActivity.this);
            }
        });
    }
}
