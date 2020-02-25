package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 */
@XStreamAlias("request-xml")
public class RequestXmlConfig {

    @XStreamImplicit(itemFieldName = "path")
    private List<PathConfig> path;

    public List<PathConfig> getPath() {
        return path;
    }

    public void setPath(List<PathConfig> path) {
        this.path = path;
    }
}
