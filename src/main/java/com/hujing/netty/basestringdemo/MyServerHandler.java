package com.hujing.netty.basestringdemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * create by : 19-9-18
 *
 * @author : hujing
 * 服务端管道处理逻辑
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client remote address : " + ctx.channel().remoteAddress());
        System.out.println("from client : " + msg);
        ctx.writeAndFlush("from server : " + UUID.randomUUID().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
