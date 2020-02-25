package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 */
@XStreamAlias("requests")
public class RequestsTagConfig {

    @XStreamImplicit(itemFieldName = "request")
    private List<SubRequestConfig> request;

    public List<SubRequestConfig> getRequest() {
        return request;
    }

    public void setRequest(List<SubRequestConfig> request) {
        this.request = request;
    }
}
