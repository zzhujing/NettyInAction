package com.hujing.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author hujing
 * @date 19-9-23
 * Scattering 和Gathering -> 在自定义协议的多ByteBuffer的使用
 */
public class NioBuffersTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8899);
        SocketChannel socketChannel = serverSocketChannel.bind(socketAddress).accept();

        int messageLength = 9;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        for (; ; ) {
            long readLength = 0;
            while (readLength < messageLength) {
                //说明还没有读取到自定义协议中的9个字节
                long read = socketChannel.read(byteBuffers);
                System.out.println(read);
                readLength += read;
                //遍历Scattering的position和limit
                Arrays.stream(byteBuffers).map(buffer -> "position : " + buffer.position() + ", limit : " + buffer.limit()).forEach(System.out::println);
            }
            //翻转
            Arrays.stream(byteBuffers).forEach(ByteBuffer::flip);
            long writeLength = 0;
            while (writeLength < messageLength) {
                long write = socketChannel.write(byteBuffers);
                writeLength += write;
            }
            //初始化position和limit位置,不然会死循环
            Arrays.stream(byteBuffers).forEach(ByteBuffer::clear);
            System.out.println("readLength : " + readLength + ", writeLength : " + writeLength + ", messageLength : " + messageLength);
        }
    }
}
