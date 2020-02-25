package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.xuanwu.ump.HttpSimulateGate.common.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
@XStreamAlias("default-handlers")
public class DefaultHandlers {
//    @XStreamImplicit(itemFieldName = "pre")
    private PreHandlerConfig pre;
//    @XStreamImplicit(itemFieldName = "pro")
    private ProHandlerConfig pro;

    public PreHandlerConfig getPre() {
        return pre;
    }

    public void setPre(PreHandlerConfig pre) {
        this.pre = pre;
    }

    public ProHandlerConfig getPro() {
        return pro;
    }

    public void setPro(ProHandlerConfig pro) {
        this.pro = pro;
    }

    public List<HandlerConfig> getProHandlerData(DefaultHandlers defaultHandlers){
        List<HandlerConfig> list = new ArrayList<HandlerConfig>();
        if(defaultHandlers != null
                && defaultHandlers.getPro() != null
                && ListUtil.isNotBlank(defaultHandlers.getPro().getHandler())){
            return defaultHandlers.getPro().getHandler();
        }
        return list;
    }

    public List<HandlerConfig> getPreHandlerData(DefaultHandlers defaultHandlers){
        List<HandlerConfig> list = new ArrayList<HandlerConfig>();
        if(defaultHandlers != null
                && defaultHandlers.getPre() != null
                && ListUtil.isNotBlank(defaultHandlers.getPre().getHandler())){
            return defaultHandlers.getPre().getHandler();
        }
        return list;
    }
}
