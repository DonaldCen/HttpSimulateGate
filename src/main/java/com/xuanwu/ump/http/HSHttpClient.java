package com.xuanwu.ump.http;

import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;

import java.util.concurrent.Callable;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpClient extends HSHttpAbstractClient implements Callable<Object> {

    public HSHttpClient(HSRequestContext context) {
        super(context);
    }

    @Override
    public ResponseResult call() throws Exception {
        return super.doRequest();
    }
}
