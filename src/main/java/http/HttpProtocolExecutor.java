package http;

import java.util.Map;
/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/11
 * @Version 1.0.0
 */
public interface HttpProtocolExecutor {
    /**
     * get请求
     */
    String doGet(String url, Map<String, String> params) throws Exception;
    /**
     * post请求
     */
    String doPost(String url, Map<String, Object> params) throws Exception;
}
