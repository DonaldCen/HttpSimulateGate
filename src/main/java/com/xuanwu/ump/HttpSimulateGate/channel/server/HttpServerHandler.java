package com.xuanwu.ump.HttpSimulateGate.channel.server;

import com.xuanwu.ump.HttpSimulateGate.channel.HttpChannelHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;


/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HttpServerHandler implements Callable<String> {
    private ExecutorService bossExecutor;
    private ExecutorService workerExecutor;
    private ScheduledExecutorService scheduledExecutor;

    public HttpServerHandler(ExecutorService bossExecutor, ExecutorService workerExecutor, ScheduledExecutorService scheduledExecutor) {
        this.bossExecutor = bossExecutor;
        this.workerExecutor = workerExecutor;
        this.scheduledExecutor = scheduledExecutor;
    }

    @Override
    public String call() throws Exception {
        ChannelFactory factory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor);
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        final HttpChannelHandler channelHandler = new HttpChannelHandler();
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = Channels.pipeline();
                p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
                        1048576, 0, 4, -4, 4));
//				p.addLast("readTimeout", new ReadTimeoutHandler(new HashedWheelTimer(),
//						60 * 1000, TimeUnit.MILLISECONDS));
                p.addLast("frameEncoder", new LengthFieldPrepender(4, true));
                p.addLast("handler", channelHandler);
                return p;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        int port = getPort();
        bootstrap.bind(new InetSocketAddress(port));
        return null;
    }
}
