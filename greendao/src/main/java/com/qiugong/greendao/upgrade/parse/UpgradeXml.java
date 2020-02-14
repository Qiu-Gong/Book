package com.qiugong.greendao.upgrade.parse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/13.
 */
public class UpgradeXml {

    private List<UpdateStep> updateStep = new ArrayList<>();
    private List<CreateVersion> createVersion = new ArrayList<>();

    public UpgradeXml(Document document) {
        NodeList updateSteps = document.getElementsByTagName("updateStep");
        for (int i = 0; i < updateSteps.getLength(); i++) {
            Element ele = (Element) updateSteps.item(i);
            updateStep.add(new UpdateStep(ele));
        }

        NodeList createVersions = document.getElementsByTagName("createVersion");
        for (int i = 0; i < createVersions.getLength(); i++) {
            Element ele = (Element) createVersions.item(i);
            createVersion.add(new CreateVersion(ele));
        }
    }

    public List<UpdateStep> getUpdateStep() {
        return updateStep;
    }

    public List<CreateVersion> getCreateVersion() {
        return createVersion;
    }

    @Override
    public String toString() {
        return "UpgradeXml{" +
                "updateStep=" + updateStep +
                ", createVersion=" + createVersion +
                '}';
    }
}
