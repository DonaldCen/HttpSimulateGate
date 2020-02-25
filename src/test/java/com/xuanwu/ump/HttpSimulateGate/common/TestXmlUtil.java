package com.xuanwu.ump.HttpSimulateGate.common;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class TestXmlUtil extends TestCase {
    public void testHttpHelperConfig() throws Exception {
        File xmlFile = new File(XmlUtil.class.getResource("/request-config.xml").toURI());
        String xml = FileUtils.readFileToString(xmlFile);
        Map map = XmlUtil.xmlToMap(xml);
        Map clientMap = (Map) map.get("httpclient-config");

        System.out.print(((Map) clientMap.get("http")).get("connection-timeout"));
    }
}
