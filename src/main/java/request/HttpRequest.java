package request;

import entity.HttpRequestContext;
import entity.ResponseResult;
import exception.HttpSimulateGateException;
import request.handler.RequestPreHandler;
import request.handler.ResponseProHandler;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public interface HttpRequest {
    /**
     * 初始化。设置默认值。
     *
     * @param context 请求上下文。
     * @throws HttpSimulateGateException
     */
    public void init(HttpRequestContext context) throws HttpSimulateGateException;

    /**
     * 执行请求。
     *
     * @return 响应结果。
     * @throws HttpSimulateGateException
     */
    public ResponseResult execute() throws HttpSimulateGateException;

    /**
     * 异步请求
     *
     * @throws HttpSimulateGateException
     */
    public void asyncExecute() throws HttpSimulateGateException;

    /**
     * 添加请求前处理器。
     *
     * @param handler
     */
    public void addRequestPreHandler(RequestPreHandler handler);

    /**
     * 添加请求后处理器，
     *
     * @param handler
     */
    public void addResponseProHandler(ResponseProHandler handler);

    /**
     * 添加参数<br/>
     *
     * @param name  参数名称。
     * @param value 参数值。支持：String:普通参数;List:数组参数;File:文件参数，上传文件。
     */
    public void addParameter(String name, Object value);

    /**
     * 添加请求头部。
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value);

    /**
     * 添加Cookie。
     *
     * @param name
     * @param value
     */
    public void addCookie(String name, String value);

    /**
     * 获取请求上下文。
     *
     * @return
     */
    public HttpRequestContext getContext() throws HttpSimulateGateException;
}
