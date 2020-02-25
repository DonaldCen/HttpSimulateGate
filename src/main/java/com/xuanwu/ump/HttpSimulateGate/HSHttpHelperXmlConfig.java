package com.xuanwu.ump.HttpSimulateGate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.xuanwu.ump.HttpSimulateGate.common.ConfigXmlFileFilter;
import com.xuanwu.ump.HttpSimulateGate.common.ListUtil;
import com.xuanwu.ump.HttpSimulateGate.common.ParseWay.ParseRequest;
import com.xuanwu.ump.HttpSimulateGate.common.XmlUtil;
import com.xuanwu.ump.HttpSimulateGate.entity.XmlConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.XmlConfig.Type;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HandlerData;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HttpClientConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestConfigData;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestHandlers;
import com.xuanwu.ump.HttpSimulateGate.entity.config.RequestXmlPath;
import com.xuanwu.ump.HttpSimulateGate.entity.request.DefaultHandlers;
import com.xuanwu.ump.HttpSimulateGate.entity.request.HandlerConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.request.RequestConfig;
import com.xuanwu.ump.HttpSimulateGate.entity.request.RequestConfigHelper;
import com.xuanwu.ump.HttpSimulateGate.entity.response.ResponseConfig;
import com.xuanwu.ump.HttpSimulateGate.request.handler.HandlerFactory;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.ResponseProHandler;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultInitHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultParameterBuilderHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultURLBuilderHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre.DefaultValidationHandlerImpl;
import com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pro.DefaultResultParseHandlerImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public static final String REQUEST_CONFIG_FILE = "/request-config.xml";
    public static final String RESPONSE_CONFIG_FILE = "/response-config.xml";
    private static final HSHttpHelperXmlConfig _instance = new HSHttpHelperXmlConfig();
    protected static Log log = LogFactory.getLog(HSHttpHelperXmlConfig.class);

    //request-config 存放数据 map
//    private Map<String, Object> requestConfigData;
    private RequestConfig requestConfig;
    private RequestConfigHelper requestHelper;
    private ResponseConfig responseConfig;

    private List<RequestPreHandler> defaultPreHandlers;
    private List<ResponseProHandler> defaultProHandlers;

    public HSHttpHelperXmlConfig() {
    }


    public static HSHttpHelperXmlConfig getInstance() throws Exception {
        //默认 xml
        return getInstance(ParseRequest.XML);
    }

    public static HSHttpHelperXmlConfig getInstance(ParseRequest way) throws Exception {
        try {
            switch (way) {
                case XML:
                    parseForXml();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return _instance;
    }

    private static void parseForXml() throws Exception {
        //解析 request-config 配置
        parseRequestConfig();
        //解析 response-config 配置
        parseResponseConfig();
    }

    private static void parseRequestConfig() throws Exception {
        if (_instance.requestConfig == null) {
            //解析request-config 文件,把数据存放到requestConfig中
            _instance.requestConfig = (RequestConfig)getConfigByUrl(REQUEST_CONFIG_FILE,Type.REQUEST);
            _instance.requestHelper = new RequestConfigHelper(_instance.requestConfig);
        }
    }

    private static void parseResponseConfig() throws Exception {
        if (_instance.responseConfig == null) {
            //1.解析request-config 文件,把数据存放到configData中
            _instance.responseConfig = (ResponseConfig)getConfigByUrl(RESPONSE_CONFIG_FILE,Type.RESPONSE);

        }
    }

    private static XmlConfig getConfigByUrl(String url,Type type) throws Exception {
        XStream xStream = new XStream(new Xpp3DomDriver());
        String path = HSHttpHelperXmlConfig.class.
                getResource(url).toURI().getPath();
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        if(type == Type.REQUEST){
            xStream.processAnnotations(RequestConfig.class);
            return (RequestConfig)xStream.fromXML(stream);
        }else {
            xStream.processAnnotations(ResponseConfig.class);
            return (ResponseConfig)xStream.fromXML(stream);
        }
    }


    public String getCharset() {
        return _instance.requestHelper.getCharset();
    }


    public List<ResponseProHandler> getDefaultProHandlers() throws Exception {
        if (defaultProHandlers == null) {
            defaultProHandlers = new ArrayList<ResponseProHandler>();
            DefaultHandlers defaultHandlers = _instance.requestHelper.getDefaultHandlers();
            Map<String, List<String>> proHandlerMap = new HashMap<String, List<String>>();
            if(defaultHandlers != null){
                List<HandlerConfig> proHandlerList = defaultHandlers.getProHandlerData(defaultHandlers);
                setHandlerConfigToMap(proHandlerList,proHandlerMap);
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
            DefaultHandlers defaultHandlers = _instance.requestHelper.getDefaultHandlers();
            if(defaultHandlers != null){
                List<HandlerConfig> preHandlerList = defaultHandlers.getPreHandlerData(defaultHandlers);
                // 前处理把相同类型整理为列表，支持配置多个相同类型的处理器
                Map<String, List<String>> preHandlerMap = new HashMap<String, List<String>>();

                setHandlerConfigToMap(preHandlerList, preHandlerMap);

                setDefaultPreHandlerList(preHandlerMap);
            }
        }
        return defaultPreHandlers;
    }

    private void setHandlerConfigToMap(List<HandlerConfig> handlerConfigs, Map<String, List<String>> handlerMap) {
        if (ListUtil.isNotBlank(handlerConfigs)) {
            for (HandlerConfig config : handlerConfigs) {
                String clazz = config.getClazz();
                String type = config.getType();
                if (handlerMap.containsKey(type)) {
                    List<String> list = handlerMap.get(type);
                    list.add(clazz);
                } else {
                    List<String> list = new ArrayList<String>();
                    list.add(clazz);
                    handlerMap.put(type, list);
                }
            }
        }
    }

    private void setDefaultPreHandlerList(Map<String, List<String>> preHandlerMap) throws Exception {
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
