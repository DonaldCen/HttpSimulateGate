package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
@XStreamAlias("pre")
public class PreHandlerConfig {
    @XStreamImplicit(itemFieldName="handler")
    private List<HandlerConfig> handler;

    public List<HandlerConfig> getHandler() {
        return handler;
    }

    public void setHandler(List<HandlerConfig> handler) {
        this.handler = handler;
    }
}
