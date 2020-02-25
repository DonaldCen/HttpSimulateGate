package com.xuanwu.ump.HttpSimulateGate.channel;


import com.xuanwu.ump.HttpSimulateGate.entity.ResponseResult;
import com.xuanwu.ump.HttpSimulateGate.request.impl.DefaultSimulateGateHttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/20
 * @Version 1.0.0
 */
@Sharable
public class HttpChannelHandler extends ChannelDuplexHandler {
    private String responseName;

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    private static final Logger logger = LoggerFactory.getLogger(HttpChannelHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest httpReq = (FullHttpRequest) msg;

        logger.info("receive from {}", ctx.channel().remoteAddress());

        DefaultSimulateGateHttpRequest defaultRequest = new DefaultSimulateGateHttpRequest(httpReq,responseName);
        ResponseResult result = defaultRequest.execute();

        sendResp(ctx, result);

    }

    private void sendResp(ChannelHandlerContext ctx, ResponseResult result) {
        HttpResponse resp;
        String resultBody = (String) result.getBody();
        resp = createResp(resultBody.getBytes(),
                result.getResponseType().getContentType(),
                HttpResponseStatus.OK);
        ChannelFuture f = ctx.channel().writeAndFlush(resp);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    private HttpResponse createResp(byte[] src, String contentType, HttpResponseStatus status) {
        ByteBuf content = PooledByteBufAllocator.DEFAULT.buffer();
        content.writeBytes(src);
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        resp.headers().set("Content-Type", contentType + "; charset=UTF-8");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            // ignore
        } else {
            logger.error("Error ocuurs in connection", cause);
        }
        ctx.channel().close();
    }
}
