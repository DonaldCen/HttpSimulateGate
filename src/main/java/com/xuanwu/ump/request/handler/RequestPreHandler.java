package com.xuanwu.ump.request.handler;

import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.exception.HSException;

/**
 * @Description 请求预处理类
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public interface RequestPreHandler {
    /**
     * 请求预处理
     *
     * @return 是否继续运行
     */
    public boolean handler(HSRequestContext context) throws HSException;

    /**
     * 处理级别：小的优先执行
     */
    public int level();
}
