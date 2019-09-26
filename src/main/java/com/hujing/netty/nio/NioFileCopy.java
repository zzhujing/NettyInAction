package com.hujing.netty.nio;


import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class NioFileCopy {
    public static void main(String[] args) throws IOException {
        @Cleanup FileInputStream fis = new FileInputStream("input.txt");
        @Cleanup FileOutputStream fos = new FileOutputStream("output.txt");
        FileChannel input = fis.getChannel();
        FileChannel output = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (; ; ) {
            int read = input.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            output.write(buffer);
            //初始化状态，不然write方法写完之后position和limit会同一索引，导致read方法返回0
            buffer.clear();
        }
    }
}
