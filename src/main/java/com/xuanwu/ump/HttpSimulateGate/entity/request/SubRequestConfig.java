package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 *
 * name CDATA #REQUIRED
 * url CDATA #REQUIRED
 * description CDATA #IMPLIED
 * response-type (TEXT|HTML|JSON|XML|BYTE_ARRAY) "HTML"
 * method (GET|POST|DELETE) "GET"
 * charset CDATA #IMPLIED
 * result-class CDATA #IMPLIED
 */
@XStreamAlias("request")
public class SubRequestConfig {
//    @XStreamImplicit(itemFieldName="handlers")
    private HandlersConfig handlers;
    @XStreamImplicit(itemFieldName="parameters")
    private List<ParameterConfig> parameters;
    @XStreamImplicit(itemFieldName="headers")
    private List<HeaderConfig> headers;

    @XStreamAsAttribute
    @XStreamAlias("url")
    private String url;
    @XStreamAsAttribute
    @XStreamAlias("description")
    private String description;
    @XStreamAsAttribute
    @XStreamAlias("response-type")
    private String responseType;
    @XStreamAsAttribute
    @XStreamAlias("method")
    private String method;
    @XStreamAsAttribute
    @XStreamAlias("charset")
    private String charset;
    @XStreamAsAttribute
    @XStreamAlias("result-class")
    private String resultClass;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
    }

    public HandlersConfig getHandlers() {
        return handlers;
    }

    public void setHandlers(HandlersConfig handlers) {
        this.handlers = handlers;
    }

    public List<ParameterConfig> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterConfig> parameters) {
        this.parameters = parameters;
    }

    public List<HeaderConfig> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderConfig> headers) {
        this.headers = headers;
    }
}
