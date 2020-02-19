package com.xuanwu.ump.common;

import com.xuanwu.ump.HSHttpHelperXmlConfig;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class TestConfigXmlFileFilter extends TestCase {
    public void testFilterPath() throws Exception {
        String path = HSHttpHelperXmlConfig.class.getResource("/").toURI().getPath();
        System.out.println(path);
        File file = new File(path);
        String mapping = "test-request/**";
        String[] matchs = mapping.split("/");

        List<String> pathList = new ArrayList<String>();
        listFlies(file, matchs, 0, pathList);

        for (String s : pathList) {
            System.out.println(s);
        }
    }

    public void listFlies(File file, String[] matchs, int i, List<String> pathList) {
        if (i > matchs.length - 1) {
            return;
        }
        File[] list = file.listFiles(new ConfigXmlFileFilter(matchs[i]));

        for (File f : list) {
            if (f.isFile()) {
                pathList.add(f.getPath());
            } else if (f.isDirectory()) {
                listFlies(f, matchs, i + 1, pathList);
            }
        }
    }
}
