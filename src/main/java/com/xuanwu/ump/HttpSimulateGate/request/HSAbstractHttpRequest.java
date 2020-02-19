package com.xuanwu.ump.HttpSimulateGate.request;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.common.MapKeyComparator;
import com.xuanwu.ump.HttpSimulateGate.entity.ErrorMessage;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ParameterDefine;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;
import com.xuanwu.ump.HttpSimulateGate.http.HSHttpTaskExecutor;

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
    protected abstract HSRequestContext builderContext() throws Exception;

    /**
     * 初始化。设置默认值。
     *
     * @param context 请求上下文。
     */
    @Override
    public abstract void init(HSRequestContext context) throws Exception;

    /**
     * 执行请求。
     *
     * @return 响应结果。
     */
    @Override
    public ResponseResult execute() throws Exception {
        ResponseResult result = null;
        if (!preRequest()) {
            // 出现错误：获取错误消息并返回
            result = new ResponseResult();
            result.setStatus(999);
            List<ErrorMessage> errorMessageList = context.getErrorMessageList();
            StringBuffer error = new StringBuffer();
            for (ErrorMessage message : errorMessageList) {
                error.append(message);
            }
            result.setBody(error.toString());
            return result;
        }
        // 执行请求
        String uuid = HSHttpTaskExecutor.getInstance().execute(context);
        // 获取执行结果
        result = HSHttpTaskExecutor.getInstance().getResult(uuid);
        // 执行后处理
        Set<Integer> proKeySet = responseProHandlerListMap.keySet();
        // 从小到大以此执行
        for (Integer key : proKeySet) {
            List<ResponseProHandler> list = responseProHandlerListMap.get(key);
            if (list != null) {
                for (ResponseProHandler handler : list) {
                    result = handler.handler(context, result);
                }
            }
        }
        // 清楚缓存
        clear();
        return result;
    }

    /**
     * 异步请求
     */
    @Override
    public void asyncExecute() throws Exception {
        if (!preRequest()) {
            // 出现错误：获取错误消息并返回
            List<ErrorMessage> errorMessageList = context.getErrorMessageList();
            StringBuffer error = new StringBuffer();
            for (ErrorMessage message : errorMessageList) {
                error.append(message);
            }
            throw new Exception(error.toString());
        }
        HSHttpTaskExecutor.getInstance().asyncExecute(context, responseProHandlerListMap);
        requestPreHandlerListMap.clear();
    }

    /**
     * 添加请求前处理器。
     */
    @Override
    public void addRequestPreHandler(RequestPreHandler handler) {
        if (handler == null) {
            return;
        }
        int level = handler.level();
        if (requestPreHandlerListMap.containsKey(level)) {
            List<RequestPreHandler> list = requestPreHandlerListMap.get(level);
            list.add(handler);
        } else {
            List<RequestPreHandler> list = new ArrayList<RequestPreHandler>();
            list.add(handler);
            requestPreHandlerListMap.put(level, list);
        }
    }

    /**
     * 添加请求后处理器，
     */
    @Override
    public void addResponseProHandler(ResponseProHandler handler) {
        if (handler == null) {
            return;
        }
        int level = handler.level();
        if (responseProHandlerListMap.containsKey(level)) {
            List<ResponseProHandler> list = responseProHandlerListMap.get(level);
            list.add(handler);
        } else {
            List<ResponseProHandler> list = new ArrayList<ResponseProHandler>();
            list.add(handler);
            responseProHandlerListMap.put(level, list);
        }
    }

    /**
     * 添加参数<br/>
     *
     * @param name  参数名称。
     * @param value 参数值。支持：String:普通参数;List:数组参数;File:文件参数，上传文件。
     */
    @Override
    public void addParameter(String name, Object value) {
        parameterDefineList.add(new ParameterDefine(name));
        inputData.put(name, value);
    }

    /**
     * 添加请求头部。
     */
    @Override
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    /**
     * 添加Cookie。
     */
    @Override
    public void addCookie(String name, String value) {
        cookieMap.put(name, value);
    }

    /**
     * 获取请求上下文。
     */
    @Override
    public HSRequestContext getContext() throws Exception {
        if (context == null) {
            context = builderContext();
        }
        return context;
    }

    private boolean preRequest() throws Exception {
        // 添加用户手动添加的数据
        addUserData();
        // 初始化
        init(context);
        // 请求处理
        defaultHandlerInit();
        // 执行前处理
        Set<Integer> preKeySet = requestPreHandlerListMap.keySet();
        // 从小到大以此执行
        for (Integer key : preKeySet) {
            List<RequestPreHandler> list = requestPreHandlerListMap.get(key);
            if (list != null) {
                for (RequestPreHandler handler : list) {
                    if (!handler.handler(context)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void addUserData() throws Exception {
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
    private void defaultHandlerInit() throws Exception {
        List<RequestPreHandler> defaultPreHandlers = HSHttpHelperXmlConfig.getInstance().getDefaultPreHandlers();
        for (RequestPreHandler requestPreHandler : defaultPreHandlers) {
            addRequestPreHandler(requestPreHandler);
        }
        List<ResponseProHandler> defaultProHandlers = HSHttpHelperXmlConfig.getInstance().getDefaultProHandlers();
        for (ResponseProHandler responseProHandler : defaultProHandlers) {
            addResponseProHandler(responseProHandler);
        }
    }

    private void clear() {
        this.context.clear();
        requestPreHandlerListMap.clear();
        responseProHandlerListMap.clear();
    }
}
