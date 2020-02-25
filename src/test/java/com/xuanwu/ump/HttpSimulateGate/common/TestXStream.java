package com.xuanwu.ump.HttpSimulateGate.common;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.request.RequestConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.response.ResponseConfig;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
public class TestXStream extends TestCase {
    public void testParseEntity() throws Exception {
        XStream xStream = new XStream(new Xpp3DomDriver());
        xStream.processAnnotations(ResponseConfig.class);
        String path = HSHttpHelperXmlConfig.class.getResource(HSHttpHelperXmlConfig.RESPONSE_CONFIG_FILE).toURI().getPath();
        System.out.println(path);
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        ResponseConfig config = (ResponseConfig)xStream.fromXML(stream);
        System.out.println(JSON.toJSONString(config));

    }

    public void testParseRequestEntity() throws Exception {
        XStream xStream = new XStream(new Xpp3DomDriver());
        xStream.processAnnotations(RequestConfig.class);
        String path = HSHttpHelperXmlConfig.class.getResource(HSHttpHelperXmlConfig.REQUEST_CONFIG_FILE).toURI().getPath();
        System.out.println(path);
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        RequestConfig requestConfig = (RequestConfig)xStream.fromXML(stream);
        System.out.println(JSON.toJSONString(requestConfig));

        xStream.toXML(requestConfig);
    }
}
