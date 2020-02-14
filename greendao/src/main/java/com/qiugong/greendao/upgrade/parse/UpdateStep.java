package com.qiugong.greendao.upgrade.parse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/14.
 */
class UpdateStep {

    private String versionFrom;
    private String versionTo;
    private List<UpdateDb> updateDb = new ArrayList<>();

    UpdateStep(Element element) {
        versionFrom = element.getAttribute("versionFrom");
        versionTo = element.getAttribute("versionTo");

        NodeList createDbs = element.getElementsByTagName("updateDb");
        for (int i = 0; i < createDbs.getLength(); i++) {
            Element ele = (Element) createDbs.item(i);
            updateDb.add(new UpdateDb(ele));
        }
    }

    public String getVersionFrom() {
        return versionFrom;
    }

    public String getVersionTo() {
        return versionTo;
    }

    public List<UpdateDb> getUpdateDb() {
        return updateDb;
    }

    @Override
    public String toString() {
        return "UpdateStep{" +
                "versionFrom='" + versionFrom + '\'' +
                ", versionTo='" + versionTo + '\'' +
                ", updateDb=" + updateDb +
                '}';
    }
}
