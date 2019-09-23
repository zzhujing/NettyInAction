package com.hujing.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hujing
 * @date 19-9-23
 */
public class NioDirectByteBufferTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("input.txt");
        FileOutputStream fos = new FileOutputStream("output.txt");
        FileChannel inputChannel = fis.getChannel();
        FileChannel outputChannel = fos.getChannel();


        //使用领拷贝的方式直接访问进行操作系统的IO操作
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        for (; ; ) {
            buffer.clear();
            int read = inputChannel.read(buffer);

            if (-1 == read) {
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }
        fis.close();
        fos.close();
    }
}
