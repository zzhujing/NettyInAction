package com.hujing.netty.customprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MyCustomProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyCustomProtocolDecoder decode invoked!");
        int length = in.readInt();
        byte[] content = new byte[length];
        //将所有数据读取到content中
        in.readBytes(content);
        CustomProtocol cp = new CustomProtocol();
        cp.setLength(length);
        cp.setContent(content);
        out.add(cp);
    }
}
