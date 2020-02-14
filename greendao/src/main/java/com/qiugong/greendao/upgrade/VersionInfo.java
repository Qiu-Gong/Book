package com.qiugong.greendao.upgrade;

import com.qiugong.greendao.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * V003/V002/V001
 *
 * @author qzx 20/2/14.
 */
public class VersionInfo {

    private String currentVersionString = null;
    private int currentVersionInt = 0;

    public void parseVersionTable() {
        try {
            String content = readFile(Constants.DATABASE_UPGRADE_VERSION);
            String[] versions = content.split("/");
            if (versions.length != 0) {
                currentVersionString = versions[0];
                currentVersionInt = Integer.valueOf(currentVersionString.substring(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        return builder.toString();
    }

    public void writeFile(String path, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(content);
        bw.flush();
        bw.close();
    }

    public String getCurrentVersionString() {
        return currentVersionString;
    }

    public int getCurrentVersionInt() {
        return currentVersionInt;
    }
}
