package com.xuanwu.ump.HttpSimulateGate.common;

/**
 * @Description request 和 response 解析方式
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/21
 * @Version 1.0.0
 */
public class ParseWay {
    //解析request参数方式
    public enum ParseRequest {
        XML,
        JSON
    }

    //解析response参数方式
    enum ParseResponse {
        XML
    }
}
