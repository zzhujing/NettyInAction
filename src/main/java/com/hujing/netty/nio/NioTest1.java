package com.hujing.netty.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }
        //切换buffer为写状态 , limit -> position, position->0
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
