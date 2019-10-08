package com.hujing.netty.customprotocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MyServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println("msg : " + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println("length : " + msg.getLength());
        System.out.println("count: " + (++this.count));
        String uuid = UUID.randomUUID().toString();
        byte[] content = uuid.getBytes();
        int length = content.length;
        CustomProtocol cp = new CustomProtocol();
        cp.setContent(content);
        cp.setLength(length);
        ctx.writeAndFlush(cp);
    }
}
