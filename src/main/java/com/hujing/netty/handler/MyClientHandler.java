package com.hujing.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<CustomProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println(msg.getBody());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new CustomProtocol(1,"c"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
