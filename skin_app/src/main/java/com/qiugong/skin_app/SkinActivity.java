package com.qiugong.skin_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.qiugong.skin_core.Skin;
import com.qiugong.skin_core.SkinManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/3/19.
 */
public class SkinActivity extends AppCompatActivity {

    private static final String TAG = "SkinActivity";

    private List<Skin> skins = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        skins.add(new Skin("ca314738be6a93e8c7552ac662bc63bb", "qiu.skin", "skin_01-debug.apk"));

        findViewById(R.id.change_skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "更换皮肤");
                    Skin skin = skins.get(0);
                    selectSkin(skin);
                    SkinManager.getInstance().loadSkin(skin.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.restore_skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "还原皮肤");
                    SkinManager.getInstance().loadSkin(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.mode_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NightModeConfig.getInstance().getNightMode(getApplicationContext())) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(), false);
                    recreate();
                    Log.d(TAG, "日间模式");
                }
            }
        });

        findViewById(R.id.mode_night).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NightModeConfig.getInstance().getNightMode(getApplicationContext())) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(), true);
                    recreate();
                    Log.d(TAG, "夜间模式");
                }
            }
        });
    }

    private void selectSkin(Skin skin) {
        File theme = new File(getFilesDir(), "theme");
        Log.d(TAG, "theme path:" + theme.getPath());
        if (theme.exists() && theme.isFile()) {
            theme.delete();
        }
        theme.mkdirs();

        File skinFile = skin.getSkinFile(theme);
        Log.d(TAG, "skinFile: " + skinFile.getPath());
        if (skinFile.exists()) {
            Log.d(TAG, "皮肤已存在,开始换肤");
            return;
        }

        Log.d(TAG, "皮肤不存在,开始下载");
        FileOutputStream fos = null;
        InputStream is = null;
        File tempSkin = new File(skinFile.getParentFile(), skin.getName() + ".temp");
        try {
            fos = new FileOutputStream(tempSkin);
            is = getAssets().open(skin.getUrl());
            byte[] bytes = new byte[10240];
            int len;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            Log.d(TAG, "皮肤包下载完成开始校验");
            if (TextUtils.equals(Skin.getSkinMD5(tempSkin), skin.getMd5())) {
                Log.d(TAG, "校验成功,修改文件名。");
                tempSkin.renameTo(skinFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempSkin.delete();
            Skin.close(fos);
            Skin.close(is);
        }
    }
}
