package com.xuanwu.ump.request.handler.impl.pre;

import com.xuanwu.ump.HSHttpHelperConstant;
import com.xuanwu.ump.annotation.Parameter;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ParameterDefine;
import com.xuanwu.ump.request.handler.RequestPreHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class DefaultParameterBuilderHandlerImpl implements RequestPreHandler {
    protected static Log log = LogFactory.getLog(DefaultParameterBuilderHandlerImpl.class);

    /**
     * 请求预处理
     *
     * @return 是否继续运行
     */
    @Override
    public boolean handler(HSRequestContext context) throws Exception {
        List<ParameterDefine> parameterDefineList = context.getParameterDefineList();
        List<NameValuePair> nameValuePairList = context.getNameValuePairList();
        if (nameValuePairList == null) {
            nameValuePairList = new ArrayList<NameValuePair>();
        }
        Map<String, Object> multipartDataMap = context.getMultipartDataMap();
        if (multipartDataMap == null) {
            multipartDataMap = new HashMap<String, Object>();
        }
        if (parameterDefineList != null) {
            for (ParameterDefine parameterDefine : parameterDefineList) {
                String name = parameterDefine.getName();
                Object value = context.getInputDataMap().get(name);
                if (value instanceof List) {
                    List list = (List) value;
                    for (Object o : list) {
                        if (o instanceof String) {
                            nameValuePairList.add(new BasicNameValuePair(name, String.valueOf(o)));
                        }
                    }
                } else if (value instanceof File) {
                    log.debug(name);
                    multipartDataMap.put(name, value);
                } else {
                    String s = String.valueOf(value);
                    if (!StringUtils.isEmpty(s) && !"null".equals(s.toLowerCase())) {
                        if (parameterDefine.getType() == Parameter.Type.LIST) {
                            String[] list = s.split(",");
                            for (String v : list) {
                                nameValuePairList.add(new BasicNameValuePair(name, v));
                            }
                        } else if (parameterDefine.getType() == Parameter.Type.FILE) {
                            File file = new File(s);
                            if (file.isFile()) {
                                multipartDataMap.put(name, value);
                            }
                        } else {
                            nameValuePairList.add(new BasicNameValuePair(name, s));
                        }
                    }
                }
            }
        }
        context.setNameValuePairList(nameValuePairList);
        context.setMultipartDataMap(multipartDataMap);
        return true;
    }

    /**
     * 处理级别：小的优先执行
     */
    @Override
    public int level() {
        return HSHttpHelperConstant.PRE_HANDLER_BUILD_PARAM;
    }
}
