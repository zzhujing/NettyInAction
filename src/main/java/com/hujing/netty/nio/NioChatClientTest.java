package com.hujing.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : hujing
 * @date : 2019/9/24
 */
public class NioChatClientTest {
    public static void main(String[] args) {
        try {

            //打开客户端channel
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            //创建selector并且将客户端通道注册上去
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            for (; ; ) {
                //打开io，当有一个被选择的channel过来时才会继续向下运行
                selector.select();

                //获取所有的SelectionKey
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectedKey : selectionKeys) {

                    //如果是正在连接状态
                    if (selectedKey.isConnectable()) {

                        SocketChannel client = ((SocketChannel) selectedKey.channel());
                        //连接状态
                        if (client.isConnectionPending()) {
                            //让连接完成
                            client.finishConnect();

                            //向服务端发送连接成功消息
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " ：  连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            //用另外一个线程来监听标准输入
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit((Runnable) () -> {

                                while (true) {
                                    writeBuffer.clear();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                    try {
                                        //将键盘输入发送到服务器端
                                        String line = br.readLine();
                                        writeBuffer.put(line.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectedKey.isReadable()) {

                        //监听服务端发送给自己的数据，并输出到控制台
                        SocketChannel channel = (SocketChannel) selectedKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(readBuffer);
                        if (read > 0) {
                            System.out.println(new String(readBuffer.array(), 0, read));
                        }
                    }
                }
                //清空selectionKey，不然出空指针异常
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
