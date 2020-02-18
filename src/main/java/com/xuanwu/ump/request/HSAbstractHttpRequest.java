package com.xuanwu.ump.request;

import com.xuanwu.ump.common.MapKeyComparator;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ParameterDefine;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.exception.HSException;
import com.xuanwu.ump.request.handler.RequestPreHandler;
import com.xuanwu.ump.request.handler.ResponseProHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public abstract class HSAbstractHttpRequest implements HSHttpRequest {
    protected static Log log = LogFactory.getLog(HSAbstractHttpRequest.class);
    protected Map<Integer, List<RequestPreHandler>> requestPreHandlerListMap = new TreeMap<Integer, List<RequestPreHandler>>(new MapKeyComparator());
    protected Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = new TreeMap<Integer, List<ResponseProHandler>>(new MapKeyComparator());
    private HSRequestContext context = null;
    private Map<String, String> headerMap = new HashMap<String, String>();
    private Map<String, String> cookieMap = new HashMap<String, String>();
    private Map<String, Object> inputData = new HashMap<String, Object>();
    private List<ParameterDefine> parameterDefineList = new ArrayList<ParameterDefine>();

    /**
     * 构建上下文
     */
    protected abstract HSRequestContext builderContext() throws HSException;

    /**
     * 初始化。设置默认值。
     *
     * @param context 请求上下文。
     */
    @Override
    public abstract void init(HSRequestContext context) throws HSException;

    /**
     * 执行请求。
     *
     * @return 响应结果。
     */
    @Override
    public ResponseResult execute() throws HSException {
        ResponseResult result = null;

        return null;
    }

    /**
     * 异步请求
     */
    @Override
    public void asyncExecute() throws HSException {

    }

    /**
     * 添加请求前处理器。
     */
    @Override
    public void addRequestPreHandler(RequestPreHandler handler) {

    }

    /**
     * 添加请求后处理器，
     */
    @Override
    public void addResponseProHandler(ResponseProHandler handler) {

    }

    /**
     * 添加参数<br/>
     *
     * @param name  参数名称。
     * @param value 参数值。支持：String:普通参数;List:数组参数;File:文件参数，上传文件。
     */
    @Override
    public void addParameter(String name, Object value) {

    }

    /**
     * 添加请求头部。
     */
    @Override
    public void addHeader(String name, String value) {

    }

    /**
     * 添加Cookie。
     */
    @Override
    public void addCookie(String name, String value) {

    }

    /**
     * 获取请求上下文。
     */
    @Override
    public HSRequestContext getContext() throws HSException {
        return null;
    }

    private boolean preRequest() throws HSException {
        // 添加用户手动添加的数据
        addUserData();
        // 初始化
        init(context);

        return false;
    }

    private void addUserData() throws HSException {
        if (context == null) {
            context = builderContext();
        }
        if (!headerMap.isEmpty()) {
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                context.addHeader(key, headerMap.get(key));
            }
        }
        if (!cookieMap.isEmpty()) {
            Set<String> keySet = cookieMap.keySet();
            for (String key : keySet) {
                context.addCookie(key, cookieMap.get(key));
            }
        }
        if (!parameterDefineList.isEmpty()) {
            for (ParameterDefine parameterDefine : parameterDefineList) {
                context.addParameterDefine(parameterDefine);
                String name = parameterDefine.getName();
                if (inputData.containsKey(name)) {
                    context.addInputData(name, inputData.get(name));
                }
            }
        }
    }

    /**
     * 初始化默认处理器
     */
    private void defaultHandlerInit() throws HSException {

    }
}
