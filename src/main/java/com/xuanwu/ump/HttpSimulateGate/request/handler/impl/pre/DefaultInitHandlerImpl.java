package com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperConstant;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ParameterDefine;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class DefaultInitHandlerImpl implements RequestPreHandler {
    /**
     * 默认初始化处理
     *
     * @return 是否继续运行
     */
    @Override
    public boolean handler(HSRequestContext context) throws Exception {
        List<ParameterDefine> list = context.getParameterDefineList();
        for (ParameterDefine parameter : list) {
            String defaultValue = parameter.getDefaultValue();
            String name = parameter.getName();
            Object value = context.getInputDataMap().get(name);
            if (!StringUtils.isEmpty(defaultValue) && (value == null || StringUtils.isEmpty(value.toString()))) {
                context.addInputData(name, defaultValue);
            }
        }
        return true;
    }

    /**
     * 处理级别：小的优先执行
     */
    @Override
    public int level() {
        return HSHttpHelperConstant.PRE_HANDLER_INIT;
    }
}
