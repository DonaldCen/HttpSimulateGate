package com.xuanwu.ump.http;

import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.request.handler.ResponseProHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpAsyncClient extends HSHttpAbstractClient implements Runnable {

    private Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = null;

    public HSHttpAsyncClient(HSRequestContext context) {
        super(context);
    }

    public HSHttpAsyncClient(HSRequestContext context, Map<Integer, List<ResponseProHandler>> responseProHandlerListMap) {
        super(context);
        this.responseProHandlerListMap = responseProHandlerListMap;
    }

    @Override
    public void run() {
        try {
            ResponseResult result = super.doRequest();
            if (responseProHandlerListMap != null) {
                // 执行后处理
                Set<Integer> proKeySet = responseProHandlerListMap.keySet();
                // 从小到大以此执行
                for (Integer key : proKeySet) {
                    List<ResponseProHandler> list = responseProHandlerListMap.get(key);
                    if (list != null) {
                        for (ResponseProHandler handler : list) {
                            handler.handler(context, result);
                        }
                    }
                }
            }
            this.context.clear();
            responseProHandlerListMap.clear();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
