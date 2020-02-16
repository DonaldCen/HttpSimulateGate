package request.handler;

import entity.HttpRequestContext;
import exception.HttpSimulateGateException;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public interface RequestPreHandler {
    /**
     * 请求预处理
     *
     * @param context
     * @return 是否继续运行
     * @throws HttpSimulateGateException
     */
    public boolean handler(HttpRequestContext context) throws HttpSimulateGateException;

    /**
     * 处理级别：小的优先执行
     *
     * @return
     */
    public int level();
}
