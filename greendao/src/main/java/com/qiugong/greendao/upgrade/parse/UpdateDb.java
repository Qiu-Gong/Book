package com.qiugong.greendao.upgrade.parse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/14.
 */
class UpdateDb {

    private String name;
    private List<String> sqlBefore = new ArrayList<>();
    private List<String> sqlAfter = new ArrayList<>();

    UpdateDb(Element element) {
        name = element.getAttribute("name");

        NodeList sqlBefore = element.getElementsByTagName("sql_before");
        for (int i = 0; i < sqlBefore.getLength(); i++) {
            String textContent = sqlBefore.item(i).getTextContent();
            this.sqlBefore.add(textContent);
        }

        NodeList sqlAfters = element.getElementsByTagName("sql_after");
        for (int i = 0; i < sqlAfters.getLength(); i++) {
            String textContent = sqlAfters.item(i).getTextContent();
            this.sqlAfter.add(textContent);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getSqlBefore() {
        return sqlBefore;
    }

    public List<String> getSqlAfter() {
        return sqlAfter;
    }

    @Override
    public String toString() {
        return "UpdateDb{" +
                "name='" + name + '\'' +
                ", sqlBefore=" + sqlBefore +
                ", sqlAfter=" + sqlAfter +
                '}';
    }
}
