package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HttpSimulateGateRequest {
    public String name();

    public String url();

    public ResponseType responseType() default ResponseType.TEXT;

    public MethodType method() default MethodType.GET;

    public String description() default "";

    public Parameter[] parameters() default {};

    public Header[] headers() default {};

    /**
     * 请求编码
     *
     * @return
     */
    public String charset() default "";

    /**
     * 返回值类型，用于自动解析
     *
     * @return
     */
    public Class<?> resultClass() default String.class;

    enum ResponseType {
        /**
         * 普通文本：不做处理
         */
        TEXT,// 不做解析
        /**
         * HTML：不做处理
         */
        HTML,// 不做解析
        /**
         * JSON：默认解析为map
         */
        JSON,// 解析为map
        /**
         * XML：默认解析为map
         */
        XML,// 解析为map
        /**
         * byte数组：不做解析
         */
        BYTE_ARRAY// 返回byte[]
    }

    enum MethodType {
        GET,
        POST,
        DELETE
    }
}
