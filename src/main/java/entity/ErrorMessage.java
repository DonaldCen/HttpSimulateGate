package entity;

import java.text.MessageFormat;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public class ErrorMessage {
    private String message = null;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, Object... var) {
        this.message = MessageFormat.format(message, var);
    }

    @Override
    public String toString() {
        return this.message;
    }
}
