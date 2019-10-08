package com.hujing.netty.tcppackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author : hujing
 * @date : 19-9-18
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("msg from client ", StandardCharsets.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        System.out.println("收到的消息为 : " + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("收到消息的数量为 : " + (++this.count));
    }
}
