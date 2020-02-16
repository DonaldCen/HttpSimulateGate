package http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.HttpConstants;
import util.MapUtil;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/11
 * @Version 1.0.0
 */
@Component
public class PoolingHttpClientExecutor implements HttpProtocolExecutor {
    private HttpClient defaultClient;
    private RequestConfig requestConfig;

    @PostConstruct
    public void init() {
        defaultClient = getClient();
    }

    public HttpClient getClient() {
        return getBuilder().build();
    }
    private HttpClientBuilder getBuilder() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(20);
        HttpClientBuilder builder = HttpClients.custom().setConnectionManager(cm);
        return builder;
    }

    @Override
    public String doGet(String url, Map<String, String> params) throws Exception {
        String getUrl = parseParamToGetUrl(url,params);
        HttpGet httpGet = new HttpGet(getUrl);
        httpGet.setConfig(requestConfig);

        HttpResponse response = defaultClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, HttpConstants.DEFAULT_CHARSET);
        EntityUtils.consume(entity);
        return result;
    }

    private String parseParamToGetUrl(String url, Map<String, String> params) throws Exception {
        List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
        if(MapUtil.isNotBlank(params)){
            for(String key:params.keySet()){
                param.add(new BasicNameValuePair(key,params.get(key)));
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append("?").append(EntityUtils.toString(new UrlEncodedFormEntity(param, HttpConstants.DEFAULT_CHARSET)));

        return sb.toString();
    }

    @Override
    public String doPost(String url, Map<String, Object> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            StringEntity entity = new StringEntity(JsonUtil.serialize(params), HttpConstants.DEFAULT_CHARSET);
            entity.setContentEncoding(HttpConstants.DEFAULT_CHARSET);
            entity.setContentType(HttpConstants.DEFAULT_CONTENTTYPE);
            httpPost.setEntity(entity);
        }

        HttpResponse response = defaultClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, WebConstants.DEFAULT_CHARSET);
        EntityUtils.consume(entity);
        return result;
    }
}
