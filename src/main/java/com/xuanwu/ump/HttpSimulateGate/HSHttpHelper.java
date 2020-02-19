package com.xuanwu.ump.HttpSimulateGate;

import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.request.HSHttpRequest;
import com.xuanwu.ump.HttpSimulateGate.request.handler.CallbackHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;
import com.xuanwu.ump.HttpSimulateGate.request.impl.DefaultGetRequest;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpHelper {
    public static Map<?, ?> doGetMap(String url) throws Exception {
        return doGetMap(url, null, null, null, null);
    }
    public static Map<?, ?> doGetMap(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws Exception {
        return doGetJson(url, parameters, charset, headers, cookies, Map.class);
    }
    public static <T> T doGetJson(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, Class<T> resultClazz) throws Exception {
        DefaultGetRequest request = new DefaultGetRequest();
        return doExecute(request, url, parameters, charset, headers, cookies, resultClazz);
    }

    /**
     * 执行自动解析的请求
     *
     * @param request
     * @param url
     * @param parameters
     * @param headers
     * @param cookies
     * @param resultClazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T doExecute(HSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, Class<T> resultClazz) throws Exception {
        if (resultClazz == null) {
            throw new Exception("该接口必须传入resultClazz！");
        }
        buildRequest(request, url, parameters, charset, headers, cookies, null);
        request.getContext().setResultClass(resultClazz);
        request.getContext().setResponseType(HSRequest.ResponseType.JSON);
        return request.execute().getBody(resultClazz);
    }

    private static void buildRequest(HSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws Exception {
        request.getContext().setUrl(url);
        if (!StringUtils.isEmpty(charset)) {
            request.getContext().setCharset(charset);
        }
        if (parameters != null) {
            Set<String> keySet = parameters.keySet();
            for (String key : keySet) {
                request.addParameter(key, parameters.get(key));
            }
        }
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            boolean isUserAgent = false;
            for (String key : keySet) {
                request.addHeader(key, headers.get(key));
                if ("user-agent".equals(key.toLowerCase())) {
                    isUserAgent = true;
                }
            }
            if (!isUserAgent) {
                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
            }
        } else {
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
        }
        if (cookies != null) {
            Set<String> keySet = cookies.keySet();
            for (String key : keySet) {
                request.addCookie(key, cookies.get(key));
            }
        }
        if (callback != null) {
            request.addResponseProHandler(new ResponseProHandler() {
                @Override
                public ResponseResult handler(HSRequestContext context, ResponseResult result) throws Exception {
                    return callback.execute(result);
                }

                @Override
                public int level() {
                    return HSHttpHelperConstant.PRO_HANDLER_USER;
                }
            });
        }
    }
}
