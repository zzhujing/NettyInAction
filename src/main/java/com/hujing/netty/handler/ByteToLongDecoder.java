package com.hujing.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class ByteToLongDecoder extends ByteToMessageCodec<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked!");
        out.writeLong(msg);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked!");
        System.out.println(in.readableBytes());
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
