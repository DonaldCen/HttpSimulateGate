package com.xuanwu.ump.entity;

/**
 * @Description 请求结果
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class ResponseResult {
    private Object body = null;
    private int status = -1;
    private long wasteTime = 0;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public <T> T getBody(Class<T> clazz) {
        return (T) body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return status == 200;
    }

    public long getWasteTime() {
        return wasteTime;
    }

    public void setWasteTime(long wasteTime) {
        this.wasteTime = wasteTime;
    }
}
