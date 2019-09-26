package com.hujing.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class NioReadFileTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("NioText.txt");
        FileChannel fc = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        fc.read(buffer);

        buffer.flip();
        while (buffer.remaining() > 0) {
            System.out.print((char) buffer.get());
        }
    }
}
