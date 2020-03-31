package com.qiugong.skin_core;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Skin {

    // 文件校验md5值
    private String md5;
    //下载地址
    private String url;
    // 皮肤名
    private String name;
    // 下载完成后缓存地址
    private String path;
    // 文件
    private File file;

    public Skin(String md5, String name, String url) {
        this.md5 = md5;
        this.name = name;
        this.url = url;
    }

    public File getSkinFile(File theme) {
        if (null == file) {
            file = new File(theme, name);
        }
        path = file.getAbsolutePath();
        return file;
    }

    public String getMd5() {
        return md5;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public static String getSkinMD5(File file) {
        FileInputStream fis = null;
        BigInteger bi;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 10];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            byte[] digest = MD5.digest();
            bi = new BigInteger(1, digest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(fis);
        }
        return bi.toString(16);
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
