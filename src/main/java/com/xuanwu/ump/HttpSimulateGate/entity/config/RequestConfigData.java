package com.xuanwu.ump.HttpSimulateGate.entity.config;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;
import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.annotation.Parameter;
import com.xuanwu.ump.HttpSimulateGate.common.ParseWay;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ParameterDefine;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class RequestConfigData extends HSHttpHelperConfigData {
    public RequestConfigData(Map<String, Object> data) {
        super(data);
    }

    public HSRequestContext getContext() throws Exception {
        HSRequestContext context = new HSRequestContext();
        if (!StringUtils.isEmpty(getValue("name"))) {
            context.setName(getValue("name"));
        }
        if (!StringUtils.isEmpty(getValue("url"))) {
            context.setUrl(getValue("url"));
        }
        if (StringUtils.isEmpty(getValue("charset"))) {
            context.setCharset(HSHttpHelperXmlConfig.getInstance(ParseWay.ParseRequest.XML).getHttpClientConfig().getHttpCharset());
        } else {
            context.setCharset(getValue("charset"));
        }
        if (!StringUtils.isEmpty(getValue("description"))) {
            context.setDescription(getValue("description"));
        }
        String responseType = getValue("response-type");
        if (!StringUtils.isEmpty(responseType)) {
            for (HSRequest.ResponseType type : HSRequest.ResponseType.values()) {
                if (type.name().equals(responseType)) {
                    context.setResponseType(type);
                }
            }
        } else {
            context.setResponseType(HSRequest.ResponseType.HTML);
        }
        String method = getValue("method");
        if (!StringUtils.isEmpty(method)) {
            for (HSRequest.MethodType type : HSRequest.MethodType.values()) {
                if (type.name().equals(method)) {
                    context.setMethod(type);
                }
            }
        } else {
            context.setMethod(HSRequest.MethodType.GET);
        }
        if(this.data.containsKey("parameters")){
            Object parameters=  getObject("parameters:parameter");
            if (parameters != null) {
                if(parameters instanceof List){
                    for (Map<String, Object> parameter : (List<Map>)parameters) {
                        ParameterDefine parameterDefine = createParameterDefine(parameter);
                        context.addParameterDefine(parameterDefine);
                    }
                }
                else if(parameters instanceof Map){
                    ParameterDefine parameterDefine = createParameterDefine((Map)parameters);
                    context.addParameterDefine(parameterDefine);
                }
            }
        }
        if(data.containsKey("headers")){
            Object headers =  getObject("headers:header");
            if (headers != null) {
                if(headers instanceof List){
                    for (Map<String, Object> header : (List<Map>)headers) {
                        context.addHeader(getValue(header, "name"), getValue(header, "value"));
                    }
                }
                else if(headers instanceof Map){
                    context.addHeader(getValue((Map)headers, "name"), getValue((Map)headers, "value"));
                }

            }
        }
        return context;
    }

    public RequestHandlers getRequestHandlers(){
        Map<String, Object> handlers = (Map<String, Object>) getObject("handlers");
        if (handlers != null) {
            RequestHandlers requestHandlers = new RequestHandlers(handlers);
            return requestHandlers;
        }
        return new RequestHandlers(new HashMap<String, Object>());
    }


    private ParameterDefine createParameterDefine(Map parameter){
        ParameterDefine parameterDefine = new ParameterDefine();
        parameterDefine.setName(getValue(parameter, "name"));
        parameterDefine.setDescription(getValue(parameter, "description"));
        parameterDefine.setDefaultValue(getValue(parameter, "defaultValue"));
        parameterDefine.setExample(getValue(parameter, "example"));
        parameterDefine.setRequired("true".equals(getValue(parameter, "required")));
        parameterDefine.setValidateRegex(getValue(parameter, "validateRegex"));
        String type = getValue(parameter, "type");
        if (!StringUtils.isEmpty(type)) {
            for (Parameter.Type t : Parameter.Type.values()) {
                if (t.name().equals(type)) {
                    parameterDefine.setType(t);
                }
            }
        } else {
            parameterDefine.setType(Parameter.Type.STRING);
        }
        return parameterDefine;
    }
}
