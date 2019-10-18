package com.hujing.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class MyMessageToByteEncoder extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked!");
        System.out.println(msg.toString());
        out.writeCharSequence(msg.toString(), Charset.forName("utf-8"));
    }
}
