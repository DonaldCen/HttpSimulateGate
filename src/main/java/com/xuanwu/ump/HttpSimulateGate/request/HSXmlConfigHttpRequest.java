package com.xuanwu.ump.HttpSimulateGate.request;

import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.request.handler.HandlerFactory;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HandlerData;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestConfigData;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSXmlConfigHttpRequest extends HSAbstractHttpRequest {
    private RequestConfigData requestConfigData;
    public HSXmlConfigHttpRequest(RequestConfigData data){
        this.requestConfigData=data;
    }

    @Override
    public void init(HSRequestContext context) throws Exception {
        // 前处理
        List<HandlerData> preHandlerList = requestConfigData.getRequestHandlers().getPreHandlers();
        if(preHandlerList!=null){
            try {
                for (HandlerData handlerData : preHandlerList) {
                    String clazz = handlerData.getClazz();
                    if (!StringUtils.isEmpty(clazz)) {
                        addRequestPreHandler(HandlerFactory.finadHandler(RequestPreHandler.class, clazz));
                    }
                }
            }
            catch (Exception e) {
                throw new Exception(e.getMessage(),e);
            }
        }
        // 后处理
        List<HandlerData> proHandlerList = requestConfigData.getRequestHandlers().getProHandlers();
        if(proHandlerList!=null){
            try{
                for(HandlerData handlerData:proHandlerList){
                    String clazz = handlerData.getClazz();
                    if (!StringUtils.isEmpty(clazz)) {
                        addResponseProHandler(HandlerFactory.finadHandler(ResponseProHandler.class, clazz));
                    }
                }
            }
            catch (Exception e){
                throw new Exception(e.getMessage(),e);
            }
        }
    }

    @Override
    protected HSRequestContext builderContext() throws Exception {
        return this.requestConfigData.getContext();
    }
}
