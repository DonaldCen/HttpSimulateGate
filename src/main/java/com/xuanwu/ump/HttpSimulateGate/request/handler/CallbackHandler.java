package com.xuanwu.ump.HttpSimulateGate.request.handler;

import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public interface CallbackHandler {
    public ResponseResult execute(ResponseResult result) throws Exception;
}
