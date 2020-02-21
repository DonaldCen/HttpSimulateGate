package com.xuanwu.ump.HttpSimulateGate.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/21
 * @Version 1.0.0
 */
public class SimulateGateServer {
    private static final Logger logger = LoggerFactory.getLogger(SimulateGateServer.class);

    public void init(){
        logger.info("Starting http server on port: " + port);
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        NioEventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        p.addLast("decoder", new HttpRequestDecoder());
                        p.addLast("encoder", new HttpResponseEncoder());
                        // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
                        p.addLast("http-aggregator",
                                new HttpObjectAggregator(65536));
                        p.addLast("handler", handler);
                    }
                });

        bootstrap.bind(new InetSocketAddress(port));
    }

    private int port;
    private ChannelHandler handler;

    public void setPort(int port) {
        this.port = port;
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }
}
