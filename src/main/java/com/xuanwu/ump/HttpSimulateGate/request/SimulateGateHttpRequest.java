package com.xuanwu.ump.HttpSimulateGate.request;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;
import com.xuanwu.ump.HttpSimulateGate.common.MapKeyComparator;
import com.xuanwu.ump.HttpSimulateGate.entity.ErrorMessage;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ParameterDefine;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;
import com.xuanwu.ump.HttpSimulateGate.entity.response.Response;
import com.xuanwu.ump.HttpSimulateGate.http.HSHttpTaskExecutor;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/21
 * @Version 1.0.0
 */
public abstract class SimulateGateHttpRequest {
    protected static Log log = LogFactory.getLog(SimulateGateHttpRequest.class);
    protected String responseName;
    /**
     * 前置处理handler
     */
    protected Map<Integer, List<RequestPreHandler>> requestPreHandlerListMap = new TreeMap<Integer, List<RequestPreHandler>>(new MapKeyComparator());
    /**
     * 后置处理handler
     */
    protected Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = new TreeMap<Integer, List<ResponseProHandler>>(new MapKeyComparator());
    /**
     * 上下文
     */
    private HSRequestContext context = null;
    /**
     * 参数列表
     */
    private Map<String,Object> parameterDefineList = null;
    /**
     * cookie列表
     */
    private Map<String, String> cookieMap = null;
    /**
     * 头参数列表
     */
    private Map<String, String> headerMap = null;

    /**
     * 添加参数
     */
    public abstract Map<String,Object> addParameters();

    /**
     * 添加请求头部。
     */
    public abstract Map<String, String> addHeaders();

    /**
     * 添加Cookie
     */
    public abstract Map<String, String> addCookies();

    private void builderContext() throws Exception {
        parameterDefineList = addParameters();
        cookieMap = addCookies();
        headerMap = addHeaders();
        context = new HSRequestContext();

        context.setParameterDefineList(parseParameterFromMap(parameterDefineList));
        context.setCookieMap(cookieMap);
        context.setHeaderMap(headerMap);

        Response response = HSHttpHelperXmlConfig.getInstance().getResponseByName(responseName);
        context.setResponse(response);

    }

    private List<ParameterDefine> parseParameterFromMap(Map<String,Object> paramMap){
        List<ParameterDefine> list = new ArrayList<ParameterDefine>();
        for(String key:paramMap.keySet()){
            ParameterDefine define = new ParameterDefine();
            define.setName(key);
            define.setDefaultValue((String) paramMap.get(key));
            list.add(define);
        }
        return list;
    }

    private boolean preRequest() throws Exception {
        //构建 上下文
        builderContext();
        //初始化默认处理器
        defaultHandlerInit();

        Set<Integer> preKeySet = requestPreHandlerListMap.keySet();
        // 执行前置处理器，处理顺序 从小到大执行
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

    /**
     * 初始化默认处理器
     * 前置处理器，后置处理器初始化
     * 即 加载HttpSimulateGate-config.xml中
     * <default-handlers></default-handlers>中的<per></per> 和 <pro></pro>中的处理器
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

    private void addRequestPreHandler(RequestPreHandler handler) {
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

    private void addResponseProHandler(ResponseProHandler handler) {
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


    public ResponseResult execute() {
        ResponseResult result = null;
        try {
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
            result = new ResponseResult();
            result.setStatus(0);
            result.setBody("success");
            // 执行请求
//            String uuid = HSHttpTaskExecutor.getInstance().execute(context);
//            // 获取执行结果
//            result = HSHttpTaskExecutor.getInstance().getResult(uuid);
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
        } catch (Exception e) {
            log.error("execute error..", e);
        }
        return result;
    }


}
