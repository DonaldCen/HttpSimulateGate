package com.xuanwu.ump.HttpSimulateGate;

import com.xuanwu.ump.HttpSimulateGate.common.ConfigXmlFileFilter;
import com.xuanwu.ump.HttpSimulateGate.common.XmlUtil;
import com.xuanwu.ump.HttpSimulateGate.request.handler.HandlerFactory;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultInitHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultParameterBuilderHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pro.DefaultResultParseHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HandlerData;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HttpClientConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestConfigData;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestHandlers;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestXmlPath;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultURLBuilderHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultValidationHandlerImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpHelperXmlConfig {
    public static final String CONFIG_XML_PATH = "/HttpSimulateGate-config.xml";
    private static final HSHttpHelperXmlConfig _instance = new HSHttpHelperXmlConfig();

    protected static Log log = LogFactory.getLog(HSHttpHelperXmlConfig.class);

    private Map<String, Object> configData;
    private Map<String, RequestConfigData> requestConfigDataMap;
    private List<RequestConfigData> requestConfigDataList;
    private HttpClientConfig httpClientConfig;
    private RequestHandlers defaultHandlers;
    private List<RequestPreHandler> defaultPreHandlers;
    private List<ResponseProHandler> defaultProHandlers;

    public HSHttpHelperXmlConfig() {}

    public static HSHttpHelperXmlConfig getInstance() throws Exception {
        try {
            if (_instance.configData == null) {
                File xmlFile = new File(XmlUtil.class.getResource(CONFIG_XML_PATH).toURI());
                log.debug(xmlFile.getPath());
                System.out.println(xmlFile.getPath());
                _instance.configData = XmlUtil.xmlToMap(xmlFile);
                _instance.httpClientConfig = new HttpClientConfig((Map) _instance.configData.get("httpclient-config"));
                _instance.defaultHandlers = new RequestHandlers((Map) _instance.configData.get("default-handlers"));
                _instance.requestConfigDataMap = new HashMap<String, RequestConfigData>();
                _instance.requestListInit();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return _instance;
    }
    public Map<String, Object> getConfigData() {
        return configData;
    }

    public HttpClientConfig getHttpClientConfig() {
        return httpClientConfig;
    }

    public String getCharset() {
        return getHttpClientConfig().getHttpCharset();
    }

    public List<ResponseProHandler> getDefaultProHandlers() throws Exception {
        if (defaultProHandlers == null) {
            defaultProHandlers = new ArrayList<ResponseProHandler>();
            List<HandlerData> proHandlerList = defaultHandlers.getProHandlers();
            // 前处理把相同类型整理为列表，支持配置多个相同类型的处理器
            Map<String, List<String>> proHandlerMap = new HashMap<String, List<String>>();
            if (proHandlerList != null) {
                for (HandlerData handlerData : proHandlerList) {
                    String clazz = handlerData.getClazz();
                    String type = handlerData.getType();
                    if (proHandlerMap.containsKey(type)) {
                        List<String> list = proHandlerMap.get(type);
                        list.add(clazz);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(clazz);
                        proHandlerMap.put(type, list);
                    }
                }
            }
            // 若用户没有配置必须的处理器，使用默认的
            String[] proHandlerTypeList = {"parse", "user"};
            for (int i = 0; i < proHandlerTypeList.length; i++) {
                String type = proHandlerTypeList[i];
                // 存在配置：使用配置
                if (proHandlerMap.containsKey(type)) {
                    List<String> list = proHandlerMap.get(type);
                    for (String clazz : list) {
                        defaultProHandlers.add(HandlerFactory.finadHandler(ResponseProHandler.class, clazz));
                    }
                } else {
                    // 没有配置：使用默认
                    switch (i) {
                        case 0: {
                            defaultProHandlers.add(HandlerFactory.finadHandler(ResponseProHandler.class, DefaultResultParseHandlerImpl.class.getName()));
                            break;
                        }
                    }
                }
            }
        }
        return defaultProHandlers;
    }

    public List<RequestPreHandler> getDefaultPreHandlers() throws Exception {
        if (defaultPreHandlers == null) {
            defaultPreHandlers = new ArrayList<RequestPreHandler>();
            List<HandlerData> preHandlerList = defaultHandlers.getPreHandlers();
            // 前处理把相同类型整理为列表，支持配置多个相同类型的处理器
            Map<String, List<String>> preHandlerMap = new HashMap<String, List<String>>();
            if (preHandlerList != null) {
                for (HandlerData handlerData : preHandlerList) {
                    String clazz = handlerData.getClazz();
                    String type = handlerData.getType();
                    if (preHandlerMap.containsKey(type)) {
                        List<String> list = preHandlerMap.get(type);
                        list.add(clazz);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(clazz);
                        preHandlerMap.put(type, list);
                    }
                }
            }
            // 若用户没有配置必须的处理器，使用默认的
            String[] preHandlerTypeList = {"init", "parameter", "url", "validation", "user"};
            for (int i = 0; i < preHandlerTypeList.length; i++) {
                String type = preHandlerTypeList[i];
                // 存在配置：使用配置
                if (preHandlerMap.containsKey(type)) {
                    List<String> list = preHandlerMap.get(type);
                    for (String clazz : list) {
                        defaultPreHandlers.add(HandlerFactory.finadHandler(RequestPreHandler.class, clazz));
                    }
                } else {
                    // 没有配置：使用默认
                    switch (i) {
                        case 0: {
                            defaultPreHandlers.add(HandlerFactory.finadHandler(RequestPreHandler.class, DefaultInitHandlerImpl.class.getName()));
                            break;
                        }
                        case 1: {
                            defaultPreHandlers.add(HandlerFactory.finadHandler(RequestPreHandler.class, DefaultParameterBuilderHandlerImpl.class.getName()));
                            break;
                        }
                        case 2: {
                            defaultPreHandlers.add(HandlerFactory.finadHandler(RequestPreHandler.class, DefaultURLBuilderHandlerImpl.class.getName()));
                            break;
                        }
                        case 3: {
                            defaultPreHandlers.add(HandlerFactory.finadHandler(RequestPreHandler.class, DefaultValidationHandlerImpl.class.getName()));
                            break;
                        }
                    }
                }
            }
        }
        return defaultPreHandlers;
    }

    private void requestListInit() throws URISyntaxException, Exception {
        if (requestConfigDataList == null) {
            requestConfigDataList = new ArrayList<RequestConfigData>();
            if (this.configData.containsKey("requests")) {
                Object obj = ((Map) this.configData.get("requests")).get("request");
                if (obj instanceof List) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
                    if (list != null) {
                        for (Map<String, Object> map : list) {
                            requestConfigDataList.add(new RequestConfigData(map));
                        }
                    }
                } else if (obj instanceof Map) {
                    requestConfigDataList.add(new RequestConfigData((Map) obj));
                }

            }
            if (this.configData.containsKey("request-xml")) {
                List<Map> mapList = new ArrayList<Map>();
                Object obj = ((Map) this.configData.get("request-xml")).get("path");
                if (obj instanceof List) {
                    mapList.addAll((List) obj);
                } else if (obj instanceof Map) {
                    mapList.add((Map) obj);
                }
                List<String> xmlPathList = new ArrayList<String>();
                if (!mapList.isEmpty()) {
                    for (Map map : mapList) {
                        RequestXmlPath pathData = new RequestXmlPath(map);
                        String path = pathData.getPath();
                        if (!StringUtils.isEmpty(path)) {
                            xmlPathList.addAll(parsePath(path));
                        }
                    }
                }
                // 解析xml
                for (String xmlPath : xmlPathList) {
                    File xmlFile = new File(xmlPath);
                    if (xmlFile.exists()) {
                        Object xmlConfigObj = XmlUtil.xmlToMap(xmlFile);
                        if (xmlConfigObj instanceof Map) {
                            Map<String, Object> xmlConfigMap = (Map) xmlConfigObj;
                            if (xmlConfigMap.containsKey("requests")) {
                                Object obj2 = ((Map) xmlConfigMap.get("requests")).get("request");
                                if (obj2 instanceof List) {
                                    List<Map<String, Object>> list = (List<Map<String, Object>>) obj2;
                                    if (list != null) {
                                        for (Map<String, Object> requestMap : list) {
                                            requestConfigDataList.add(new RequestConfigData(requestMap));
                                        }
                                    }
                                } else if (obj2 instanceof Map) {
                                    requestConfigDataList.add(new RequestConfigData((Map) obj2));
                                }

                            }
                        }
                    }
                }
            }
            for (RequestConfigData requestConfigData : requestConfigDataList) {
                requestConfigDataMap.put(requestConfigData.getValue("name"), requestConfigData);
            }
        }
    }

    public RequestConfigData getRequestConfigData(String name) {
        if (requestConfigDataMap.containsKey(name)) {
            return requestConfigDataMap.get(name);
        } else {
            return null;
        }
    }

    public List<RequestConfigData> getRequestConfigDataList() throws Exception, URISyntaxException {
        if (requestConfigDataList == null) {
            requestListInit();
        }
        return requestConfigDataList;
    }

    /**
     * 解析指定路径下所有xml
     */
    private List<String> parsePath(String path) throws URISyntaxException {
        String classpath = HSHttpHelperXmlConfig.class.getResource("/").toURI().getPath();
        File classPathDir = new File(classpath);
        String[] matchs = path.split("/");
        int i = 0;
        if (path.startsWith("/")) {
            i = 1;
        }
        List<String> pathList = new ArrayList<String>();
        listFlies(classPathDir, matchs, 1, pathList);
        return pathList;
    }

    /**
     * 获取符合规则的所有xml
     */
    private void listFlies(File file, String[] matchs, int i, List<String> pathList) {
        if (i > matchs.length - 1) {
            return;
        }
        File[] list = file.listFiles(new ConfigXmlFileFilter(matchs[i]));

        for (File f : list) {
            if (f.isFile()) {
                pathList.add(f.getPath());
            } else if (f.isDirectory()) {
                listFlies(f, matchs, i + 1, pathList);
            }
        }
    }
}