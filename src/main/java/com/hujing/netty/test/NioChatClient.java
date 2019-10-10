package com.hujing.netty.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioChatClient {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        for (; ; ) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    SocketChannel client =(SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        client.finishConnect(); //完成链接
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((LocalDateTime.now()+": 连接成功").getBytes());
                        writeBuffer.flip();
                        client.write(writeBuffer);
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit((Runnable)()->{
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            while (true) {
                                writeBuffer.clear();
                                try {
                                    String s = br.readLine();
                                    writeBuffer.put(s.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    client.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                } else if (selectionKey.isReadable()) {
                    SocketChannel client = ((SocketChannel) selectionKey.channel());
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int read = client.read(readBuffer);
                    if (read > 0) {
                        System.out.println(new String(readBuffer.array(), StandardCharsets.UTF_8));
                    }
                    iterator.remove();
                }
            }
        }
    }
}
