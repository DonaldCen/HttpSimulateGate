package com.xuanwu.ump.request.impl;

import com.xuanwu.ump.annotation.HSRequest;
import com.xuanwu.ump.annotation.HSRequest.ContentType;
import com.xuanwu.ump.annotation.HSRequest.MethodType;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.request.HSAnnotationHttpRequest;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
@HSRequest(
        name = "默认GET请求", url = "{url}", method = MethodType.GET,contentType = ContentType.JSON
)
public class DefaultGetRequest extends HSAnnotationHttpRequest {
    @Override
    public void init(HSRequestContext context) throws Exception {

    }
}
