package com.xuanwu.ump.HttpSimulateGate.entity;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 */
public abstract class XmlConfig {
    public abstract Type getType();
    public enum Type{
        REQUEST,
        RESPONSE
    }
}
