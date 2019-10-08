package com.hujing.netty.customprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author : hujing
 * @date : 19-9-18
 */
public class MyClientHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            CustomProtocol customProtocol = new CustomProtocol();
            byte[] content = "HelloWorld".getBytes();
            customProtocol.setLength(content.length);
            customProtocol.setContent(content);
            ctx.writeAndFlush(customProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println("msg : " + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println("length : " + msg.getLength());
        System.out.println("count: " + (++this.count));
    }
}
