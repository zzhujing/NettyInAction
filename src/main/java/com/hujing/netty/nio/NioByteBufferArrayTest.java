package com.hujing.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author : hujing
 * @date : 2019/9/24
 * ByteBuffer[] 的使用，可以自定义协议，比如ByteBuffer[0] 为协议头 ByteBuffer[1] 为特殊标记 ByteBuffer[2] 为协议体
 *  这样可以无需获取Buffer之后判断然后操作逻辑。
 */
public class NioByteBufferArrayTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        //阻塞
        SocketChannel socketChannel = serverSocketChannel.bind(address).accept();

        //构造ByteBuffer[]

        int messageLength = 9;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocateDirect(2);
        buffers[1] = ByteBuffer.allocateDirect(3);
        buffers[2] = ByteBuffer.allocateDirect(4);


        for (; ; ) {
            long readLength = 0;
            //读满9个字节才跳出读循环
            while (readLength < messageLength) {
                long read = socketChannel.read(buffers);
                readLength += read;
                System.out.println("read : " + read);
                Arrays.stream(buffers).map(b -> "position : " + b.position() + ", limit : " + b.limit()).forEach(System.out::println);
            }

            //翻转为写
            Arrays.stream(buffers).forEach(ByteBuffer::flip);
            long writeLength = 0;
            //一次最多写出九个字节
            while (writeLength < messageLength) {
                long write = socketChannel.write(buffers);
                writeLength += write;
            }
            //初始化状态，不然第二次会死循环
            Arrays.stream(buffers).forEach(ByteBuffer::clear);
        }
    }
}
