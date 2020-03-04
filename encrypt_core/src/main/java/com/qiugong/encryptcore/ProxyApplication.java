package com.qiugong.encryptcore;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.qiugong.encryptcore.tools.AES;
import com.qiugong.encryptcore.tools.Utils;
import com.qiugong.encryptcore.tools.Zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author qzx 20/3/1.
 */
public class ProxyApplication extends Application {

    private static final String TAG = "ProxyApplication";

    private String app_name;
    private String app_version;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext: begin");

        // 获取用户填入的metadata
        getMetaData();

        // 得到当前加密了的APK文件
        File apkFile = new File(getApplicationInfo().sourceDir);

        // 把apk解压 app_name+"_"+app_version 目录中的内容需要boot权限才能用
        File versionDir = getDir(app_name + "_" + app_version, MODE_PRIVATE);
        File appDir = new File(versionDir, "app");
        File dexDir = new File(appDir, "dexDir");
        Log.d(TAG, "appDir path: " + appDir.getPath());
        Log.d(TAG, "dexDir path: " + dexDir.getPath());

        // 得到我们需要加载的Dex文件
        List<File> dexFiles = new ArrayList<>();
        // 进行解密（最好做MD5文件校验）
        if (!dexDir.exists() || dexDir.list().length == 0) {
            // 把apk解压到appDir
            Zip.unZip(apkFile, appDir);
            Log.d(TAG, "unZip apk complete.");

            // 获取目录下所有的文件
            File[] files = appDir.listFiles();
            AES aes = new AES();
            aes.init(AES.DEFAULT_PWD);
            for (File file : files) {
                String name = file.getName();
                Log.d(TAG, "list file : " + name);
                if (name.endsWith(".dex") && !TextUtils.equals(name, "classes.dex")) {
                    Log.d(TAG, "decrypt file : " + name);
                    try {
                        // 读取文件内容
                        byte[] bytes = Utils.getBytes(file);
                        byte[] decrypt = aes.decrypt(bytes);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(decrypt);
                        fos.flush();
                        fos.close();
                        dexFiles.add(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Collections.addAll(dexFiles, dexDir.listFiles());
        }
        Log.d(TAG, "dexFiles: " + dexFiles);

        try {
            // 2.把解密后的文件加载到系统
            loadDex(dexFiles, versionDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDex(List<File> dexFiles, File versionDir) throws Exception {
        // 1.获取 DexClassLoader.pathList
        Field pathListField = Utils.findField(getClassLoader(), "pathList");
        Object pathListObj = pathListField.get(getClassLoader());
        Log.d(TAG, "pathList: " + pathListObj);

        // 2.获取数组 DexPathList.dexElements
        Field dexElementsField = Utils.findField(pathListObj, "dexElements");
        Object[] dexElementsObj = (Object[]) dexElementsField.get(pathListObj);
        Log.d(TAG, "dexElements: " + Arrays.toString(dexElementsObj));

        //3.反射到初始化 dexElements 的方法
        Method makeDexElements = Utils.findMethod(pathListObj, "makePathElements", List.class, File.class, List.class);
        ArrayList<IOException> suppressedExceptions = new ArrayList<>();
        Object[] addElements = (Object[]) makeDexElements.invoke(pathListObj, dexFiles, versionDir, suppressedExceptions);
        Log.d(TAG, "addElements: " + Arrays.toString(addElements));

        //合并数组
        Object[] newElements = (Object[]) Array.newInstance(dexElementsObj.getClass().getComponentType(), dexElementsObj.length + addElements.length);
        System.arraycopy(dexElementsObj, 0, newElements, 0, dexElementsObj.length);
        System.arraycopy(addElements, 0, newElements, dexElementsObj.length, addElements.length);
        Log.d(TAG, "newElements: " + Arrays.toString(newElements));

        //替换classloader中的element数组
        dexElementsField.set(pathListObj, newElements);
    }

    private void getMetaData() {
        try {
            ApplicationInfo applicationInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            if (null != metaData) {
                if (metaData.containsKey("app_name")) {
                    app_name = metaData.getString("app_name");
                }
                if (metaData.containsKey("app_version")) {
                    app_version = metaData.getString("app_version");
                }
            }
            Log.d(TAG, "getMetaData: name = " + app_name + " version = " + app_version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
