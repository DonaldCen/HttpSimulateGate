package com.xuanwu.ump.annotation;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public @interface Header {
    /**
     * 名称
     *
     * @return
     */
    public String name();

    /**
     * 值
     *
     * @return
     */
    public String value();
}
