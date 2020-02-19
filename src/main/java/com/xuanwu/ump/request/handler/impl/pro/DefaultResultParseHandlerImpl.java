package com.xuanwu.ump.request.handler.impl.pro;

import com.xuanwu.ump.HSHttpHelperConstant;
import com.xuanwu.ump.annotation.HSRequest;
import com.xuanwu.ump.common.JsonUtil;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.request.handler.ResponseProHandler;

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
