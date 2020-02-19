package com.xuanwu.ump;

import junit.framework.TestCase;

import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class TestHSHttpHelper extends TestCase {

    /**
     * 测试获取JSON，解析为MAP
     * @throws Exception
     */
    public void testDoGetMap()throws Exception{
        String url="https://www.hao123.com/sugdata_s4.json?r=-805836";
        Map resultMap = HSHttpHelper.doGetMap(url);
        System.out.println(resultMap);
    }
}
