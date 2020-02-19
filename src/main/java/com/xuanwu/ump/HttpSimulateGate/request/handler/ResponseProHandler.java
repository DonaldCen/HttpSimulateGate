package com.xuanwu.ump.HttpSimulateGate.request.handler;

import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;

/**
 * @Description 响应后处理类
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public interface ResponseProHandler {
    /**
     * 响应后处理
     */
    public ResponseResult handler(HSRequestContext context, ResponseResult result) throws Exception;

    /**
     * 执行级别：小的优先执行
     */
    public int level();
}
