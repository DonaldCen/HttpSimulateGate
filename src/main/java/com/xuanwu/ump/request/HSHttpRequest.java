package com.xuanwu.ump.request;

import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.exception.HSException;
import com.xuanwu.ump.request.handler.RequestPreHandler;
import com.xuanwu.ump.request.handler.ResponseProHandler;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public interface HSHttpRequest {
    /**
     * 初始化。设置默认值。
     *
     * @param context 请求上下文。
     */
    public void init(HSRequestContext context) throws HSException;

    /**
     * 执行请求。
     *
     * @return 响应结果。
     */
    public ResponseResult execute() throws HSException;

    /**
     * 异步请求
     */
    public void asyncExecute() throws HSException;

    /**
     * 添加请求前处理器。
     */
    public void addRequestPreHandler(RequestPreHandler handler);

    /**
     * 添加请求后处理器，
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
     */
    public void addHeader(String name, String value);

    /**
     * 添加Cookie。
     */
    public void addCookie(String name, String value);

    /**
     * 获取请求上下文。
     */
    public HSRequestContext getContext() throws HSException;
}
