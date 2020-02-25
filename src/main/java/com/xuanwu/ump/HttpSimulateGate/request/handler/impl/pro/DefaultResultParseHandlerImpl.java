package com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pro;

import com.thoughtworks.xstream.XStream;
import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperConstant;
import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.entity.ContentType;
import com.xuanwu.ump.HttpSimulateGate.entity.response.Response;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.common.JsonUtil;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class DefaultResultParseHandlerImpl implements ResponseProHandler {
    /**
     * 响应后处理
     */
    @Override
    public ResponseResult handler(HSRequestContext context, ResponseResult result) throws Exception {
        String contentType = context.getHeaderMap().get(HSHttpHelperConstant.CONTENT_TYPE);
        result.setResponseType(ContentType.getContentTypeByName(contentType));

        Response response = context.getResponse();
        Class<?> resultClass = null;
        if(response != null && StringUtils.isNotBlank(response.getClazz())){
           resultClass = Class.forName(response.getClazz());
        }
        String resultBody = result.getBody().toString();
        // 解析结果,只解析JSON
        if (contentType.equalsIgnoreCase(ContentType.JSON.getContentType())) {
            if (resultClass != null && resultClass != String.class) {
//                Object resultObj = JsonUtil.fromJson(resultBody, resultClass);
                result.setBody(resultBody);
            }
        }else if(contentType.equalsIgnoreCase(ContentType.XML.getContentType())){
            if (resultClass != null && resultClass != String.class) {
                XStream xstream = new XStream();
                String xml = xstream.toXML(resultBody);
                result.setBody(xml);
            }
        }
        return result;
    }

    /**
     * 执行级别：小的优先执行
     */
    @Override
    public int level() {
        return HSHttpHelperConstant.PRO_HANDLER_PARSE;
    }
}
