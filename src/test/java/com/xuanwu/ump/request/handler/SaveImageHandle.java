package com.xuanwu.ump.request.handler;

import com.xuanwu.ump.HSHttpHelperConstant;
import com.xuanwu.ump.annotation.HSRequest;
import com.xuanwu.ump.entity.HSRequestContext;
import com.xuanwu.ump.entity.ResponseResult;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class SaveImageHandle implements ResponseProHandler {

    @Override
    public ResponseResult handler(HSRequestContext context, ResponseResult result) throws Exception {
        String savePath = String.valueOf(context.getInputDataMap().get("savePath"));
        System.out.println("正在下载:" + savePath);

        if (context.getResponseType() == HSRequest.ResponseType.BYTE_ARRAY) {
            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            byte[] body = (byte[]) result.getBody();
            try {
                FileUtils.writeByteArrayToFile(file, body);
            } catch (IOException e) {
                new Exception(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public int level() {
        return HSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
