package com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pro;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperConstant;
import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.common.JsonUtil;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;

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
        // 解析结果,只解析JSON
        if (context.getResponseType() == HSRequest.ResponseType.JSON) {
            String json = result.getBody().toString();
            Class<?> resultClass = context.getResultClass();
            if (resultClass != null && resultClass != String.class) {
                Object resultObj = JsonUtil.fromJson(json, resultClass);
                result.setBody(resultObj);
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
