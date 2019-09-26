package com.hujing.netty.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author : hujing
 * @date : 2019/9/25
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        //设置为true,在TIME_WAIT状态也可以连接
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));
        ByteBuffer buffer = ByteBuffer.allocateDirect(4096 * 4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            long readLength = 0;
            int readCount = 0;
            while ((readCount = socketChannel.read(buffer)) != -1) {
                readLength += readCount;
                buffer.rewind();
            }
            System.out.println("readLength : " + readLength);
        }
    }
}
