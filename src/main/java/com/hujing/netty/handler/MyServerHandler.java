package com.hujing.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println(msg.toString());
        System.out.println(msg.getBody());
        ctx.writeAndFlush(new CustomProtocol(1,"s"));
    }
}
