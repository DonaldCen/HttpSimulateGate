package com.xuanwu.ump.HttpSimulateGate.entity.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class RequestHandlers extends HSHttpHelperConfigData {
    public RequestHandlers(Map<String, Object> data) {
        super(data);
    }

    public List<HandlerData> getPreHandlers() {
        List<HandlerData> list = new ArrayList<HandlerData>();
        if(data.containsKey("pre")){
            Object handler = getObject("pre:handler");
            if(handler instanceof Map){
                list.add(new HandlerData((Map)handler));
            }
            else if(handler instanceof List){
                List<Map> preList = (List<Map>) handler;
                if (preList != null) {
                    for (Map map : preList) {
                        list.add(new HandlerData(map));
                    }
                }
            }
        }
        return list;
    }

    public List<HandlerData> getProHandlers() {
        List<HandlerData> list = new ArrayList<HandlerData>();
        if(data.containsKey("pro")){
            Object handler = getObject("pro:handler");
            if(handler instanceof Map){
                list.add(new HandlerData((Map)handler));
            }
            else if(handler instanceof List){
                List<Map> proList = (List<Map>) handler;
                if (proList != null) {
                    for (Map map : proList) {
                        list.add(new HandlerData(map));
                    }
                }
            }
        }
        return list;
    }
}
