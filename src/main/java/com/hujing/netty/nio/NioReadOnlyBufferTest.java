package com.hujing.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class NioReadOnlyBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(8);
        buffer.limit(buffer.capacity());

        //生成只读buffer的时候是根据position和limit来生成新的HelpByteBufferR
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
