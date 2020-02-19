package com.xuanwu.ump.HttpSimulateGate.request.handler.impl.pre;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperConstant;
import com.xuanwu.ump.HttpSimulateGate.annotation.Parameter;
import com.xuanwu.ump.HttpSimulateGate.entity.ErrorMessage;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ParameterDefine;
import com.xuanwu.ump.HttpSimulateGate.request.handler.RequestPreHandler;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class DefaultValidationHandlerImpl implements RequestPreHandler {
    /**
     * 请求预处理
     *
     * @return 是否继续运行
     */
    @Override
    public boolean handler(HSRequestContext context) throws Exception {
        List<ParameterDefine> parameterDefineList = context.getParameterDefineList();
        if (parameterDefineList != null) {
            for (ParameterDefine parameterDefine : parameterDefineList) {
                boolean required = parameterDefine.isRequired();
                String name = parameterDefine.getName();
                String value = String.valueOf(context.getInputDataMap().get(name));
                // 验证非空
                if (required && StringUtils.isEmpty(value)) {
                    context.addValidationResult(
                            new ErrorMessage("参数[{0}({1})]，不能为空。",
                                    parameterDefine.getName(), parameterDefine.getDescription()));
                }
                // 验证数字类型
                if (parameterDefine.getType() == Parameter.Type.INT && !StringUtils.isBlank(value)) {
                    try {
                        Integer.valueOf(value);
                    } catch (NumberFormatException e) {
                        context.addValidationResult(
                                new ErrorMessage("参数[{0}]类型为INT，输入为[{1}]，转换失败。",
                                        parameterDefine.getName(), value));
                    }
                }
                // 验证正则
                String regex = parameterDefine.getValidateRegex();
                if (!StringUtils.isBlank(value) && !StringUtils.isBlank(regex) && !Pattern.matches(regex, value)) {
                    context.addValidationResult(
                            new ErrorMessage("参数[{0}]，输入为[{1}]，正则表达式[{2}]验证失败。",
                                    parameterDefine.getName(), value, parameterDefine.getValidateRegex()));
                }
            }
        }
        return context.getErrorMessageList().isEmpty();
    }

    /**
     * 处理级别：小的优先执行
     */
    @Override
    public int level() {
        return HSHttpHelperConstant.PRE_HANDLER_VALIDATION;
    }
}
