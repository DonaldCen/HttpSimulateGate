package com.xuanwu.ump.request.handler.impl.pre;

import com.xuanwu.ump.HSHttpHelperConstant;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.exception.HSException;
import com.xuanwu.ump.request.handler.RequestPreHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class DefaultURLBuilderHandlerImpl implements RequestPreHandler {
    /**
     * 请求预处理
     *
     * @return 是否继续运行
     */
    @Override
    public boolean handler(HSRequestContext context) throws HSException {
        String url = context.getUrl();
        Pattern pattern = Pattern.compile("(\\{([^&/\\}]+)\\})");
        Matcher matcher = pattern.matcher(url);
        String key = null, value = null;
        while (matcher.find()) {
            key = matcher.group(1);
            value = String.valueOf(context.getInputDataMap().get(matcher.group(2)));

            key = key.replace("{", "\\{").replace("}", "\\}");
            url = url.replaceAll(key, value);
        }
        context.setUrl(url);
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
