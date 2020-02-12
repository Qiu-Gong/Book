package com.qiugong.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.qiugong.greendao.bean.Person;
import com.qiugong.greendao.bean.User;
import com.qiugong.greendao.db.BaseDao;
import com.qiugong.greendao.db.BaseDaoFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao<Person> person = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
                person.insert(new Person("QiuGong", 18, true));

                BaseDao<User> user = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
                user.insert(new User(1, "Qiu", "123456"));
            }
        });
    }
}
