package com.xuanwu.ump.HttpSimulateGate;

/**
 * @Description 常量
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public interface HSHttpHelperConstant {
   

    /**
     * 前处理级别：第一步初始化
     */
    int PRE_HANDLER_INIT = 0;
    String PRE_HANDLER_INIT_TYPE = "init";
    /**
     * 前处理级别：第二步验证参数
     */
    int PRE_HANDLER_VALIDATION = 1;
    String PRE_HANDLER_VALIDATION_TYPE = "validation";

    /**
     * 前处理级别：第三步生成URL和参数
     */
    int PRE_HANDLER_BUILD_PARAM = 2;
    String PRE_HANDLER_BUILD_PARAM_TYPE = "parameter";
    String PRE_HANDLER_BUILD_URL_TYPE = "url";
    /**
     * 前处理级别：自定义
     */
    int PRE_HANDLER_USER = 3;
    String PRE_HANDLER_USER_TYPE = "user";
    /**
     * 后处理级别：第一步解析结果
     */
    int PRO_HANDLER_PARSE = 0;
    String PRO_HANDLER_PARSE_TYPE = "parse";
    /**
     * 后处理级别：自定义
     */
    int PRO_HANDLER_USER = 1;
    String PRO_HANDLER_USER_TYPE = "user";

    String DEFAULT_CONTENT_TYPE = "application/json";
    String CONTENT_TYPE = "Content-Type";
}
