package com.xuanwu.ump.HttpSimulateGate.entity.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
@XStreamAlias("header")
public class Header {
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
