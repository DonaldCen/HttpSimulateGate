package com.xuanwu.ump.request.handler;

import com.xuanwu.ump.HSHttpHelperConstant;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;
import com.xuanwu.ump.request.HSHttpRequest;
import com.xuanwu.ump.request.HSHttpRequestFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class DownloadAllImageHandle implements ResponseProHandler {
    @Override
    public ResponseResult handler(HSRequestContext context, ResponseResult result) throws Exception {
        String body = result.getBody(String.class);
        // 提取下载图片正则表达式
        String reg = "\"objURL\":\"([^\"]*\\.jpg)\",";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);
        String savePath = "C:/testDownloadImage/";
        System.out.println(savePath);
        int i = 0;
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            HSHttpRequest request = HSHttpRequestFactory.getHttpRequest("saveImageRequest");

            request.getContext().addInputData("savePath", savePath + i + ".jpg");
            request.getContext().addInputData("url", imageUrl);
            // 同步下载
            //request.execute();
            // 异步下载：多个下载请求同时进行
            request.asyncExecute();

            i++;
        }
        return result;
    }

    @Override
    public int level() {
        return HSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
