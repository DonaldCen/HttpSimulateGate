package com.xuanwu.ump.exception;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class HSException extends Exception{
    private static final long serialVersionUID = 1L;

    public HSException(String message) {
        super(message);
    }

    public HSException(String message, Throwable e) {
        super(message, e);
    }

    public HSException(Throwable e) {
        super(e);
    }
}
