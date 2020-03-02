package com.qiugong.encryptcore.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

/**
 * @author qzx 20/3/1.
 */
public class Main {

    private static final String BUILD_TOOLS = "D:\\Android\\Sdk\\build-tools\\28.0.3\\";
    private static final String CMD_DX = "dx.bat --dex --output";
    private static final String CMD_ZIPALIGN = "zipalign.exe -v -p 4";
    private static final String SPACE = " ";

    private static final String CORE_AAR_FILE_DIR = "encrypt_core/build/outputs/aar/encrypt_core-debug.aar";
    private static final String CORE_AAR_TEMP_DIR = "encrypt_core/build/outputs/temp";

    private static final String APP_FILE_DIR = "encrypt_app/build/outputs/apk/debug/";
    private static final String APP_TEMP_DIR = "encrypt_app/build/outputs/temp";

    private static final String KEY_ALIAS = "jett";
    private static final String KEY_PASS = "123456";

    public static void main(String[] args) throws Exception {

        // 1.制作只包含解密代码的dex文件
        File aarFile = new File(CORE_AAR_FILE_DIR);
        File aarTemp = new File(CORE_AAR_TEMP_DIR);
        Zip.unZip(aarFile, aarTemp);
        File classesJar = new File(aarTemp, "classes.jar");
        File classesDex = new File(aarTemp, "classes.dex");

        // dx --dex --output out.dex in.jar
        Process process = Runtime
                .getRuntime()
                .exec(BUILD_TOOLS + CMD_DX
                        + SPACE + classesDex.getAbsolutePath()
                        + SPACE + classesJar.getAbsolutePath());
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new RuntimeException("dex error");
        }

        // 2.加密APK中所有的dex文件
        File apkFile = new File(APP_FILE_DIR + "encrypt_app-debug.apk");
        File apkTemp = new File(APP_TEMP_DIR);
        Zip.unZip(apkFile, apkTemp);
        File[] dexFiles = apkTemp.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".dex");
            }
        });

        AES aes = new AES();
        aes.init(AES.DEFAULT_PWD);
        for (File dexFile : dexFiles) {
            byte[] bytes = Utils.getBytes(dexFile);
            byte[] encrypt = aes.encrypt(bytes);
            FileOutputStream fos = new FileOutputStream(new File(apkTemp, "secret-" + dexFile.getName()));
            fos.write(encrypt);
            fos.flush();
            fos.close();
            dexFile.delete();
        }

        // 3.把dex放入apk解压目录，重新压成apk文件
        classesDex.renameTo(new File(apkTemp, "classes.dex"));
        File unSignedApk = new File(APP_FILE_DIR + "app-unsigned.apk");
        Zip.zip(apkTemp, unSignedApk);

        // 4.对齐和签名
        // zipalign -v -p 4 my-app-unsigned.apk my-app-unsigned-aligned.apk
        File alignedApk = new File(APP_FILE_DIR + "app-unsigned-aligned.apk");
        process = Runtime.getRuntime().exec(BUILD_TOOLS + CMD_ZIPALIGN
                + SPACE + unSignedApk.getAbsolutePath()
                + SPACE + alignedApk.getAbsolutePath());
        process.waitFor();

        // apksigner sign  --ks jks文件地址 --ks-key-alias 别名 --ks-pass pass:jsk密码 --key-pass pass:别名密码 --out  out.apk in.apk
        File signedApk = new File(APP_FILE_DIR + "app-signed-aligned.apk");
        File jks = new File("encrypt_core/proxy2.jks");
        process = Runtime.getRuntime().exec(BUILD_TOOLS + "apksigner.bat sign --ks " + jks.getAbsolutePath()
                + " --ks-key-alias " + KEY_ALIAS + " --ks-pass pass:" + KEY_PASS + " --key-pass pass:" + KEY_PASS + " --out "
                + signedApk.getAbsolutePath() + " " + alignedApk.getAbsolutePath());
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new RuntimeException("dex error");
        }
        System.out.println("执行成功");
    }
}
