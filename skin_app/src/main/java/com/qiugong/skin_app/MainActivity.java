package com.qiugong.skin_app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.qiugong.skin_app.adapter.MyFragmentPagerAdapter;
import com.qiugong.skin_app.fragment.MusicFragment;
import com.qiugong.skin_app.fragment.RadioFragment;
import com.qiugong.skin_app.fragment.VideoFragment;
import com.qiugong.skin_app.widget.MyTabLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final Fragment[] fragments = new Fragment[]{new MusicFragment(), new VideoFragment(), new RadioFragment()};
    private final String[] titles = new String[]{"音乐", "视频", "电台"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyFragmentPagerAdapter fragmentPagerAdapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), Arrays.asList(fragments), Arrays.asList(titles));
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.select_skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
