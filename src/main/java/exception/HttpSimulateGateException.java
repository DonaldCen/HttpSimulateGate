package exception;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
public class HttpSimulateGateException extends Exception {

    private static final long serialVersionUID = 1L;

    public HttpSimulateGateException(String message) {
        super(message);
    }

    public HttpSimulateGateException(String message, Throwable e) {
        super(message, e);
    }

    public HttpSimulateGateException(Throwable e) {
        super(e);
    }
}
