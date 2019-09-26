package com.hujing.netty.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author : hujing
 * @date : 2019/9/25
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);
        String filename = "/home/hujing/Documents/openjdk-8u40-b25-linux-x64-10_feb_2015.tar.gz";
        FileChannel fc = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();

        //这里会直接使用零拷贝的方法将FileChannel中的指定字节数拷贝到指定的Channel中，比使用循环会更加的快捷！
        fc.transferTo(0, fc.size(), socketChannel);
/*
    普通的使用循环进行零拷贝
    ByteBuffer buffer = ByteBuffer.allocateDirect(4096 * 4096);
        int readLength = 0;
        for (; ; ) {
            int read = fc.read(buffer);
            if (read == -1) {
                System.out.println("readLength :" + readLength);
                break;
            }
            readLength += read;
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }*/
        System.out.println("nio send Time : " + (System.currentTimeMillis() - startTime));
    }
}
