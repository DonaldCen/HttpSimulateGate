package com.xuanwu.ump.HttpSimulateGate.common;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;

import junit.framework.TestCase;

import java.io.File;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class TestXmlToMapUtil extends TestCase {
    public void testXmlToMap() throws Exception {
        File xmlFile = new File(XmlUtil.class.getResource(HSHttpHelperXmlConfig.REQUEST_CONFIG_FILE).toURI());
        Map map = XmlUtil.xmlToMap(xmlFile);
        System.out.print(map);
    }
}
