package com.xuanwu.ump.HttpSimulateGate.common;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class ConfigXmlFileFilter implements FilenameFilter {
    private String matchPathName;

    public ConfigXmlFileFilter(String matchPathName) {
        super();
        this.matchPathName = matchPathName.replace("*", "[^/]+");
    }

    @Override
    public boolean accept(File file, String name) {
        boolean result = name.matches(this.matchPathName);
        if (result && file.isFile()) {
            result = result && name.endsWith(".xml");
        }
        return result;
    }
}
