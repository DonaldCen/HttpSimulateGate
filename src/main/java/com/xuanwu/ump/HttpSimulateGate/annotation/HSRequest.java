package com.xuanwu.ump.HttpSimulateGate.annotation;

import com.xuanwu.ump.HttpSimulateGate.entity.ContentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HSRequest {
    /**
     * 名称
     */
    public String name();

    /**
     * 请求路径
     */
    public String url();

    public ContentType contentType() default ContentType.JSON;

    /**
     * 返回值类型：默认普通文本
     */
    public ResponseType responseType() default ResponseType.TEXT;

    /**
     * 请求方式
     */
    public MethodType method() default MethodType.GET;

    /**
     * 描述
     */
    public String description() default "";

    /**
     * 参数列表
     */
    public Parameter[] parameters() default {};

    /**
     * 头部列表
     */
    public Header[] headers() default {};

    /**
     * 请求编码
     */
    public String charset() default "";

    /**
     * 返回值类型，用于自动解析
     */
    public Class<?> resultClass() default String.class;

    /**
     * 返回值类型：根据类型自动解析结果
     */
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

    /**
     * 请求方式
     */
    enum MethodType {
        GET,
        POST,
        DELETE
    }

}
