package com.qiugong.skin_core;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * @author qzx 20/3/20.
 */
public class SkinManager extends Observable {

    private static SkinManager instance;

    private Application mContext;

    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }

    private SkinManager(Application application) {
        mContext = application;

        SkinPreference.init(application);
        SkinResources.init(application);

        SkinActivityLifecycle skinActivityLifecycle = new SkinActivityLifecycle();
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle);

        try {
            loadSkin(SkinPreference.getInstance().getSkinPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSkin(String skinPath) throws Exception {
        if (TextUtils.isEmpty(skinPath)) {
            SkinPreference.getInstance().setSkinPath("");
            SkinResources.getInstance().reset();

        } else {
            // 创建 AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPath);

            // 创建 Resources
            Resources appResources = mContext.getResources();
            Resources skinResources = new Resources(assetManager, appResources.getDisplayMetrics(), appResources.getConfiguration());

            // 获取外部Apk(皮肤包)包名
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            String packageName = packageInfo.packageName;

            // 更新皮肤
            SkinResources.getInstance().applySkin(skinResources, packageName);

            // 记录
            SkinPreference.getInstance().setSkinPath(skinPath);
        }

        setChanged();
        notifyObservers(null);
    }
}
