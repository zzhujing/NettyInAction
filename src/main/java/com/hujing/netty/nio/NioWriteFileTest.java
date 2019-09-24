package com.hujing.netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class NioWriteFileTest {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("NioWriter.txt");
        FileChannel fc = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte[] bytes = "Hello World Java".getBytes();

        for (byte b : bytes) {
            buffer.put(b);
        }
        //切换成写
        buffer.flip();
        fc.write(buffer);
    }
}
