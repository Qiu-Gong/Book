package com.qiugong.tinker;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qiugong.tinker.core.FixDex;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        findViewById(R.id.fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixBug();
            }
        });
    }

    private void calculate() {
        Calculator calculator = new Calculator();
        calculator.calculate(this);
    }

    private void fixBug() {
        File sourceFile = new File(Environment.getExternalStorageDirectory(), "classes2.dex");
        File targetFile = new File(
                getDir(FixDex.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "classes2.dex");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        Log.d(TAG, "fixBug: sourceFile = " + sourceFile + " targetFile = " + targetFile);

        try {
            copyFile(sourceFile, targetFile);
            Toast.makeText(this, "复制到私有目录完成", Toast.LENGTH_SHORT).show();
            FixDex.loadFixedDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
        BufferedOutputStream outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }

        outBuff.flush();
        inBuff.close();
        outBuff.close();
    }
}
