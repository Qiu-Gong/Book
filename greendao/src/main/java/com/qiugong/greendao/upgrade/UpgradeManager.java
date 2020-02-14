package com.qiugong.greendao.upgrade;

import android.content.Context;
import android.util.Log;

import com.qiugong.greendao.bean.Login;
import com.qiugong.greendao.db.BaseDaoFactory;
import com.qiugong.greendao.db.IBaseDao;
import com.qiugong.greendao.sub_sqlite.LoginDao;
import com.qiugong.greendao.upgrade.parse.CreateDb;
import com.qiugong.greendao.upgrade.parse.CreateVersion;
import com.qiugong.greendao.upgrade.parse.UpgradeXml;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author qzx 20/2/13.
 */
public class UpgradeManager {

    private static final String TAG = "UpgradeManager";

    private List<Login> loginList;

    public void checkVersion(Context context) {
        IBaseDao<Login> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(LoginDao.class, Login.class);
        loginList = baseDao.query(new Login());

        // 读取 xml 文件信息
        UpgradeXml xml = readUpgradeXml(context);
        Log.d(TAG, "checkVersion: " + xml.toString());

        // 读取 version 版本
        VersionInfo info = new VersionInfo();
        info.parseVersionTable();

        CreateVersion createVersion = CreateVersion.
                analyzeCreateVersion(xml, info.getCurrentVersionString());
        executeCreateVersion(createVersion);
    }

    private void executeCreateVersion(CreateVersion createVersion) {
        for (CreateDb createDb : createVersion.getCreateDb()) {
            List<String> sqlCreate = createDb.getSqlCreateTable();
            if (loginList != null && !loginList.isEmpty()) {
                for (int i = 0; i < loginList.size(); i++) {

                }
            }
        }
    }

    public void startUpgradeDb(Context context) {

    }

    private UpgradeXml readUpgradeXml(Context context) {
        InputStream inputStream = null;
        Document document = null;

        try {
            inputStream = context.getAssets().open("updateXml.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (document == null) return null;
        return new UpgradeXml(document);
    }
}
