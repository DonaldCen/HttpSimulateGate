package com.xuanwu.ump.HttpSimulateGate.entity;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class HSRequestContext {
    /**
     * 名称
     */
    private String name;
    /**
     * 头列表
     */
    private Map<String, String> headerMap;
    /**
     * 参数
     */
    private List<ParameterDefine> parameterDefineList;
    /**
     * 验证结果列表
     */
    private List<ErrorMessage> errorMessageList;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求方法
     */
    private HSRequest.MethodType method;

    private HSRequest.ContentType contentType;
    /**
     * 相应类型
     */
    private HSRequest.ResponseType responseType;
    /**
     * 描述
     */
    private String description;
    /**
     * 输入
     */
    private Map<String, Object> inputDataMap;
    /**
     * 请求参数
     */
    private List<NameValuePair> nameValuePairList;
    /**
     * cookie
     */
    private Map<String, String> cookieMap;
    /**
     * 编码
     */
    private String charset;
    /**
     * 多媒体参数
     */
    private Map<String, Object> multipartDataMap;
    /**
     * 返回值类型：用于自动解析
     */
    private Class<?> resultClass;

    public HSRequestContext() {
        headerMap = new HashMap<String, String>();
        parameterDefineList = new ArrayList<ParameterDefine>();
        errorMessageList = new ArrayList<ErrorMessage>();
        cookieMap = new HashMap<String, String>();
        multipartDataMap = new HashMap<String, Object>();
        inputDataMap = new HashMap<String, Object>();
    }

    public String getName() {
        return name;
    }

    public HSRequestContext setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public HSRequestContext addHeader(String name, String value) {
        this.headerMap.put(name, value);
        return this;
    }

    public List<ParameterDefine> getParameterDefineList() {
        return parameterDefineList;
    }

    public HSRequestContext addParameterDefine(ParameterDefine parameterDefine) {
        if (!containsParameterDefine(parameterDefine.getName())) {
            this.parameterDefineList.add(parameterDefine);
        }
        return this;
    }

    public boolean containsParameterDefine(String name) {
        for (ParameterDefine parameter : this.parameterDefineList) {
            if (parameter.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    public HSRequestContext setUrl(String url) {
        this.url = url;
        return this;
    }

    public HSRequest.MethodType getMethod() {
        return method;
    }

    public HSRequestContext setMethod(HSRequest.MethodType method) {
        this.method = method;
        return this;
    }

    public HSRequest.ContentType getContentType() {
        return contentType;
    }

    public HSRequestContext setContentType(HSRequest.ContentType type){
        this.contentType = type;
        return this;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }

    public HSRequestContext setResultClass(Class<?> resultClass) {
        this.resultClass = resultClass;
        return this;
    }

    public HSRequest.ResponseType getResponseType() {
        return responseType;
    }

    public HSRequestContext setResponseType(HSRequest.ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HSRequestContext setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, Object> getInputDataMap() {
        return inputDataMap;
    }

    public HSRequestContext addInputData(String key, Object value) {
        getInputDataMap().put(key, value);
        return this;
    }

    public Map<String, Object> getMultipartDataMap() {
        return multipartDataMap;
    }

    public HSRequestContext setMultipartDataMap(Map<String, Object> multipartDataMap) {
        this.multipartDataMap = multipartDataMap;
        return this;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public HSRequestContext addValidationResult(ErrorMessage errorMessage) {
        this.errorMessageList.add(errorMessage);
        return this;
    }

    public List<NameValuePair> getNameValuePairList() {
        return nameValuePairList;
    }

    public HSRequestContext setNameValuePairList(List<NameValuePair> nameValuePairList) {
        this.nameValuePairList = nameValuePairList;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public HSRequestContext addCookie(String name, String value) {
        this.cookieMap.put(name, value);
        return this;
    }

    /**
     * 不清楚cookie
     */
    public void clear() {
        if (this.inputDataMap != null) {
            this.inputDataMap.clear();
        }
        if (this.headerMap != null) {
            this.headerMap.clear();
        }
        if (this.multipartDataMap != null) {
            this.multipartDataMap.clear();
        }
        if (this.nameValuePairList != null) {
            this.nameValuePairList.clear();
        }
        if (this.errorMessageList != null) {
            this.errorMessageList.clear();
        }
        if (this.parameterDefineList != null) {
            this.parameterDefineList.clear();
        }
    }

    public void clearCookie() {
        if (this.cookieMap != null) {
            this.cookieMap.clear();
        }
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("name=").append(name).append("\n")
                .append("description=").append(description).append("\n")
                .append("url=").append(url).append("\n")
                .append("method=").append(method.name()).append("\n")
                .append("responseType=").append(responseType.name()).append("\n");
        if (headerMap != null) {
            str.append("headers:\n");
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(headerMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (cookieMap != null) {
            str.append("cookies:\n");
            Set<String> keySet = cookieMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(cookieMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (nameValuePairList != null) {
            str.append("parameters:\n");
            for (NameValuePair nvp : nameValuePairList) {
                str.append(nvp.getName()).append("=").append(nvp.getValue()).append(";");
            }
            str.append("\n");
        }
        if (multipartDataMap != null) {
            str.append("multipartData:\n");
            Set<String> keySet = multipartDataMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(multipartDataMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }
        if (parameterDefineList != null) {
            str.append("parameterDefine:\n");
            for (ParameterDefine parameter : parameterDefineList) {
                str.append(parameter.getName()).append(";");
            }
            str.append("\n");
        }

        if (inputDataMap != null) {
            str.append("inputData:\n");
            Set<String> keySet = inputDataMap.keySet();
            for (String key : keySet) {
                str.append(key).append("=").append(inputDataMap.get(key).toString()).append(";");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
