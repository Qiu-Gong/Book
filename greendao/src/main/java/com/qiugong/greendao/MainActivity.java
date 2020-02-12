package com.qiugong.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.qiugong.greendao.bean.Person;
import com.qiugong.greendao.bean.User;
import com.qiugong.greendao.db.BaseDao;
import com.qiugong.greendao.db.BaseDaoFactory;

public class MainActivity extends Activity {

    private int cnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao<Person> person = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
                person.insert(new Person("QiuGong_" + cnt, 18, true));

                BaseDao<User> user = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
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

                BaseDao<User> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                baseDao.update(user, where);
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person where = new Person();
                where.setName("QiuGong_1");

                BaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
                baseDao.delete(where);
            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User where = new User();
                where.setPassword("123456");

                BaseDao<User> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                baseDao.query(where);
            }
        });
    }
}
