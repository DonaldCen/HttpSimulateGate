package com.xuanwu.ump.HttpSimulateGate.common;

import com.alibaba.fastjson.JSONObject;
import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperConstant;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/25
 * @Version 1.0.0
 */
public class HttpUtil {

    /**
     * 获取 头信息
     * @param fullHttpRequest
     * @return
     */
    public static Map<String, String> getHeaderFromRequest(FullHttpRequest fullHttpRequest){
        Map<String, String> params = new HashMap<String, String>();
        HttpHeaders header = fullHttpRequest.headers();
        List<Map.Entry<String, String>> headers = header.entries();
        for(Map.Entry<String, String> entry:headers){
            params.put(entry.getKey(),entry.getValue());
        }
        if(!params.containsKey(HSHttpHelperConstant.CONTENT_TYPE)){
            params.put(HSHttpHelperConstant.CONTENT_TYPE,HSHttpHelperConstant.DEFAULT_CONTENT_TYPE);
        }
        return params;
    }

    public static Map<String, String> getCookieFromRequest(FullHttpRequest fullHttpRequest){
        Map<String, String> params = new HashMap<String, String>();
        HttpHeaders header = fullHttpRequest.headers();
        Set<Cookie> cookies;
        String value = fullHttpRequest.headers().get(HttpHeaders.Names.COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
            cookies = CookieDecoder.decode(value);
        }
        for(Cookie cookie:cookies){
            params.put(cookie.name(),cookie.value());
        }
        return params;
    }

    /**
     * 转换参数
     * @param fullHttpRequest
     * @return
     */
    public static Map<String, Object> getParameterFromRequest(FullHttpRequest fullHttpRequest){
        Map<String, Object> params = new HashMap<String, Object>();
        if (fullHttpRequest.method() == HttpMethod.GET) {
            params = getGetParamsFromChannel(fullHttpRequest);
        } else if (fullHttpRequest.method() == HttpMethod.POST) {
            params = getPostParamsFromChannel(fullHttpRequest);
        }
        return params;
    }

    /*
     * 获取GET方式传递的参数
     */
    private static Map<String, Object> getGetParamsFromChannel(FullHttpRequest fullHttpRequest) {

        Map<String, Object> params = new HashMap<String, Object>();

        // 处理get请求
        QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
        Map<String, List<String>> paramList = decoder.parameters();
        for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
            params.put(entry.getKey(), entry.getValue().get(0));
        }
        return params;
    }


    /*
     * 获取POST方式传递的参数
     */
    private static Map<String, Object> getPostParamsFromChannel(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();

        // 处理POST请求
        String strContentType = fullHttpRequest.headers().get("Content-Type").trim();
        if (strContentType.contains("x-www-form-urlencoded")) {
            params  = getFormParams(fullHttpRequest);
        } else if (strContentType.contains("application/json")) {
            try {
                params = getJSONParams(fullHttpRequest);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return params;
    }

    /*
     * 解析from表单数据（Content-Type = x-www-form-urlencoded）
     */
    private static Map<String, Object> getFormParams(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();

        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
        List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();

        for (InterfaceHttpData data : postData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }

        return params;
    }

    /*
     * 解析json数据（Content-Type = application/json）
     */
    private static Map<String, Object> getJSONParams(FullHttpRequest fullHttpRequest) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<String, Object>();

        ByteBuf content = fullHttpRequest.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, "UTF-8");
        JSONObject  jsonObject = JSONObject.parseObject(strContent);
        params = (Map<String,Object>)jsonObject;
        return params;
    }
}
