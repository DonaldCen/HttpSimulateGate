package request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import annotation.HttpSimulateGateRequest;
import entity.ErrorMessage;
import entity.HttpRequestContext;
import entity.ParameterDefine;
import entity.ResponseResult;
import exception.HttpSimulateGateException;
import request.handler.RequestPreHandler;
import request.handler.ResponseProHandler;
import util.MapKeyComparator;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public class AbstractHttpRequest implements HttpRequest {
    protected static Log log = LogFactory.getLog(AbstractHttpRequest.class);
    protected Map<Integer, List<RequestPreHandler>> requestPreHandlerListMap = new TreeMap<Integer, List<RequestPreHandler>>(new MapKeyComparator());
    protected Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = new TreeMap<Integer, List<ResponseProHandler>>(new MapKeyComparator());
    private HttpRequestContext context = null;
    private Map<String, String> headerMap = new HashMap<String, String>();
    private Map<String, String> cookieMap = new HashMap<String, String>();
    private Map<String, Object> inputData = new HashMap<String, Object>();
    private List<ParameterDefine> parameterDefineList = new ArrayList<ParameterDefine>();

    @Override
    public void init(HttpRequestContext context) throws HttpSimulateGateException {

    }

    @Override
    public ResponseResult execute() throws HttpSimulateGateException {
        ResponseResult result = null;
        return null;
    }

    @Override
    public void asyncExecute() throws HttpSimulateGateException {

    }

    @Override
    public void addRequestPreHandler(RequestPreHandler handler) {

    }

    @Override
    public void addResponseProHandler(ResponseProHandler handler) {

    }

    @Override
    public void addParameter(String name, Object value) {

    }

    @Override
    public void addHeader(String name, String value) {

    }

    @Override
    public void addCookie(String name, String value) {

    }

    @Override
    public HttpRequestContext getContext() throws HttpSimulateGateException {
        return null;
    }
}
