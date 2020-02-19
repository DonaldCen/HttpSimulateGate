package com.xuanwu.ump.HttpSimulateGate.request.impl;

import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.request.HSAnnotationHttpRequest;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
@HSRequest(
        name = "默认GET请求", url = "{url}", method = HSRequest.MethodType.GET,contentType = HSRequest.ContentType.JSON
)
public class DefaultGetRequest extends HSAnnotationHttpRequest {
    @Override
    public void init(HSRequestContext context) throws Exception {

    }
}
