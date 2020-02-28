package com.qiugong.tinker.core;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author qzx 20/2/28.
 */
public class FixDex {

    public final static String DEX_DIR = "odex";
    private final static String DEFAULT_DEX = "classes.dex";
    private final static String DEX_ENDS = ".dex";
    private final static String OPT_DEX_DIR = "opt_dex";

    private static Set<File> loadDex = new HashSet<>();

    public static void loadFixedDex(Context context) {
        // data/user/0/com.qiugong.thinker/app_odex
        File fileDir = context.getDir(DEX_DIR, Context.MODE_PRIVATE);
        File[] files = fileDir.listFiles();
        if (files == null || files.length == 0) return;

        for (File file : files) {
            if (file.getName().endsWith(DEX_ENDS) && !file.getName().equals(DEFAULT_DEX)) {
                loadDex.add(file);
            }
        }
        createDexClassLoader(context, fileDir);
    }

    private static void createDexClassLoader(Context context, File fileDir) {
        // data/user/0/com.qiugong.thinker/app_odex/opt_dex
        String optimizedDirectory = fileDir.getAbsolutePath() + File.separator + OPT_DEX_DIR;
        File fOpt = new File(optimizedDirectory);
        if (!fOpt.exists()) {
            fOpt.mkdirs();
        }

        DexClassLoader classLoader;
        for (File dex : loadDex) {
            classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    optimizedDirectory, null, context.getClassLoader());
            hotFix(classLoader, context);
        }
    }

    private static void hotFix(DexClassLoader classLoader, Context context) {
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            // 1. 获取自己的DexElements数组对象
            // BaseDexClassLoader.pathList.dexElements
            // /data/user/0/com.qiugong.tinker/app_odex/classes2.dex
            Object newPathList = ReflectUtils.getPathList(classLoader);
            Object newDexElements = ReflectUtils.getDexElements(newPathList);

            // 2. 获取系统的DexElements数组对象
            // /data/app/com.qiugong.tinker-rIf3DFHFQxeMMf-DyQnjiw==/base.apk
            Object sysPathList = ReflectUtils.getPathList(pathClassLoader);
            Object sysDexElements = ReflectUtils.getDexElements(sysPathList);

            // 3. 合并
            Object dexElements = combineArray(newDexElements, sysDexElements);

            // 5. 重新赋值给系统的 pathList
            ReflectUtils.setField(sysPathList, sysPathList.getClass(), dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并数组
     *
     * @param arrayLhs 前数组（插队数组）
     * @param arrayRhs 后数组（已有数组）
     * @return 处理后的新数组
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }
}
