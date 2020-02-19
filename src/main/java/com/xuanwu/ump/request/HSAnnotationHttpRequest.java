package com.xuanwu.ump.request;

import com.xuanwu.ump.HSHttpHelperXmlConfig;
import com.xuanwu.ump.annotation.HSRequest;
import com.xuanwu.ump.annotation.Header;
import com.xuanwu.ump.annotation.Parameter;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ParameterDefine;

import org.apache.commons.lang.StringUtils;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public abstract class HSAnnotationHttpRequest extends HSAbstractHttpRequest {

    @Override
    protected HSRequestContext builderContext()  throws Exception{
        HSRequestContext context = null;
        HSRequest ann = this.getClass().getAnnotation(HSRequest.class);
        if (ann != null) {
            context = new HSRequestContext();
            context.setName(ann.name());
            if (StringUtils.isEmpty(context.getUrl())) {
                context.setUrl(ann.url());
            }
            if (StringUtils.isEmpty(context.getDescription())) {
                context.setDescription(ann.description());
            }
            if (context.getResponseType() == null) {
                context.setResponseType(ann.responseType());
            }
            if (context.getMethod() == null) {
                context.setMethod(ann.method());
            }
            if(context.getContentType() == null){
                context.setContentType(ann.contentType());
            }

            String charset = ann.charset();
            if (StringUtils.isEmpty(charset)) {
                charset = HSHttpHelperXmlConfig.getInstance().getCharset();
            }
            if (StringUtils.isEmpty(context.getCharset())) {
                context.setCharset(charset);
            }
            if (context.getResultClass() == null) {
                context.setResultClass(ann.resultClass());
            }

            if (ann.headers() != null) {
                for (Header header : ann.headers()) {
                    context.addHeader(header.name(), header.value());
                }
            }
            if (ann.parameters() != null) {
                for (Parameter parameter : ann.parameters()) {
                    context.addParameterDefine(builderParameterDefine(parameter));
                }
            }
        }
        return context;
    }

    /**
     * 获取参数
     *
     * @param parameter
     * @return
     */
    private ParameterDefine builderParameterDefine(Parameter parameter) {
        ParameterDefine parameterDefine = new ParameterDefine();
        parameterDefine.setName(parameter.name());
        parameterDefine.setDescription(parameter.description());
        parameterDefine.setDefaultValue(parameter.defaultValue());
        parameterDefine.setExample(parameter.example());
        parameterDefine.setRequired(parameter.required());
        parameterDefine.setType(parameter.type());
        parameterDefine.setValidateRegex(parameter.validateRegex());
        return parameterDefine;
    }
}
