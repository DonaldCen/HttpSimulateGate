package com.xuanwu.ump.HttpSimulateGate.channel;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/20
 * @Version 1.0.0
 */
public class HttpChannelHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
    }
}
