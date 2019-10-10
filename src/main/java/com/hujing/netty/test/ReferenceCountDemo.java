package com.hujing.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ReferenceCountDemo {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1232);
        buffer.writeInt(1232);
        int length = buffer.readInt();
        System.out.println(length);
        System.out.println(buffer.readBytes(4).readInt());
    }
}
