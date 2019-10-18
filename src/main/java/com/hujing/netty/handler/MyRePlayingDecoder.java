package com.hujing.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyRePlayingDecoder extends ReplayingDecoder<DecoderStatus> {

    private int length;
    private CustomProtocol customProtocol;

    public MyRePlayingDecoder() {
        super(DecoderStatus.READ_LENGTH);
        customProtocol = new CustomProtocol();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //使用自定义枚举来区分什么时候进行复杂的数据结构的解码
        switch (state()) {
            case READ_LENGTH:
                //这里默认第一个字节是header
                length = Integer.parseInt(in.readCharSequence(1, StandardCharsets.UTF_8).toString());
                System.out.println(length);
                //记录当前readIndex，用来避免多次mark
                checkpoint(DecoderStatus.READ_BODY);
                customProtocol.setHeader(length);
                out.add(customProtocol);
            case READ_BODY:
                //第二个字节是body
                String body = in.readCharSequence(1, StandardCharsets.UTF_8).toString();
                checkpoint(DecoderStatus.READ_LENGTH);
                customProtocol.setBody(body);
                out.add(body);
                break;
            default:
                throw new IllegalAccessException("UnSupported State");
        }
    }
}
