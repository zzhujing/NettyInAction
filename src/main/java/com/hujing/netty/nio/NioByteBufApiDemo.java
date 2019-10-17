package com.hujing.netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioByteBufApiDemo {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byte[] bytes = "HelloWorld".getBytes();
        byteBuffer.put(bytes, 0, bytes.length);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print(((char) byteBuffer.get()));
        }

        byteBuffer.position(0);
        while (byteBuffer.hasRemaining()) {
            System.out.print(((char) byteBuffer.get()));
        }

    }
}
