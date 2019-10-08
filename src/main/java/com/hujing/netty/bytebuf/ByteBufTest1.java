package com.hujing.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author : hujing
 * @date : 2019/9/29
 */
public class ByteBufTest1 {
    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("Hello World".getBytes(StandardCharsets.UTF_8));

        System.out.println(byteBuf.arrayOffset());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readableBytes());

        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.println((char) byteBuf.getByte(i));
        }

        System.out.println(byteBuf.getCharSequence(0, 5, StandardCharsets.UTF_8));
    }
}
