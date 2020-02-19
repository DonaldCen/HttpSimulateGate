package com.xuanwu.ump.HttpSimulateGate.http;

import com.xuanwu.ump.HttpSimulateGate.HSHttpHelperXmlConfig;
import com.xuanwu.ump.HttpSimulateGate.annotation.HSRequest;
import com.xuanwu.ump.HttpSimulateGate.entity.HSRequestContext;
import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HttpClientConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public abstract class HSHttpAbstractClient {
    protected static Log log = LogFactory.getLog(HSHttpAbstractClient.class);

    protected HSRequestContext context;
    protected HttpClient httpClient = null;
    protected HttpRequestBase httpRequest = null;

    public HSHttpAbstractClient(HSRequestContext context) {
        this.context = context;
    }

    protected void buildHttpRequest() throws Exception {
        List<NameValuePair> nvps = context.getNameValuePairList();
        String url = context.getUrl();
        if (context.getMethod() == HSRequest.MethodType.GET) {
            dealWithGetRequest(url,nvps);
        } else if (context.getMethod() == HSRequest.MethodType.POST) {
            dealWithPostRequest(url,nvps);
        } else {
            throw new Exception("暂时不支持其他 methodType="+context.getMethod());
        }
        // 添加head
        addHeader();
        // 添加cookie
        addCookie();
    }

    private void dealWithGetRequest(String url,List<NameValuePair> nvps) throws Exception {
        this.httpRequest = new HttpGet(url);
        String str = null;
        try {
            if (nvps != null) {
                str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, context.getCharset()));
                String doc = "?";
                if (context.getUrl().indexOf("?") != -1) {
                    doc = "&";
                }
                url += doc + str;
                //context.setUrl(url);
                this.httpRequest.setURI(new URI(url));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void dealWithPostRequest(String url,List<NameValuePair> nvps) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setHeader("ContentType", context.getContentType().getContentType());

        Map<String, Object> multipartDataMap = context.getMultipartDataMap();
        try {
            // 文件上传
            if (multipartDataMap != null && !multipartDataMap.isEmpty()) {
                log.debug("文件上传");
                Set<String> keySet = multipartDataMap.keySet();
                MultipartEntity entity = new MultipartEntity();
                for (String key : keySet) {
                    Object obj = multipartDataMap.get(key);
                    if (obj instanceof File) {
                        FileBody fileBody = new FileBody((File) obj);
                        entity.addPart(key, fileBody);
                    } else if (obj instanceof String) {
                        StringBody stringBody = new StringBody(String.valueOf(obj));
                        entity.addPart(key, stringBody);
                    }
                }
                if (nvps != null && !nvps.isEmpty()) {
                    for (NameValuePair nvp : nvps) {
                        StringBody stringBody = new StringBody(nvp.getValue());
                        entity.addPart(nvp.getName(), stringBody);
                    }
                }
                post.setEntity(entity);
            }
            // 普通提交
            else {
                UrlEncodedFormEntity entry = new UrlEncodedFormEntity(nvps, context.getCharset());
                entry.setContentType("application/x-www-form-urlencoded;charset=" + context.getCharset());
                post.setEntity(entry);
            }
            this.httpRequest = post;
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e);
        }
    }

    private void addHeader(){
        Map<String, String> headerMap = context.getHeaderMap();
        if (!headerMap.isEmpty()) {
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                this.httpRequest.addHeader(key, headerMap.get(key));
            }
        }
    }

    private void addCookie(){
        Map<String, String> cookieMap = context.getCookieMap();
        if (!cookieMap.isEmpty()) {
            Set<String> keySet = cookieMap.keySet();
            StringBuffer cookies = new StringBuffer();
            for (String key : keySet) {
                cookies.append(key).append("=").append(cookieMap.get(key)).append(";");
            }
            this.httpRequest.addHeader("Cookie", cookies.toString());
        }
    }

    public ResponseResult doRequest() throws Exception {
        if (context == null) {
            throw new Exception("没有请求context！");
        }
        httpClient = new DefaultHttpClient();
        if (context.getUrl().startsWith("https")) {
            wrapClient();
        }
        HttpClientConfig httpClientConfig = HSHttpHelperXmlConfig.getInstance().getHttpClientConfig();
        this.httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpClientConfig.getConnectionTimeout());
        this.httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, httpClientConfig.getSocketTimeout());
        // 初始化
        buildHttpRequest();
        log.debug(context);
        ResponseResult result = new ResponseResult();
        // 开始执行
        long beginTime = System.currentTimeMillis();
        try {
            HttpResponse response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            // 返回状态
            result.setStatus(response.getStatusLine().getStatusCode());
            // 相应时间
            result.setWasteTime(System.currentTimeMillis() - beginTime);
            // 是否是gzip响应
            boolean isGzip = false;
            if (entity.getContentEncoding() != null) {
                String gzip = entity.getContentEncoding().getValue();
                isGzip = "gzip".equals(gzip.toLowerCase());
            }
            // 返回为byte[]
            if (context.getResponseType() == HSRequest.ResponseType.BYTE_ARRAY) {
                InputStream in = entity.getContent();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try {
                    byte b[] = new byte[1024 * 8];
                    int j = 0;
                    while ((j = in.read(b)) != -1) {
                        out.write(b, 0, j);
                    }
                    out.flush();
                    out.close();
                } catch (IOException ex) {
                    throw ex;
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw e;
                    }
                }
                result.setBody(out.toByteArray());
            }
            // gzip自动解压
            else if (isGzip) {
                result.setBody(EntityUtils.toString(new GzipDecompressingEntity(entity), context.getCharset()));
            } else {
                result.setBody(EntityUtils.toString(entity, context.getCharset()));
            }
            // 获取cookie
            Header[] cookies = response.getHeaders("Cookie");
            if (cookies != null && cookies.length > 0) {
                for (Header cookie : cookies) {
                    context.addCookie(cookie.getName(), cookie.getValue());
                }
            }
            log.debug("响应时间：" + result.getWasteTime());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e);
        } finally {
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    private void wrapClient() throws Exception {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            httpClient = new DefaultHttpClient(ccm, httpClient.getParams());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }
}
