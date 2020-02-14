package com.qiugong.greendao.upgrade.parse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/14.
 */
public class CreateDb {

    private String name;
    private List<String> sqlCreateTable = new ArrayList<>();

    CreateDb(Element element) {
        name = element.getAttribute("name");
        NodeList sqlCreateTables = element.getElementsByTagName("sql_createTable");
        for (int i = 0; i < sqlCreateTables.getLength(); i++) {
            String textContent = sqlCreateTables.item(i).getTextContent();
            sqlCreateTable.add(textContent);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getSqlCreateTable() {
        return sqlCreateTable;
    }

    @Override
    public String toString() {
        return "CreateDb{" +
                "name='" + name + '\'' +
                ", sqlCreateTable=" + sqlCreateTable +
                '}';
    }
}
