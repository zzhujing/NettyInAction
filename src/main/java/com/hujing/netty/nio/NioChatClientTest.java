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
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            for (; ; ) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectedKey : selectionKeys) {
                    if (selectedKey.isConnectable()) {
                        SocketChannel client = ((SocketChannel) selectedKey.channel());
                        //连接状态
                        if (client.isConnectionPending()) {
                            //让连接完成
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " ：  连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit((Runnable) () -> {

                                while (true) {
                                    writeBuffer.clear();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                    try {
                                        String line = br.readLine();
                                        writeBuffer.put(line.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (selectedKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectedKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(readBuffer);
                        if (read > 0) {
                            System.out.println(new String(readBuffer.array(), 0, read));
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
