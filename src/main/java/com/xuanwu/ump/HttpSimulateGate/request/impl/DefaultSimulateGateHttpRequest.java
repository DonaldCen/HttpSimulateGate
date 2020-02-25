package com.xuanwu.ump.HttpSimulateGate.request.impl;

import com.xuanwu.ump.HttpSimulateGate.common.HttpUtil;
import com.xuanwu.ump.HttpSimulateGate.request.SimulateGateHttpRequest;

import java.util.Map;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/25
 * @Version 1.0.0
 */
public class DefaultSimulateGateHttpRequest extends SimulateGateHttpRequest {
    private FullHttpRequest fullHttpRequest;

    public DefaultSimulateGateHttpRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }
    public DefaultSimulateGateHttpRequest(FullHttpRequest fullHttpRequest,String responseName) {
        this.fullHttpRequest = fullHttpRequest;
        this.responseName = responseName;
    }

    @Override
    public Map<String, Object> addParameters() {
        return HttpUtil.getParameterFromRequest(fullHttpRequest);
    }

    @Override
    public Map<String, String> addHeaders() {
        return HttpUtil.getHeaderFromRequest(fullHttpRequest);
    }

    @Override
    public Map<String, String> addCookies() {
        return HttpUtil.getCookieFromRequest(fullHttpRequest);
    }
}
