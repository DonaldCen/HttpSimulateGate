package com.xuanwu.ump.HttpSimulateGate.entity.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.xuanwu.ump.HttpSimulateGate.entity.XmlConfig;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */

@XStreamAlias("response-config")
public class ResponseConfig extends XmlConfig {
    @XStreamImplicit(itemFieldName="responses")
    private List<Responses> responses;

    public List<Responses> getResponses() {
        return responses;
    }

    public void setResponses(List<Responses> responses) {
        this.responses = responses;
    }

    @Override
    public Type getType() {
        return Type.RESPONSE;
    }
}
