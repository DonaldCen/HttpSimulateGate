package com.xuanwu.ump.http;

import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.entity.config.HttpClientConfig;
import com.xuanwu.ump.request.HSHttpHelperXmlConfig;
import com.xuanwu.ump.request.handler.ResponseProHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpTaskExecutor {
    private static final HSHttpTaskExecutor _instance = new HSHttpTaskExecutor();
    private ThreadPoolExecutor threadPool = null;
    private Map<String, FutureTask<Object>> taskMap = new HashMap<String, FutureTask<Object>>();

    private HSHttpTaskExecutor() {}

    public static HSHttpTaskExecutor getInstance()  throws Exception{
        if(_instance.threadPool==null){
            HttpClientConfig httpClientConfig = HSHttpHelperXmlConfig.getInstance().getHttpClientConfig();
            _instance.threadPool = new ThreadPoolExecutor(httpClientConfig.getCorePoolSize(),
                    httpClientConfig.getPoolMaxPoolSize(),
                    httpClientConfig.getKeepAliveSeconds(),
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(httpClientConfig.getPoolQueueCapacity()),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return _instance;
    }
    /**
     * 等待执行线程完成
     */
    public void waitForFinish(Thread thread){
        while (this.threadPool.getActiveCount()>0){
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ThreadPoolExecutor getThreadPool(){
        return threadPool;
    }

    public String execute(HSRequestContext context) throws Exception {
        FutureTask<Object> task = new FutureTask<Object>(new HSHttpClient(context));
        threadPool.submit(task);
        String GUID = UUID.randomUUID().toString();
        taskMap.put(GUID, task);
        return GUID;
    }

    public void asyncExecute(HSRequestContext context, Map<Integer, List<ResponseProHandler>> responseProHandlerListMap) throws Exception {
        HSHttpAsyncClient client = new HSHttpAsyncClient(context, responseProHandlerListMap);
        threadPool.submit(client);
    }

    public ResponseResult getResult(String GUID) throws Exception {
        FutureTask<Object> task = taskMap.get(GUID);
        try {
            return (ResponseResult) task.get();
        } catch (Exception e) {
            ResponseResult result = new ResponseResult();
            result.setStatus(500);
            result.setWasteTime(-1);
            result.setBody(e.getMessage());
            return result;
        } finally {
            taskMap.remove(GUID);
        }
    }
}
