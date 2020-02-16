package request.handler;

import entity.HttpRequestContext;
import entity.ResponseResult;
import exception.HttpSimulateGateException;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public interface ResponseProHandler {
    /**
     * 响应后处理
     *
     * @param context
     * @param result
     * @return
     * @throws HttpSimulateGateException
     */
    public ResponseResult handler(HttpRequestContext context, ResponseResult result) throws HttpSimulateGateException;

    /**
     * 执行级别：小的优先执行
     *
     * @return
     */
    public int level();
}
