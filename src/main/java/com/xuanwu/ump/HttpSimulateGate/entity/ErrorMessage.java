package com.xuanwu.ump.HttpSimulateGate.entity;

import java.text.MessageFormat;

/**
 * @Description 参数验证结果
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class ErrorMessage {
    private static final int SUCCESS_STATUS = 0;

    private String message;
    private int status;

    private ErrorMessage(){}

    private ErrorMessage(int status,String message){
        this.message = message;
        this.status = status;
    }

    public ErrorMessage(String message, Object... var) {
        this.message = MessageFormat.format(message, var);
    }

    private ErrorMessage success(){
        return new ErrorMessage(SUCCESS_STATUS,"SUCCESS");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
