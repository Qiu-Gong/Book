package com.qiugong.greendao.upgrade.parse;

import android.text.TextUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/14.
 */
public class CreateVersion {

    private String version;
    private List<CreateDb> createDb = new ArrayList<>();

    CreateVersion(Element element) {
        version = element.getAttribute("version");

        NodeList createDbs = element.getElementsByTagName("createDb");
        for (int i = 0; i < createDbs.getLength(); i++) {
            Element ele = (Element) createDbs.item(i);
            createDb.add(new CreateDb(ele));
        }
    }

    public static CreateVersion analyzeCreateVersion(UpgradeXml xml, String version) {
        if (xml == null || TextUtils.isEmpty(version)) {
            return null;
        }

        List<CreateVersion> createVersions = xml.getCreateVersion();
        if (createVersions != null) {
            for (CreateVersion createVersion : createVersions) {
                if (createVersion.version.equals(version)) {
                    return createVersion;
                }
            }
        }
        return null;
    }

    public String getVersion() {
        return version;
    }

    public List<CreateDb> getCreateDb() {
        return createDb;
    }

    @Override
    public String toString() {
        return "CreateVersion{" +
                "version='" + version + '\'' +
                ", createDb=" + createDb +
                '}';
    }
}
