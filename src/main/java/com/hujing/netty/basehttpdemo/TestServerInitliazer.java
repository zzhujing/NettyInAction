package com.hujing.netty.basehttpdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author hujing
 * × channel 初始化器
 */
public class TestServerInitliazer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //get pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //add ChannelHandler
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
