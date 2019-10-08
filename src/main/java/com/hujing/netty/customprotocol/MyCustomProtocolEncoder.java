package com.hujing.netty.customprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MyCustomProtocolEncoder extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyCustomProtocolEncoder encode invoked!");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
