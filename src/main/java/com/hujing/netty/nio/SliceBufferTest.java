package com.hujing.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author : hujing
 * @date : 19-9-23
 * Slice Buffer 就是原来Buffer的一个快照，他们共享底层byte[]，但是有独立的mark,position,limit..
 */
public class SliceBufferTest {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //init buffer
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        //get slice buffer
        buffer.position(2);
        buffer.limit(5);

        ByteBuffer sliceBuffer = buffer.slice();

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            //带索引的绝对获取，不会调用flip方法装换指针位置
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
