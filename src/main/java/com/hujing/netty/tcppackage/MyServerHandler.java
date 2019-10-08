package com.hujing.netty.tcppackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        System.out.println("收到的消息为 : " + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("收到消息的数量为 : " + (++this.count));
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), StandardCharsets.UTF_8);
        System.out.println(ctx.writeAndFlush(byteBuf));
    }
}
